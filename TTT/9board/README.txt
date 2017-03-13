Author: Zhizhou Zhang
Date: 2/15/2016
CSC 242: TTT
I did not collaborate on this project and all work is my own.

platform:
	Windows 10, 64-bit, Dev-C++ 5.2.0.3
Language:
	C++
How to run:
	I'm using Dev-C++, and compile and excute it directly. If
	I have free time after the writeup, I will try to learn how
	to write a makefile. If you have some problem about how to run
	contact me: zzhang72@u.rochester.edu

Files:
	README.txt  --this file, Including the basic explanation of this project
	board.cpp   --definition and implementation of board class
	clever.cpp  --clever computer player by minimax with alphabet pruning
	game.cpp   --definition and implementation of game class
	humanGames.cpp   --definition and implementation of humanGames class
	move.cpp   --definition and implementation of move class
	player.cpp   --definition and implementation of player class
	state.cpp   --definition and implementation of state class
	play.cpp    --main function

Extra:
	1. I hope to join in the tournament, but don't know how, since the platform and language

	2. Don't know about the output of winner, if you want standard output,
	you can go state.cpp, from line 51 to 59, change the cerr into cout

	3. you can adjust the depth of search, also the search time in clever.cpp, line 20.