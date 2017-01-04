:- use_module(tile_game).

solve( Start, Solution)  :-
  breadthfirst( [ [Start] ], Sal),
  reverse(Sal, Solution).

% This checks to see if the current configuration is the goal
breadthfirst( [ [Node | Path] | _], [Node | Path])  :-
  goal(Node).

% The reason for storing all the paths is to ensure that we do not
% choose the same path repeatedly

breadthfirst( [Path | Paths], Solution)  :-
  extend( Path, NewPaths),            % Finds all the possibilities that haven't been chosen
  conc( Paths, NewPaths, Paths1),     % Concatenates the paths
  breadthfirst( Paths1, Solution).    % Continues with the new paths

extend( [Node | Path], NewPaths) :-
  % Finds all the possibilities that we haven't already chosen
  bagof( [NewNode, Node | Path],
	  ( move( Node, NewNode), not(member( NewNode, [Node | Path]
	  )) ),
         NewPaths),
  !.

extend( _, [] ).              % bagof failed: Node has no successor


conc([],L,L).
conc([H|L1],L2,[H|L3]):-
     conc(L1,L2,L3).






















% solve( Start, Solution)  :-
%  breadthfirst( [ [Start] | Z] - Z, Solution).

%breadthfirst( [ [Node | Path] | _] - _, [Node | Path] )  :-
%  goal( Node).

%breadthfirst( [Path | Paths] - Z, Solution)  :-
%  extend( Path, NewPaths),
%  conc( NewPaths, Z1, Z),              % Add NewPaths at end
%  Paths \== Z1,                        % Set of candidates not empty
%  breadthfirst( Paths - Z1, Solution).

