/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method board
*/
#ifndef BOARD_H
#define BOARD_H

#include <string>
#include <vector>
#include "move.cpp"
#include <iostream>

using namespace std;

//  to convert the continuous space into the print order, I need this, since
//  actually I'm using two types of representative order of the board
const int printOrder[9][9] = 
	{{ 0,  1,  2,  9, 10, 11, 18, 19, 20},
	 { 3,  4,  5, 12, 13, 14, 21, 22, 23},
	 { 6,  7,  8, 15, 16, 17, 24, 25, 26},
	 {27, 28, 29, 36, 37, 38, 45, 46, 47},
	 {30, 31, 32, 39, 40, 41, 48, 49, 50},
	 {33, 34, 35, 42, 43, 44, 51, 52, 53}, 
	 {54, 55, 56, 63, 64, 65, 72, 73, 74},
	 {57, 58, 59, 66, 67, 68, 75, 76, 77},
	 {60, 61, 62, 69, 70, 71, 78, 79, 80}};

//  structure to check, a tuple of three integer, which simplifies the procedure to
//  check who wins
struct Traid
{
	int t[3];
	//  constructor
	Traid(int a, int b, int c)
	{
		t[0] = a, t[1] = b; t[2] = c;
	}
};

//  I need this to store the pre-caculated information about the triad of
//  possible win traid
static vector<Traid> vecTraid;

class Board
{
public:
	//  constructor
	Board()
	{
		//  set size to 9 and set all position to blank
		size = 9;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				bd[i * 9 + j] = blank;

		//  enumerate all eight possible triad to win in first small board
		vecTraid.push_back((Traid){0, 1, 2});
		vecTraid.push_back((Traid){3, 4, 5});
		vecTraid.push_back((Traid){6, 7, 8});
		vecTraid.push_back((Traid){0, 3, 6});
		vecTraid.push_back((Traid){1, 4, 7});
		vecTraid.push_back((Traid){2, 5, 8});
		vecTraid.push_back((Traid){0, 4, 8});
		vecTraid.push_back((Traid){2, 4, 6});

		//  using loops to pre-caculate all the possible 72 traid in the board for a win
		for (int i = 0; i < 8; i++)
			for (int j = 1; j <= 8; j++)
				vecTraid.push_back((Traid){vecTraid[i].t[0] + j * 9, vecTraid[i].t[1] + j * 9,
					vecTraid[i].t[2] + j * 9});

	}


	//  print the board by standard error
	void print()
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				//  get the printorder
				int index = printOrder[i][j];
				if (bd[index] == X)
					cerr << "X";
				else if (bd[index] == O)
					cerr << "O";
				else
					cerr << "S";

				//  divide each small board
				if (j % 3 == 2)
					cerr << " ";

			}
			cerr << endl;
			//  divide each small board
			if (i % 3 == 2)
				cerr << endl;
		}
		cerr << endl;
	}

	//  method

	//  check a location is empty or not
	bool isEmpty(int i, int j)
	{
		return bd[i * 9 + j] == blank;
	}

	//  apply a move to the board
	void apply(Move move)
	{
		int i = move.getX();
		int j = move.getY();
		bd[i * 9 + j] = move.getPlayer();
	}

	//  check a game is over or not
	bool isDone()
	{
		//  if there is a winner, the game is over
		if (getWinner() != 0)
			return true;
		//  the board is full, game is over
		else
			return isFull();
	}

	//  get the winner, by enumearting all the possible traid
	int getWinner()
	{
		//  enumerating
		for (int i = 0; i < vecTraid.size(); i++)
		{
			int sum = 0;			
			for (int j = 0; j < 3; j++)
				sum += bd[vecTraid[i].t[j]];
			//  3 O in a row
			if (sum == 3)
				return 1;
			//  3 X in a row
			else if (sum == -3)
				return -1;
		}
		//  return with a draw
		return 0;
	}

	//  check the board is full or not
	bool isFull()
	{
		//  if there exists one empty place, it is not full
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (isEmpty(i, j))
					return false;

		//  after check all possible location, it is full
		return true;
	}

	//  check the small 3*3 board is full or not, according to the constraints
	bool isFullLastSlot(int last)
	{
		for (int j = 0; j < size; j++)
			if (isEmpty(last, j))
				return false;
		return true;
	}

	//  get all the possible moves
	vector<Move> getPossibleMoves(Player p, int lastSlot)
	{
		//  if it is the first move or the board according to the last slot is full
		//  get all the empty possible location
		if (lastSlot == -1 || isFullLastSlot(lastSlot))
		{
			vector<Move> vec;
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					if (isEmpty(i, j))
						vec.push_back((Move){p, i, j});
			return vec;
		}
		//  or get the possible location inside of the responed small board
		else
		{
			vector<Move> vec;
			for (int i = 0; i < size; i++)
				if (isEmpty(lastSlot, i))
					vec.push_back((Move){p, lastSlot, i});

			return vec;
		}
	}
	//  return the size;
	int getSize()
	{
		return size;
	}
	//  the board
	Player bd[81];
protected:
	int size;
	
};


#endif
