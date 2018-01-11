# JMiniZinc Diagnosis UI

This is an example how to use the diagnosis component of JMiniZinc.

## Set up a small diagnosis application with UI 
- Precondition: You have installed [MiniZinc IDE (bundled)](http://www.minizinc.org/software.html)
- Download the binaries from the latest [JMiniZinc release](https://github.com/siemens/JMiniZinc/releases)
- Unzip them to your local folder (thus adding some jar files)

## Start the application
- Add the MiniZinc folder to your path variable
- Call `javaw -classpath * at.siemens.ct.jmz.ui.VariableDialog sample.mzn`
- If you do not hand over a mzn filename, then a file chooser dialog will open

## Input a MiniZinc file
- Variables defined in the mzn file can be assigned in the UI
- Value assignments in the UI can then be diagnosed
- Constraints in the mzn file serve as background knowledge
- See [sample.mzn](sample.mzn) for an example

## In the UI
- Set some variables (as defined in the mzn file) 
- Select a diagnosis algorithm (e.g. QuickXPlain+HSDAG or FastDiag)
- Start the diagnosis to see minimal diagnoses, conflict sets, intermediate steps