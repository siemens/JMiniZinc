% parameters / domains
int: nc = 3;
int: min = 4;
int: max = 5;

% decision variables
var 1..nc: x_1; 
var min..max: x2; 
var bool: x3;
var int: x4;
solve satisfy