% exercise 1 from the lecture 4_diagnosis

% parameters / domains
int: nc = 3;

% decision variables
var 1..nc: x1; 
var 1..nc: x2; 
var 1..nc: x3; 
var bool: c1;
var bool: c2;
var bool: c3;

% constraints
constraint c1 -> x2<x1;
constraint c2 -> x3=x2;
constraint c3 -> x3>2;