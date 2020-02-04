% This is an explanatory example for minimal diagnoses.

% You may define constants and use them for domains of variables.
int: max = 4;
enum Size = { XS, S, M, L, XL };

% Define only one variable in each row!
% Use only types bool, int, min..max and enum!
var 1..max: x1; 
var 1..max: x2; 
var Size: s;
% You may use array variables.
array[3..max] of var int: x;
var bool: c3; 
% If you specify a value, the variable is considered a constant.
var bool: c4 = true; % permanently true, i.e. no decision variable

% You may define arbitrary constraints (they are background knowledge).
constraint x1 > x2;
constraint x1 = x2 + x[3];
constraint (x1 > 3) <-> (s = XL);
% If you want to analyse constraints as well, use activation variables:
constraint c3 -> (x2 = x[3]);
constraint c4 -> (x[3] = x[4]); % background knowledge because c4 is constant

% Do not write a solve expression into this file!
%solve satisfy; % will lead to problems during diagnosis
