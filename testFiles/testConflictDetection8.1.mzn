% exercise 1 from the lecture 4_diagnosis

% parameters / domains
int: nc = 3;

% decision variables
var 1..nc: x1; 
var 1..nc: x2; 
var int: x3; 
var bool: c;

x3 = 4;

% constraints
constraint x1>x2;
constraint x1=x2+x3;
constraint c -> (x2=x3);

solve satisfy;