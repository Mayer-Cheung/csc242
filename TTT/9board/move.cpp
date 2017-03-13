/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method of move, as smallest move during the game
*/
#ifndef MOVE_H
#define MOVE_H

//  include string from STL
#include <string>
#include "player.cpp"

class Move
{
	//  public components
public:
	//  constructor
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

	//  print, change the information into string
	std::string toString()
	{
		std::string str;
		str += "Move(";
		if (player == X)
			str += 'X';
		else
			str += 'O';

		str = str + ", " + (char)(x + '1') + ", " + (char)(y + '1') + ")";
		return str;
	}

//  protected componets, including the player and its two location parameter
protected:
	Player player;
	int x, y;
};
#endif
