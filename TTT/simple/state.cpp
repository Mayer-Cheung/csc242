/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
simple TTT
date: 2/14/2016
file: definition and method of state
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
	//  construtor
	State(Board bd, Player np):board(bd), next_player(np){}
	
	State(int size, Player p)
	{
		State(Board(size), p);
	}

	State(int size)
	{
		State(Board(size), X);
	}

	State()
	{
		State(3, X);
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

	Player getNextPlayer()
	{
		return next_player;
	}

	//print by standard error
	void print()
	{
		cerr << "Next palyer: ";
		if (next_player == X)
			cerr << "X" << endl;
		else
			cerr << "O" << endl;
	}

	//  apply a move
	void apply(Move move)
	{
		board.apply(move);
		next_player = (next_player == X) ? O : X;
	}

	//  check game is over
	bool isDone()
	{
		return board.isDone();
	}

	//  get a winner
	int getWinner()
	{
		return board.getWinner();
	}

	//  set current player
	void setNextPlayer(Player p)
	{
		next_player = p;
	}

	//  return all possible move
	vector<Move> getPossibleMoves()
	{
		return board.getPossibleMoves(next_player);
	}
protected:
	Board board;
	Player next_player;
};
#endif
