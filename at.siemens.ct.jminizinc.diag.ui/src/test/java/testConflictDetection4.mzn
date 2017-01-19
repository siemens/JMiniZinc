% parameters / domains
int: nc = 3;
int: min = 4;
int: max = 5;

% decision variables
var 1..nc: x_1; 
var min..max: x2; 
var bool: x3;
var int: x4;

array[1..2] of var bool: x5 = [true,true];
array[1..max] of var bool: x6;
array[1..max] of var 1..max: x7;
array[1..max] of var int: x8;


solve satisfy;