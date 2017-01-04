:- use_module(tile_game).


assertretract(X) :- assert(X).
assertretract(X) :- retract(X), fail.



% Use this one, this way you can alter the depth in the command line

depthfirst2(Node, [Node], _):-
	goal(Node).

depthfirst2(Node, _, Maxdepth):-
        Maxdepth > 0,
	seennode(Node), !,
	fail.

depthfirst2(Node, [Node|Solution], Maxdepth):-
	Maxdepth > 0,
	move(Node, Node1),
	Max1 is Maxdepth -1,
	assertretract(seennode(Node)),
	depthfirst2(Node1, Solution, Max1).








% Lets give the books version of dfs a go

:- dynamic(seennode/1).

solve(Node, Solution):-
	depthfirst([],Node, Solution, 0).

depthfirst(Path,Node, [Node|Path], N):-
	N < 25,
	goal(Node),
	write('blar').

depthfirst(_,Node, _, N):-
	N < 25,
	seennode(Node), !,
	fail.


depthfirst(Path ,Node, Solution, N):-
	N < 25,
	move(Node, Node1), M is N + 1,
	assertretract(seennode(Node)),
	depthfirst([Node|Path], Node1, Solution, M).









