/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
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

class Board
{
public:
	//  constructor
	//  with default size 3
	Board()
	{
		size = 3;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				bd[i][j] = blank;
	}

	// with assigned size
	Board(int s)
	{
		size = 3;
		for (int i = 0; i < s; i++)
			for (int j = 0; j < s; j++)
				bd[i][j] = blank;
	}

	//  print by standard error with the board
	void print()
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (bd[i][j] == X)
					cerr << "X";
				else if (bd[i][j] == O)
					cerr << "O";
				else
					cerr << "S";
			}
			cerr << endl;
		}
		cerr << endl;
	}

	//  check is empty
	bool isEmpty(int x, int y)
	{
		return bd[x][y] == blank;
	}

	//  apply the move
	void apply(Move move)
	{
		int x = move.getX();
		int y = move.getY();
		bd[x][y] = move.getPlayer();
	}

	//  check whether the game is over
	bool isDone()
	{
		if (getWinner() != 0)
			return true;
		else
			return isFull();
	}

	//  to get a winner
	int getWinner()
	{
		int n = 0;

		n = checkHorizontals();
		if (n != 0)
			return n;

		n = checkVerticals();
		if (n != 0)
			return n;

		n = checkDiagonals();
		if (n != 0)
			return n;

		return 0;
	}

	//  check horizontal direction to get a winner
	int checkHorizontals()
	{
		int n = 0;
		for (int i = 0; i < size; i++)
		{
		    n = checkHorizontal(i);
			if (n != 0)
				return n;
		}
		return 0;
	}

	//  check specific row if there is a winner
	int checkHorizontal(int y)
	{
		int sum = 0;
		for (int i = 0; i < size; i++)
			sum += bd[y][i];
		if (sum == size)
			return 1;
		else if (sum == 4 * size)
			return 2;
		else
			return 0;
	}

	//  check vertical direction to figure out if there is a winner
	int checkVerticals()
	{
		int n = 0;
		for (int i = 0; i < size; i++)
		{
			n = checkVertical(i);
			if (n != 0)
				return n;
		}
		return 0;
	}

	//  check the exact column to figure out if there is a winner
	int checkVertical(int n)
	{
		int sum = 0;
		for (int i = 0; i < size; i++)
			sum += bd[i][n];
		if (sum == size)
			return 1;
		else if (sum == 4 * size)
			return 2;
		else
			return 0;
	}

	//  check two diagonal if there is a winner
	int checkDiagonals()
	{
		int sum = 0;
		for (int i = 0; i < size; i++)
			sum += bd[i][i];
		if (sum == size)
			return 1;
		else if (sum == 4 * size)
			return 2;

		sum = 0;
		for (int i = 0; i < size; i++)
			sum += bd[i][size - i - 1];
		if (sum == size)
			return 1;
		else if (sum == 4 * size)
			return 2;
		else
			return 0;
	}

	//  check it is full or not
	bool isFull()
	{
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (isEmpty(i, j))
					return false;

		return true;
	}

	//  get all possible move
	vector<Move> getPossibleMoves(Player p)
	{
		vector<Move> vec;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (isEmpty(i, j))
					vec.push_back((Move){p, i, j});
		return vec;
	}

	int getSize()
	{
		return size;
	}
protected:
	int size;
	Player bd[3][3];
};

#endif
