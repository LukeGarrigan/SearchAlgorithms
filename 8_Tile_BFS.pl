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


% Figure 11.10   An implementation of breadth-first search.


% solve( Start, Solution):
%    Solution is a path (in reverse order) from Start to a goal


%
solve( Start, Solution)  :-
  breadthfirst( [ [Start] ], Sal),
  reverse(Sal, Solution).

% solve( Start, Solution)  :-
%  breadthfirst( [ [Start] | Z] - Z, Solution).

%breadthfirst( [ [Node | Path] | _] - _, [Node | Path] )  :-
%  goal( Node).

%breadthfirst( [Path | Paths] - Z, Solution)  :-
%  extend( Path, NewPaths),
%  conc( NewPaths, Z1, Z),              % Add NewPaths at end
%  Paths \== Z1,                        % Set of candidates not empty
%  breadthfirst( Paths - Z1, Solution).

% This checks to see if the current configuration is the goal
breadthfirst( [ [Node | Path] | _], [Node | Path])  :-
  goal(Node).

% Knows that it's not a goal configuration
%


% The reason for storing all the paths is to ensure that we do not get
% stuck in a loop where we choose the same path repeatedly

breadthfirst( [Path | Paths], Solution)  :-
  extend( Path, NewPaths),            % Finds all the possibilities that haven't been chosen
  conc( Paths, NewPaths, Paths1),     % Concatenates the paths
  breadthfirst( Paths1, Solution).    % Continues with the new paths

extend( [Node | Path], NewPaths) :-
  % Finds all the possibilities that we haven't already chosen
  bagof( [NewNode, Node | Path],
	  ( move( Node, NewNode, _), not(member( NewNode, [Node | Path]
	  )) ),
         NewPaths),
  !.

extend( _, [] ).              % bagof failed: Node has no successor


conc([],L,L).
conc([H|L1],L2,[H|L3]):-
conc(L1,L2,L3).
