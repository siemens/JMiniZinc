This is an example how to use JMiniZinc for a diagnosis application with UI.

The folder contains:
- readme.txt: this file
- sample.mzn: an example of a MiniZinc file to be diagnosed
- sample_windows.bat: an example how to start a diagnosis application on Windows
- sample_mac.sh: an example how to start a diagnosis application on Apple
- *.jar: the necessary Java libraries

Start the application (see sample_windows.bat or sample_mac.sh):
- Precondition: You have unzipped this content to a local folder
- Precondition: You have installed MiniZinc IDE (bundled)
- Add the MiniZinc installation folder to your path variable
- Call javaw -classpath "*" at.siemens.ct.jmz.ui.VariableDialog sample.mzn
- If you do not hand over a mzn filename then a file chooser dialog opens

In the UI:
- Set some variables (as defined in the mzn file) 
- Select a diagnosis algorithm (e.g. QuickXPlain+HSDAG or FastDiag)
- Click "Start the diagnosis"
- Thus you will see minimal diagnoses, conflict sets, intermediate steps 

At present, we support only part of the MiniZinc language:
- See comments in sample.mzn for information on supported features
- Follow those conventions in your own mzn files for diagnosis
