/*  
author: Zhizhou Zhang
CSC242 University of Rochester
Project 1
9-board TTT
date: 2/14/2016
file: definition and method of player
*/
#ifndef PLAYER_H
#define PLAYER_H
//  use enumerating type as Professor Ferguson introduced in the class
//  represent player by value of -1 and 1, easy to evaluate for later utility
//  X is first player, blank means still unoccupied, O means second player
enum Player
{
	X = -1, 
	blank = 0,
	O = 1
};
#endif
