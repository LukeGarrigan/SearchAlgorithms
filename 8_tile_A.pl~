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

% Heuristic estimate h is the sum of distances of each tile
% From its 'home' square pluis 3 times 'sequence' score
h([Empty|L], H):-
	goal([Empty|G]),
	totaldist(L, G, D),
	seq(L, S),
	H is D + 3*S.

% Calculates the entire distance of each head
totaldist([],[],0).
totaldist([T|L], [T1| L1], D):-
	d(T, T1, D1),
	totaldist(L,L1, D2),
	D is D1+D2.

seq([First|L], S):-
	seq([First|L],First,S).


seq([T1,T2|L],First, S):-
	score(T1,T2,S1),
	seq([T2|L], First, S2),
	S is S1 + S2.

seq([Last], First, S):-
	score(Last,First, S).

score( 2/2, _, 1):- !.

score( 1/3, 2/3, 0) :- !.
score( 2/3, 3/3, 0) :- !.
score( 3/3, 3/2, 0) :- !.
score( 3/2, 3/1, 0) :- !.
score( 3/l, 2/l, 0) :- !.
score( 2/l, 1/1, 0) :- !.
score( 1/l, l/2, 0) :- !.
score( 1/2, 1/3, 0) :- !.

score(_,_,2).


showsol([]).
showsol([P|L]):-
	showsol(L),
	nl, write('---'),
	showpos(P).

% Display a board position
showpos([S0, S1, S2, S3, S4, S5, S6, S7, S8]):-
	member(Y, [3,2,1]),
	nl, member(X, [1,2,3]),
	member(Tile-X/Y,
	       [''-S0,1-S1, 2-S2, 3-S3, 4-S4, 5-S5, 6-S6,7-S7, 8-S8]),
	write(Tile),fail.


showpos(_).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%
%%%     A* Algorithm
%%%
%%%
%%%     Nodes have form    S#D#F#A
%%%            where S describes the state or configuration
%%%                  D is the depth of the node
%%%                  F is the evaluation function value
%%%                  A is the ancestor list for the node

:- op(400,yfx,'#').    /* Node builder notation */

solve(State,Soln) :- f_function(State,0,F),
                     search([State#0#F#[]],S), reverse(S,Soln).

f_function(State,D,F) :-
	h(State,H),
        F is D + H.

search([State#_#_#Soln|_], Soln) :- goal(State).
search([B|R],S) :- expand(B,Children),
                   insert_all(Children,R,Open),
                   search(Open,S).

insert_all([F|R],Open1,Open3) :- insert(F,Open1,Open2),
                                 insert_all(R,Open2,Open3).
insert_all([],Open,Open).

insert(B,Open,Open) :- repeat_node(B,Open), ! .
insert(B,[C|R],[B,C|R]) :- cheaper(B,C), ! .
insert(B,[B1|R],[B1|S]) :- insert(B,R,S), !.
insert(B,[],[B]).

repeat_node(P#_#_#_, [P#_#_#_|_]).

cheaper( _#_#F1#_ , _#_#F2#_ ) :- F1 < F2.

expand(State#D#_#S,All_My_Children) :-
     bagof(Child#D1#F#[Move|S],
           (D1 is D+1,
             move(State,Child,Move),
             f_function(Child,D1,F)),
           All_My_Children).






