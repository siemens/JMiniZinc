int: max = 4;

var 1..max: a; 
var 1..max: b; 
 par bool : c = true;
var int: d;

var bool: c1; 
constraint c1 -> a < b;

var bool: c2 = true; 
constraint c2 -> a = b + d;

var bool: c3; 
constraint c3 -> b = d;

var bool: c4; 
constraint c4 -> a < d;

array[1..max] of var int: x;
array[1..max] of var bool: xbool ;





% constraint x[1] = a;
% constraint x[2] = b;
% constraint x[3] = x[2];
% constraint x[4] = d;
% constraint x[4] > 1;

