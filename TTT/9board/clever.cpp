/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method of clever game, using alphabet pruning
*/
#ifndef CLEVER_H
#define CLEVER_H

#include "humanGames.cpp"
#include <set>
#include <vector>
#include <cstdlib>

using namespace std;

//  default search depth
const int maxDepth = 7;

//  defualt numbere to signal a maximum utility
const int maximum = 1e6;

//  a structure to help search, with score of utility and whether it can cause a win
//  or not
struct Score
{
	int score;
	bool decisive;

	//  constructor
	Score()
	{
		score = 0;
		decisive = false;
	}
	Score(int a, bool b)
	{
		score = a;
		decisive = b;
	}
};

//  extra vector to remember related auxilory information
//  to store the location of center 
static set<int> center;

//  to store the location of corner
static set<int> corner;

//  to store the possible triad for a win
static vector< vector<Traid> > vec;

//  class of clever game
class CleverGame : public HumanGame
{
public:
	//  a signal to take down the human player or the computer player
	//  I will explain it into detail in the write_up
	int standFor;

	//  constructor
	CleverGame()
	{
		//  set standFor
		standFor = 1;

		//  get humanPlayer
		humanPlayer = getHumanPlayer();

		//  set the bool of first move to true
		fst = true;

		//  pre-calculate the center
		int a[9] = {4, 13, 22, 31, 40, 49, 58, 67, 76};
		for (int i = 0; i < 9; i++)
			center.insert(a[i]);

		//  pre-calculate the corner
		int b[4] = {0, 2, 6, 8};
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 4; j++)
				corner.insert(b[j] + i * 9);

