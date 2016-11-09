% exercise 1 from the lecture 4_diagnosis

% parameters / domains
int: nc = 3;

% decision variables
var 1..nc: x1; 
var 1..nc: x2; 
var 1..nc: x3; 

% constraints
%constraint x1=1;
%constraint x1=2;
constraint x2=x1;
constraint x3=x2;
constraint x3>2;


% solve / optimize
%solve satisfy;

% results format
%output ["x1=", show(x1), " x2=", show(x2), " x3=", show(x3), "\n"];
