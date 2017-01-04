:- module(tile_game, [goal/1, move/2, swap/4, mandist/3]).


goal([3/1, 1/3, 2/3, 3/3, 1/2, 2/2, 3/2, 1/1, 2/1]).
% Empty can move to any of its neighbours which means that 'empty' and
% its neighbour interchange their positions

% 0 is always at the head of the list so swaps are made there
move( [Blank|Tiles], [Tile|New_Tiles]):-              % All arc costs are 1
	swap(Blank, Tile, Tiles, New_Tiles).             % Swap Empty and T in L giving L1


swap(Blank, Tile, [Tile|Tiles], [Blank|Tiles]):-          % T|L is the rest of the board
	mandist(Blank, Tile, 1).  % Finds all the possible legal moves
			 % The maximum difference must be one ( can't skip a tile )

swap(Blank, Tile, [First_Tile|Tiles],[First_Tile|New_Tiles]):-
	swap(Blank, Tile, Tiles, New_Tiles ). % takes off the head of the tail and passe

% D is the Manh. distance
mandist( X /Y , X1 / Y1 , D ):-
       D is abs(X - X1 )+ abs(Y - Y1 ).
