% Example inputs
% simple type solve_20(X) as an example
% Minimum number of moves to solve this puzzle is 20
solve_20(X):-
	solve_bfs_dl([3/1, 1/2, 1/1, 3/3, 1/3, 2/1, 2/2, 2/3, 3/2], X).

solve_10(X):-
        solve_bfs_dl([1/1, 1/2, 1/3, 2/3, 2/2, 3/3, 3/2, 2/1, 3/1], X).

solve_2(X):-
        solve_bfs_dl([3/3, 1/3, 2/3, 3/2, 1/2, 2/2, 3/1, 1/1, 2/1], X).

:- use_module(tile_game).

solve_bfs( Start, Direction)  :-
  breadthfirst( [ [Start] ],  Direction).

% This checks to see if the current configuration is the goal
breadthfirst( [ [Node | _] | _], [])  :-
  goal(Node).

% The reason for storing all the paths is to ensure that we do not
% choose the same path repeatedly

breadthfirst( [Path | Paths],  [Direction|S])  :-
  extend( Path, NewPaths, Direction),            % Finds all the possibilities that haven't been chosen
  conc( Paths, NewPaths, Paths1),     % Concatenates the paths
  breadthfirst( Paths1, S).    % Continues with the new paths

extend( [Node | Path], NewPaths, Direction) :-
  % Finds all the possibilities that we haven't already chosen from a given node
  bagof( [NewNode, Node | Path],
	  ( move( Node, NewNode,Direction), not(member( NewNode, [Node | Path]))),
         NewPaths),
  !.

extend( _, [], _ ).              % bagof failed: Node has no successor


conc([],L,L).
conc([H|L1],L2,[H|L3]):-
     conc(L1,L2,L3).



%BFSPQ Works but is very slow with larger numbers

solve_brf_pq(Node,Solution):-
	brf_pq([[Node]], Solution).

brf_pq([[Node|Path]|_], [Node|Path]):-
	goal(Node).

brf_pq([[Node|Path]|OtherPaths], Solution):-
	findall([NextNode, Node|Path], move(Node, NextNode, _), NewPaths),
	append(OtherPaths,NewPaths,Paths),
	brf_pq(Paths,Solution).


% BFSDL much faster but ERROR: Out of global stack for larger numbers
solve_bfs_dl(Node, Solution):-
	bfs_dl([[Node]|Z]-Z, Solution).

bfs_dl([[Node|Path]|_]-_,[Node|Path]):-
	goal(Node).

bfs_dl([[Node|Path]|OtherPaths]-Z, S):-
	findall([NextNode, Node|Path],
		move(Node,NextNode,_),NewPaths),
	make_diff_list(NewPaths, DiffNewPaths),
	append_diff(OtherPaths-Z, DiffNewPaths, Paths),
	not(empty_diff(Paths)),
	bfs_dl(Paths,S).



make_diff_list(X,Y-Z):- append(X, Z, Y).
append_diff(X-Y ,Y-Z ,X-Z ).
empty_diff([]-[]).



% Practice code BFS

solve(Start):-
	bfs([[Start]]).

bfs([[Node| _]|_]) :-
    goal(Node).

bfs([Path| Paths]):-
	extend2(Path, NewPaths),
	conc(Paths, NewPaths, Paths1),
	bfs(Paths1).

extend2([Node|BeenPaths], NewPaths):-
	findall(NewNode, (move(Node, NewNode, _),not(member(NewNode, [Node|BeenPaths]))),NewPaths),!.

extend2( _, _).















% solve( Start, Solution)  :-
%  breadthfirst( [ [Start] | Z] - Z, Solution).

%breadthfirst( [ [Node | Path] | _] - _, [Node | Path] )  :-
%  goal( Node).

%breadthfirst( [Path | Paths] - Z, Solution)  :-
%  extend( Path, NewPaths),
%  conc( NewPaths, Z1, Z),              % Add NewPaths at end
%  Paths \== Z1,                        % Set of candidates not empty
%  breadthfirst( Paths - Z1, Solution).

