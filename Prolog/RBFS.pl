:- use_module(tile_game).



% rbfs(+P, +X/G/F/FF,Bound,-NewFF,-Sol,-Stop)
% P         Accumulator Path
% X/G/F/FF  Node to explore with its G, F and FF Value
% Bound:    Maximum evaluation in subtree rooted at X
% NewFF	    Returned FF val
% Sol       Returned solution path
% Stop	    Flag set to value stop when goal node found



start_rbfs(InitialState, Solution, Cost):-
	rbfs([InitialState], InitialState/0/0/0, 9999, Cost, Sol,_),!,
	reverse(Sol,Solution).


rbfs(_,_/_/F/_,Bound,F,_,_):-
	F>Bound, !.

rbfs(Path,X/_/F/_,_,F,Path,stop):-
	goal(X).

% replaced C with 1 --- Maybe add the cost to the move predicate?
rbfs(Path, X/G/F/FF, Bound, NewFF, Sol, RS):-
	findall(Y/G1/F1/FF1, (move(X,Y,_), not(member(Y,Path)),
			      seq(Y,H), G1 is G+1, F1 is G1+H,
			      (	  F<FF -> FF1 is max(FF,F1) ; FF1 is F1)),L),
	sortFF(L,L1),
	loop_rbfs(Path,L1,Bound,NewFF,Sol,RS).

loop_rbfs(_,[],_,9999,_,_).

loop_rbfs(_,[_/_/_/FF|_], Bound, NewFF,_,_):-
	(   FF >Bound; FF >= 9999), !, NewFF= FF.



loop_rbfs(Path,[X/G/F/FF|T], Bound, NewFF,Sol,RS):-
	nextbound(T,Bound,FF2),
	Newbound is min(Bound,FF2),
	rbfs([X|Path], X/G/F/FF, Newbound,NewFF1, Sol,RS),!,
	(   RS \== stop ->
	  ( insert(X/G/F/NewFF1,T,L1),
	    loop_rbfs(Path,L1,Bound,NewFF,Sol,RS)
	  )
	;
	NewFF=NewFF1).


sortFF(L,S):-
	map_list_to_pairs(extractF,L,L1),
	keysort(L1,L2),
	pairs_values(L2,S).

extractF(_/_/_/FF,FF).

nextbound([],Bound,Bound):-!.
nextbound([_/_/_/FF|_],_,FF).
insert(X/G/F/FF,[],[X/G/F/FF]).
insert(X/G/F/FF,[Y/G1/F1/FF1|T],[X/G/F/FF,Y/G1/F1/FF1|T]):-
	FF =<FF1,!.
insert(X/G/F/FF,[Y/G1/F1/FF1|T], [Y/G1/F1/FF1|L]):-
	insert(X/G/F/FF,T,L).


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
