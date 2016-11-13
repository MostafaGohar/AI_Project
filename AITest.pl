
%%	to run code in prolog type the following:
%%	main(S,0).
%%  then type 'w' to see the enitre result if .... appear in the solution.


at(3,2,0,s).
%knowledge base describing the maze 'canMoveForward(originalX, originalY, afterMovingForwardX, afterMovingForwardY, direction)'
canMoveForward(0,0,1,0,1).
canMoveForward(1,0,2,0,1).
canMoveForward(1,0,0,0,3).
canMoveForward(2,0,3,0,1).
canMoveForward(2,0,1,0,3).
canMoveForward(3,0,3,1,2).
canMoveForward(3,0,2,0,3).
canMoveForward(0,1,1,1,1).
canMoveForward(0,1,0,2,2).
canMoveForward(1,1,2,1,1).
canMoveForward(1,1,0,1,3).
canMoveForward(2,1,3,1,1).
canMoveForward(2,1,1,1,3).
canMoveForward(3,1,3,0,0).
canMoveForward(3,1,2,1,3).
canMoveForward(0,2,0,1,0).
canMoveForward(0,2,0,3,2).
canMoveForward(1,2,2,2,1).
canMoveForward(1,2,1,3,2).
canMoveForward(2,2,3,2,1).
canMoveForward(2,2,1,2,3).
canMoveForward(3,2,3,3,2).
canMoveForward(3,2,2,2,3).
canMoveForward(0,3,0,2,0).
canMoveForward(0,3,1,3,1).
canMoveForward(1,3,1,2,0).
canMoveForward(1,3,0,3,3).
canMoveForward(2,3,3,3,1).
canMoveForward(3,3,3,2,0).
canMoveForward(3,3,2,3,3).

% at(x_position, y_position, direction, situation)
at(X,Y,D,result(A,X,Y,D,S)):-
	
	(canMoveForward(X1,Y1,X,Y,D), A = forward, at(X1,Y1,D,S));
	(D1 is D+1, D2 is mod(D1,4), at(X,Y,D2,S), A = left);
	(D1 is D-1, D2 is mod(D1,4), at(X,Y,D2,S), A = right).


% main method implementing iterative depth search by calling 'call_with_depth_limit' multiple times until a solution appears (R does not equal depth_limit_exceeded)
% also repeated 4 times to get the solution which reaches the cell first regardless of direction

main(S,Depth):-
	call_with_depth_limit(at(2,1,0,S),Depth,R), R \= depth_limit_exceeded. 
main(S,Depth):-
	call_with_depth_limit(at(2,1,1,S),Depth,R), R \= depth_limit_exceeded.
main(S,Depth):-
	call_with_depth_limit(at(2,1,2,S),Depth,R), R \= depth_limit_exceeded.
main(S,Depth):-
	call_with_depth_limit(at(2,1,3,S),Depth,R), R \= depth_limit_exceeded.

main(S,Depth):-
 Depth1 is Depth + 1 ,
 main(S,Depth1).







	