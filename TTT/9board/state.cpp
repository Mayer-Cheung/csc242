/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method state
*/
#ifndef STATE_H
#define STATE_H

#include "board.cpp"
#include <iostream>
#include <vector>

using namespace std;

class State
{
public:
	//  constructor
	State()
	{
		lastSlot = -1;
	}

	State & operator = (const State &a)
	{
		board = a.board;
		next_player = a.next_player;
	}

	Board getBoard()
	{
		return board;
	}

	//  return current player
	Player getNextPlayer()
	{
		return next_player;
	}

	//  return the opposite player
	Player getAnother()
	{
		return next_player == O ? X : O;
	}

	//print the information and last slot by standard error
	void print()
	{
		cerr << "Next palyer: ";
		if (next_player == X)
			cerr << "X" << endl;
		else
			cerr << "O" << endl;
		cerr << "Last slot: " << lastSlot + 1 << endl;
	}

	//  apply the move and change next player
	void apply(Move move)
	{
		board.apply(move);
		next_player = (next_player == X) ? O : X;
	}

	//  check the game is over
	bool isDone()
	{
		return board.isDone();
	}

	//  return winner
	int getWinner()
	{
		return board.getWinner();
	}

	//  change current player
	void setNextPlayer(Player p)
	{
		next_player = p;
	}

	//  return all possible moves
	vector<Move> getPossibleMoves()
	{
		return board.getPossibleMoves(next_player, lastSlot);
	}

	//  change last slot
	void setLast(int a)
	{
		lastSlot = a;
	}
	
	//  record last slot position
	int lastSlot;

	//  the board
	Board board;
protected:
	//  current player
	Player next_player;
};
#endif
