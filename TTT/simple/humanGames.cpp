/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
date: 2/14/2016
file: definition and method of human game
*/
#ifndef HUMAN_GAME_H
#define HUMAN_GAME_H

#include "game.cpp"
#include <iostream>

using namespace std;

//  allow two boring people to play
class HumanGame: public Game
{
public:
protected:
	Move getNextMove()
	{
		int a;
		cerr << "Enter a move" << endl;
		cin >> a;
		int x = (a - 1) / 3;
		int y = (a - 1) % 3;
		return Move(state.getNextPlayer(), x, y);
	}
};

#endif
