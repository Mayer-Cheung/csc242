/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
date: 2/14/2016
file: definition and method of move
*/
#ifndef MOVE_H
#define MOVE_H

#include <string>
#include "player.cpp"

//  basic elements of a game
class Move
{
public:
	//  constructor and distructor
	Move(Player p, int i, int j)
	{
    	player = p;
	 	x = i;
	 	y = j;
    }

	//  method
	Player getPlayer()
	{
		return player;
	}

	int getX()
	{
		return x;
	}

	int getY()
	{
		return y;
	}

	//  print
	std::string toString()
	{
		std::string str;
		str += "Move(";
		if (player == X)
			str += 'X';
		else
			str += 'O';

		str = str + ", " + (char)(x + '0') + ", " + (char)(y + '0') + ")";
		return str;
	}

protected:
	Player player;
	int x, y;
};
#endif
