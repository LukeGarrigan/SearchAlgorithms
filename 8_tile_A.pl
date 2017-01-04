:- use_module(tile_game).







% Heuristic estimate h is the sum of distances of each tile
% From its 'home' square plus 3 times 'sequence' score
h([Empty|L], H):-
	goal([Empty|G]),
	totaldist(L, G, D),
	seq(L, S),
	H is D + 3*S.

% The total distance of the eight tiles in their current position to
% their goal position
totaldist([],[],0).
totaldist([T|L], [T1| L1], D):-
	mandist(T, T1, D1),
	totaldist(L,L1, D2),
	D is D1+D2.

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












% Figure 12.3  A best-first search program.


% bestfirst( Start, Solution): Solution is a path from Start to a goal

bestfirst( Start, Solution) :-
  expand( [], l( Start, 0/0),  9999, _, yes, Solution).
	%  Assume 9999 is greater than any f-value

% expand( Path, Tree, Bound, Tree1, Solved, Solution):
%   Path is path between start node of search and subtree Tree,
%   Tree1 is Tree expanded within Bound,
%   if goal found then Solution is solution path and Solved = yes

%  Case 1: goal leaf-node, construct a solution path

expand( P, l( N, _), _, _, yes, [N|P])  :-
   goal(N).

%  Case 2: leaf-node, f-value less than Bound
%  Generate successors and expand them within Bound.

expand( P, l(N,F/G), Bound, Tree1, Solved, Sol)  :-
  F  =<  Bound,
  (  bagof( M/C, ( s(N,M,C), not member(M,P) ), Succ),
     !,                                    % Node N has successors
     succlist( G, Succ, Ts),               % Make subtrees Ts
     bestf( Ts, F1),                       % f-value of best successor
     expand( P, t(N,F1/G,Ts), Bound, Tree1, Solved, Sol)
     ;
     Solved = never                        % N has no successors - dead end
  ) .

%  Case 3: non-leaf, f-value less than Bound
%  Expand the most promising subtree; depending on
%  results, procedure continue will decide how to proceed

expand( P, t(N,F/G,[T|Ts]), Bound, Tree1, Solved, Sol)  :-
  F  =<  Bound,
  bestf( Ts, BF), min( Bound, BF, Bound1),          % Bound1 = min(Bound,BF)
  expand( [N|P], T, Bound1, T1, Solved1, Sol),
  continue( P, t(N,F/G,[T1|Ts]), Bound, Tree1, Solved1, Solved, Sol).

%  Case 4: non-leaf with empty subtrees
%  This is a dead end which will never be solved

expand( _, t(_,_,[]), _, _, never, _) :- !.

%  Case 5:  f-value greater than Bound
%  Tree may not grow.

expand( _, Tree, Bound, Tree, no, _)  :-
  f( Tree, F), F > Bound.

% continue( Path, Tree, Bound, NewTree, SubtreeSolved, TreeSolved, Solution)

continue( _, _, _, _, yes, yes, Sol).

continue( P, t(N,F/G,[T1|Ts]), Bound, Tree1, no, Solved, Sol)  :-
  insert( T1, Ts, NTs),
  bestf( NTs, F1),
  expand( P, t(N,F1/G,NTs), Bound, Tree1, Solved, Sol).

continue( P, t(N,F/G,[_|Ts]), Bound, Tree1, never, Solved, Sol)  :-
  bestf( Ts, F1),
  expand( P, t(N,F1/G,Ts), Bound, Tree1, Solved, Sol).

% succlist( G0, [ Node1/Cost1, ...], [ l(BestNode,BestF/G), ...]):
%   make list of search leaves ordered by their F-values

succlist( _, [], []).

succlist( G0, [N/C | NCs], Ts)  :-
  G is G0 + C,
  h( N, H),                             % Heuristic term h(N)
  F is G + H,
  succlist( G0, NCs, Ts1),
  insert( l(N,F/G), Ts1, Ts).

% Insert T into list of trees Ts preserving order w.r.t. f-values

insert( T, Ts, [T | Ts])  :-
  f( T, F), bestf( Ts, F1),
  F  =<  F1, !.

insert( T, [T1 | Ts], [T1 | Ts1])  :-
  insert( T, Ts, Ts1).


% Extract f-value

f( l(_,F/_), F).        % f-value of a leaf

f( t(_,F/_,_), F).      % f-value of a tree

bestf( [T|_], F)  :-    % Best f-value of a list of trees
  f( T, F).

bestf( [], 9999).       % No trees: bad f-value

min( X, Y, X)  :-
  X  =<  Y, !.

min( X, Y, Y).
