goal([2/2,1/3,2/3,3/3,3/2,3/1,2/1,1/1,1/2]).

% Empty can move to any of its neighbours which means that 'empty' and
% its neighbour interchange their positions

move( [Empty|L], [T|L1], 1):-              % All arc costs are 1
	swap(Empty, T, L, L1).             % Swap Empty and T in L giving L1

swap(Empty, T, [T|L], [Empty|L]):-
	d(Empty, T, 1).

swap(Empty, T, [T1|L],[T1|L1]):-
	swap(Empty, T, L, L1).


d(X/Y, X1/Y1, D):-
	dif(X, X1, Dx),
	dif(Y, Y1, Dy),
	D is Dx+Dy.

% Calucates the absolute difference
dif(A, B, D):-
	D is A-B,
	D >= 0, !;
	D is B-A.



printlist([]).
printlist([X|Y]) :- write(X), nl, printlist(Y).

printindent( X, 0 ) :- write(X), nl, !.
printindent( X, N ) :- write('  '), M is N-1, printindent(X,M).

assertretract(X) :- assert(X).
assertretract(X) :- retract(X), fail.

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
	move(Node, Node1, _), M is N + 1,
	assertretract(seennode(Node)),
	depthfirst([Node|Path], Node1, Solution, M).



% Use this one, this way you can alter the depth in the command line

depthfirst2(Node, [Node], _):-
	goal(Node).

depthfirst2(Node, _, Maxdepth):-
        Maxdepth > 0,
	seennode(Node), !,
	fail.

depthfirst2(Node, [Node|Solution], Maxdepth):-
	Maxdepth > 0,
	move(Node, Node1, _),
	Max1 is Maxdepth -1,
	assertretract(seennode(Node)),
	depthfirst2(Node1, Solution, Max1).










:- dynamic(seenstate/1).

solveall( S, _, N ) :-
    N<30,
    seenstate(S), !,
    printindent( alreadytried(S), N ),
    fail.

% If we're at the final state, succeed.
solveall( S, [S], N ) :-
    N<30,
    goal(S),
    printindent( goalfound(S), N ),
    writef( '%w moves.\n', [N] ).

% Otherwise, assert that we have seen the current state, then choose a
% successor state and solve it.
solveall( S, [S|Rest], N ) :-
    N<30,
   %printindent( solving(S), N ),
    assertretract( seenstate(S) ),
    move(S,T, _), M is N+1, solveall(T,Rest,M).


clear():-
	retractall(seennode(_)),
	retractall(seenstate(_)).







