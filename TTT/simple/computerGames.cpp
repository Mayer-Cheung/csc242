/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
date: 2/14/2016
file: definition and method of computer game
*/
#ifndef COMPUTER_GAMES_H
#define COMPUTER_GAMES_H

#include "humanGames.cpp"

//  allow you to play with a very stupid program
class ComputerGame : public HumanGame
{
public:
	ComputerGame()
	{
		humanPlayer = getHumanPlayer();
	}
	
	//  ask if the human wanna go first
	Player getHumanPlayer()
	{
		while (true)
		{
			char a;
			cerr << "You want play with X or O?" << endl;
			cin >> a;
			if (a == 'o' || a =='O')
				return O;
			else if (a == 'X' || a == 'x')
				return X;
			else
				cerr << "Don't joke, choose from X and O" << endl;
		}
	}

	//  get next move
	Move getNextMove()
	{
		if (state.getNextPlayer() == humanPlayer)
			return HumanGame::getNextMove();
		else
			return getNextComputerMove();
	}

	//  just return the next available move, pretty stupid
	Move getNextComputerMove()
	{
		vector<Move> vec = state.getPossibleMoves();
		return vec[0];
	}
protected:
	Player humanPlayer;
};
#endif
