:- use_module(tile_game).



% Use this one, this way you c
%an alter the depth in the command line

% Example inputs
% simple type solve_20(X, 30) as an example
% Minimum number of moves to solve this puzzle is 20
solve_20(X):-
	solve_a([3/1, 1/2, 1/1, 3/3, 1/3, 2/1, 2/2, 2/3, 3/2], X).

solve_10(X):-
	solve_a([1/1, 1/2, 1/3, 2/3, 2/2, 3/3, 3/2, 2/1, 3/1], X).


assertretract(X) :- assert(X).
assertretract(X) :- retract(X), fail.


% search([State/Depth/F-function/ Ancestors]]
solve_a(Start, Solution):-
	f_function(Start, 0, F),
	search([Start/0/F/[]], Node),
	reverse(Node, Solution).

% f(n) = g(n) + h(n)  D is the current depth
f_function(State, D, F):-
	seq(State, H),
	F is D+H*3.

% the 'sequence score' that measures the degree to which the tiles are
% already ordered in the current position with respect to the order
% required in the goal configuration. seq is computed as the sum of
% scores for each tile according to the following rules:
seq([First|L], S):-
	seq([First|L],First,S).


seq([T1,T2|L],First, S):-
	score(T1,T2,S1),
	seq([T2|L], First, S2),
	S is S1 + S2.

seq([Last], First, S):-
	score(Last,First, S).


% returns the heuristic estimate
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



% Check whether state is goal node
search([State/_/_/Solution|_], Solution):-
	goal(State).


search([B|R], S):-
	expand(B, Children),
	insert_all(Children, R, NewOpen),
	search(NewOpen, S).

expand(State/D/_/A, All_My_Children):-
	bagof(Child/D1/F/[Direction|A],
	      (	  D1 is D + 1,			  % increases the depth each time
		  move(State, Child,Direction),	  % gets each child and the direction
		  f_function(Child, D1, F)),	  % calculates the f function of each
	      All_My_Children).



% inserts each node head at a time
insert_all([F|R], Open1, Open3):-
	insert(F, Open1, Open2),
	insert_all(R, Open2, Open3).

% Base case, ensuring they have been concatenated
insert_all([], Open, Open).

insert(B, Open, Open):- repeat_node(B, Open), !.

% If B is cheaper, make it the head node
insert(B,[C|R], [B,C|R]) :-
	cheaper(B, C),!.

insert(B,[B1|R], [B1|S]) :- insert(B,R,S), !.
insert(B,[],[B]).

repeat_node(P/_/_/_, [P/_/_/_|_]).
% finds which node will be put at the front of
% the open list
cheaper(_/_/H1/_, _/_/H2/_):- H1 < H2.










% An attempt at Pierres Implementation
%
%

:- dynamic(closed/2).
a_star(Node, Cost, Path):-
	retractall(closed(_,_)),
	seq(Node, Heuristic),
	list_to_heap([Heuristic-[Node,nil]],Heap),
	a_star_h(Heap,[Node1,Cost]),!,
	create_path(Node1,Path1),
	reverse(Path1, Path),
	retractall(closed(_,_)).
a_star(_,_,_):-
	retractall(closed(_,_)),fail.


a_star_h(Heap, [Node,Cost]):-
	min_of_heap(Heap,Cost,[Node,Pred]),
	goal(Node),
	assert(closed(Node,Pred)).

a_star_h(Heap,S):-
	min_of_heap(Heap,_,[Node,_]),
	closed(Node,_),
	get_from_heap(Heap,_,_,Heap1),
	a_star_h(Heap1,S).

% Same situations as RBFS Cost is set to 1
% 20 movements has trouble executing ( probably something to do with the
% cost function )
a_star_h(Heap,S):-
	get_from_heap(Heap,PathLength,[Node,Pred],Heap1),
	assert(closed(Node,Pred)),
	findall([C,[NextNode,Node]],
		(   move(Node,NextNode,_), not(closed(NextNode,_)),
		    C is PathLength+1),
		NewNodeCostAndPairs),
	update_heap(Heap1, NewNodeCostAndPairs, Heap2),
	a_star_h(Heap2, S).

update_heap(Heap,[],Heap).
update_heap(Heap, [[Cost,NodePair]|More], Heap2):-
	add_to_heap(Heap,Cost,NodePair, Heap1),
	update_heap(Heap1, More,Heap2).
create_path(Node,[Node]):-
	closed(Node,nil),!.
create_path(Node,[Node|S]):-
	closed(Node, Pred),
	create_path(Pred,S).










