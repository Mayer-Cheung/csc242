/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
date: 2/14/2016
file: definition and method game
*/
#ifndef GAME_H
#define GAME_H

#include <iostream>
#include "state.cpp"

using namespace std;

//  abstract class
class Game
{
public:
	//  main function
	void play()
	{
		loop();
		gameOver();
	}
	
	State getState()
	{
		return state;
	}
	
	Game()
	{
		state.setNextPlayer(X);	
	}
protected:
	
	State state;

	//  until game stopped
	void loop()
	{
		while (!state.isDone())
		{
			state.print();
			Move move = getNextMove();
			state.apply(move);
			cerr << move.toString() << endl;
			state.getBoard().print();
		}
	}

	//  check whether the game is over
	void gameOver()
	{
		int winner = state.getWinner();
		if (winner != 0)
		{
			cerr << "The winner is ";
			if (winner == 1)
				cerr << "X" << endl;
			else
				cerr << "O" << endl;
		}
		else
		{
			cerr << "It is a draw";
		}
	}

	virtual Move getNextMove() = 0; 
};
#endif
