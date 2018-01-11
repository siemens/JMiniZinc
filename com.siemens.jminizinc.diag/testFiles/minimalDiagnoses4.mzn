% This is an explanatory example for minimalDiagnoses.jar.

% You may define constants and use them for domains of variables.
int: max = 4;

% Define only one variable in each row!
% Use only types int, min..max, and bool!
var 1..max: x1; 
var 1..max: x2; 
% You may use array variables.
array[3..max] of var int: x;
var bool: c3; 
% If you specify a value, the variable is considered a constant.
var bool: c4 = true; % c4 is permanently true (no decision variable).

% You may define arbitrary constraints (they are background knowledge).
constraint x1 > x2;
constraint x1 = x2 + x[3];
% If you want to analyse constraints as well, use activation variables:
constraint c3 -> (x2 = x[3]);
constraint c4 -> (x[3] = x[4]); % this is background because c4 is constant

% Do not write a solve expression into this file!
%solve satisfy; % will lead to problems during diagnosis
%constraint x[3] = 3;
