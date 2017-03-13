/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method of game
*/
#ifndef GAME_H
#define GAME_H

#include <iostream>
#include "state.cpp"

using namespace std;

//  abstract class of game
class Game
{
public:
	//  main function to play
	void play()
	{
		loop();
		gameOver();
	}
	
	//  return state
	State getState()
	{
		return state;
	}
	
	//  constructor, which set the first player to X
	Game()
	{
		state.setNextPlayer(X);	
	}

//  private member
protected:
	
	State state;

	//  run the game, when the game is not over, output the move by standard output
	//  output the board and related information by standard error
	void loop()
	{
		while (!state.isDone())
		{
			state.print();
			Move move = getNextMove();
			state.apply(move);
			state.setLast(move.getY());
			cout << move.getX() + 1 << " " << move.getY() + 1 << endl;
			cerr << move.toString() << endl;
			state.getBoard().print();
		}
	}

	//  check whether a game is over, output result by standard error
	void gameOver()
	{
		int winner = state.getWinner();
		if (winner != 0)
		{
			cerr << "The winner is ";
			if (winner == -1)
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
