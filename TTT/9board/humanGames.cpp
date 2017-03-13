/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method of human game
*/
#ifndef HUMAN_GAME_H
#define HUMAN_GAME_H

#include "game.cpp"
#include <iostream>

using namespace std;

//  allow two people to play
class HumanGame: public Game
{
protected:
	//  get next move, by human or standard input
	//  accepting two number and return a move
	Move getNextMove()
	{
		int a, b;
		cerr << "Enter a move" << endl;
		while (true)
		{
			cin >> b >> a;
			if (b <= 0 || b > 9 || a <= 0 || a > 9)
			{
				cerr << "Illegal number, input again!" << endl;
				continue;
			}
			else if (state.board.isEmpty(b - 1, a - 1) == false)
			{
				cerr << "Has been occupied, input again!" << endl;
				continue;
			}
			else if (state.lastSlot != -1 && b - 1 != state.lastSlot && 
				state.board.isFullLastSlot(state.lastSlot) == false)
			{
				cerr << "Dude, follow the rule, input again!" << endl;
				continue;
			}
			else
				break;
		}
		b--, a--;
		// state.setLast(a);		
		a = b * 9 + a;
		int x = a / 9;
		int y = a % 9;
		return Move(state.getNextPlayer(), x, y);
	}
};

#endif
