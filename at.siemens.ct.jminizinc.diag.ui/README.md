# JMiniZinc Diagnosis UI

This is an example how to use the diagnosis component of JMiniZinc.

## Set up a small diagnosis application with UI 
- Precondition: You have installed [MiniZinc IDE (bundled)](http://www.minizinc.org/software.html)
- Download the binaries from the latest [JMiniZinc release](https://github.com/siemens/JMiniZinc/releases)
- Unzip them to your local folder (thus adding some jar files)

## Start the application (see sample.bat as an example)
- Add the MiniZinc folder to your path variable
- Call `javaw -classpath * at.siemens.ct.jmz.ui.VariableDialog sample.mzn`
- If you do not hand over a mzn filename, then a file chooser dialog will open

## In the UI
- Set some variables (as defined in the mzn file) 
- Select a diagnosis algorithm (e.g. QuickXPlain+HSDAG or FastDiag)
- Start the diagnosis to see minimal diagnoses, conflict sets, intermediate steps 