		vector<Traid> v;
		//  number of the possible win traid
		int c[9] = {3, 2, 3, 2, 4, 2, 3, 2, 3};
		//  the detail of they include, like
		/*
		0 1 2
		3 4 5
		6 7 8, 
		put a piece in 0, you can later win by put 012, 036, 048, which
		is 0, 3, 6 in the order from the first 8 triad calculated in the board.cpp
		*/
		int d[9][5] = 
		{
			{0, 3, 6},
			{0, 4},
			{0, 5, 7},
			{1, 3},
			{1, 4, 6, 7},
			{1, 5},
			{2, 3, 7},
			{2, 4},
			{2, 5, 6}
		};
		//  with the help of loop, simplify the procedure
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < c[i]; j++)
			{
				v.push_back(vecTraid[d[i][j]]);
			}
			vec.push_back(v);
			v.clear();
		}
	}

	//  get next move
	Move getNextMove()
	{
		//  human turn
		if (state.getNextPlayer() == humanPlayer)
		{
			//  set first to false
			fst = false;
			return HumanGame::getNextMove();
		}
		//  computer turn
		else
			return getNextComputerMove();
	}


	//  ask people wanna play first or not, X first, O second
	//  accept both lower and upper case
	Player getHumanPlayer()
	{
		while (true)
		{
			char a;
			cerr << "You want play with X or O?" << endl;
			cin >> a;

			//  set standFor and return
			if (a == 'o' || a =='O')
			{
				standFor = -1;
				return O;
			}
			else if (a == 'X' || a == 'x')
				return X;
			//  loop until the right choice
			else
				cerr << "Don't joke, choose from X and O" << endl;
		}
	}

	//  utility function by summing up and enumerating
	Score utility(int last, int pos, int depth, int standFor)
	{
		Score s(0, false);

		for (int i = 0; i < vec[pos].size(); i++)
		{
			Traid tp = vec[pos][i];

			//  get the sum
			int sum = 0;
			for (int j = 0; j < 3; j++)
				sum += state.board.bd[tp.t[j] + 9 * last];

			//  deal with sum
			switch (sum)
			{
				//  case 3 indluding a win for either player
				case  3:
					s.score = maximum * standFor;
					s.decisive = true;
					break;
				case -3:
					s.score = -maximum * standFor;
					s.decisive = true;
					break;

				//  case 2 is like a big chance to win
				case  2:
					s.score += 1500 * standFor;
					break;
				case -2:
					s.score -= 1500 * standFor;
					break;

				//  like XSS or XXS, if the later, block the other player
				case  1:
				case -1:
				    //  detect it is a block or not
					//  if it is, every place have to be occupied and not empty
					if ((state.board.bd[tp.t[0] + 9 * last]
						& state.board.bd[tp.t[1] + 9 * last]
						& state.board.bd[tp.t[2] + 9 * last])
						!= 0)
					{
						s.score -= sum * 500 * standFor;
					}
					break;

				//  not huge difference, normal move
				case  0:
					s.score += state.board.bd[pos + 9 * last] * standFor;
					break;
				default:
					break;
			}
			//  if find a decisive move, break
			if (s.decisive)
				break;
		}

		//  based on the information we get from the simple TTT
		//  set priority to the center location
		if (!s.decisive)
		{
			int temp = pos + 9 * last;
			int weight = 0;
			//  check it is a center
			if (center.find(temp) != center.end())
				weight += 3;
			//  check it is a corner
			if (corner.find(temp) != corner.end())
				weight += 1;
			s.score += weight * state.board.bd[temp] * standFor;
		}
		return s;
	}

	//  alphabeta pruning, according to AIMA 5.3
	int alphabeta(int lastS, Player p, Player np, int alpha, int beta,
		int depth, int scoreNow, bool lastMove, int standFor)
	{
		//  already got the solution or exceed the search depth
		if (lastMove || depth >= maxDepth)
			return scoreNow;
		int temp_score = scoreNow;

		//  enumerate all possiblity in small board
		for (int i = 0; i < 9; i++)
		{
			int temp_move = i + 9 * lastS;

			//  check it is blank
			if (state.board.bd[temp_move] == blank)
			{
				//  put it
				state.board.bd[temp_move] = p;

				//  get the utility function
				Score s = utility(lastS, i, depth, standFor);

				//  search for the next
				int val = alphabeta(i, np, p, alpha, beta, depth + 1,
					scoreNow + s.score, s.decisive, standFor);

				//  like DFS, restore the board
				state.board.bd[temp_move] = blank;

				//  process with different standfor
				if (standFor == 1)
				{				
					switch (p)
					{
						case O:
							if (val > alpha)
								alpha = val;
							break;
						case X:
							if (val < beta)
								beta = val;
							break;
					}
				}
				else
				{
					switch (p)
					{
						case X:
							if (val > alpha)
								alpha = val;
							break;
						case O:
							if (val < beta)
								beta = val;
							break;
					}					
				}
				if (beta <= alpha)
					break;
			} 
		}
		temp_score = beta;
		//  1 as computer play like O
		if (standFor == 1)
		{

			if (p == O)
				temp_score = alpha;
		}
		//  -1 as computer play like X
		else
		{
			if (p == X)
				temp_score = alpha;
		}
		return temp_score;
	}

	//  get best move for computer
	Move getNextComputerMove()
	{
		//  if it is the first move, just go for the mid
		if (fst)
		{
			fst = false;
			return Move(X, 4, 4);
		}

		//  set initial value
		int best_val = -maximum;

		//  get all possible move
		vector<Move> possible = state.getPossibleMoves();

		//  set the move to first
		Move my_move = possible[0];

		//  travesel all possibility
		for (int i = 0; i < possible.size(); i++)
		{
			int temp_move = possible[i].getX() * 9 + possible[i].getY();

				//  apply, and check
				state.board.bd[temp_move] = possible[i].getPlayer();


				Score s = utility(possible[i].getX(), possible[i].getY(), 0, standFor);

				int val = alphabeta(possible[i].getY(), state.getAnother(), state.getNextPlayer(),
				    -(maximum + maxDepth + 1),
					maximum + maxDepth + 1, 1, s.score, s.decisive, standFor);

				//  restore the board
				state.board.bd[temp_move] = blank;

				//  get a better move, replace the old one
				//  update the utility
				if (val > best_val)
				{
					best_val = val;
					my_move = (Move){state.getNextPlayer(), possible[i].getX(), possible[i].getY()};
				}
		}
		return my_move;
	}

private:
	bool fst;
	Player humanPlayer;
};
#endif
