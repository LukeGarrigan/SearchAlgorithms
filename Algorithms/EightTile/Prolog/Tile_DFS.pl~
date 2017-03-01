:- use_module(tile_game).

assertretract(X) :- assert(X).
assertretract(X) :- retract(X), fail.

% Use this one, this way you can alter the depth in the command line

% Example inputs
% simple type solve_20(X, 30) as an example
% Minimum number of moves to solve this puzzle is 20
solve_20(X, MaxDepth):-
	clear, solve_dfs([3/1, 1/2, 1/1, 3/3, 1/3, 2/1, 2/2, 2/3, 3/2],MaxDepth, X).

solve_10(X, MaxDepth):-
	clear, solve_dfs([1/1, 1/2, 1/3, 2/3, 2/2, 3/3, 3/2, 2/1, 3/1],MaxDepth, X).


% Use this one, this way you can alter the depth in the command line

:- dynamic(seennode/1).

solve_dfs(Node, MaxMoves, Solution ):-
	dfs_depth(Node, 0, MaxMoves, Solution).

% Performs DFS at current allowed number of moves
dfs_depth(Node,NumMoves,_, Solution):-
	dfs_id(Node, Solution, NumMoves).

% Increases the allowed number of moves and recurisvely calls
dfs_depth(Node, NumMoves, MaxMoves, Solution):-
	MaxMoves > NumMoves,
	NewNumMoves is NumMoves+1,
	write('Moves Allowed is ' ),
	write(NumMoves),nl,
	dfs_depth(Node, NewNumMoves, MaxMoves, Solution).

% Checks to see if the node is a goal node
dfs_id(Node,[],_):-
	goal(Node).



% If the Node has already been seen fail
dfs_id(Node, _, Maxdepth):-
        Maxdepth > 0,
	seennode(Node), !,
	fail.
% Decrements the number of moves for each move that is taken
% If number of moves is 0 then it fails and the program will
% increase the maximum depth
dfs_id(Node, [Direction|Solution], NumMoves):-
	NumMoves > 0,
	move(Node, Node1, Direction),
	Max1 is NumMoves -1,
	assertretract(seennode(Node)),
	dfs_id(Node1, Solution, Max1).

clear :-
	retractall(seennode(_)).




















% Lets give the books version of dfs a go


%solve(Node, Solution):-
%	depthfirst([],Node, Solution, 0).

%depthfirst(Path,Node, [Node|Path], N):-
%	N < 25,
%	goal(Node).

%depthfirst(_,Node, _, N):-
%	N < 25,
%	seennode(Node), !,
%	fail.


%depthfirst(Path ,Node, Solution, N):-
%	N < 25,
%	move(Node, Node1), M is N + 1,
%	assertretract(seennode(Node)),
%	depthfirst([Node|Path], Node1, Solution, M).
