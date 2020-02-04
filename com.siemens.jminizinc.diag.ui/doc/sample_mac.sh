#!/bin/bash
# Author: Lukas Hejny, TU Graz

# MacOS path variables are stored here
file="/etc/paths.d/minizinc"
if [ -f "$file" ]
then
	# minizinc file already in /etc/paths.d/
	echo "MiniZinc already added to path."
	echo 'Launching JMiniZinc...'
	java -cp ".:ui-1.4.jar:diag-1.3.jar:core-1.4.jar"  at.siemens.ct.jmz.ui.VariableDialog sample.mzn
else
	# minizinc file no present in /etc/paths.d/
	# create file and write installation folder of MiniZincIDE application.
	touch file
	echo /Applications/MiniZincIDE.app/Contents/Resources > $file
	echo "Added MiniZinc to path. Please restart your Terminal to apply changes."
fi