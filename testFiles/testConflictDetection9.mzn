

int:max =4;
var 1..max:a;
var 1..max:b;
var int: d;

var bool:c1 = true;
constraint c1-> a < b;

var bool:c2;
constraint c2->a = b+d;

var bool : c3;
constraint c3 -> b=d;

var bool: c4;
constraint c4 ->a < d;

array[1..max] of var int: x;

constraint x[1]=a;
constraint x[2]=b;
constraint x[3]=x[2];
constraint x[4]=d;
