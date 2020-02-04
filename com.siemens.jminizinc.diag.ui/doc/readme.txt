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
- If you do not hand over a mzn filename, then a file chooser dialog opens

In the UI:
- Set some variables (as defined in the mzn file) 
- Run a diagnosis or filter invalid values

Run a diagnosis:
- Select a diagnosis algorithm (e.g. QuickXPlain+HSDAG or FastDiag)
- Click "Start diagnosis ..." 
- The systems shows you minimal diagnoses and conflict sets,
  as well as intermediate steps 

Filter invalid values:
- Click "Calculate valid options..."
- The system marks all those values (of all variables with discrete domains)
  as "(invalid)" which cannot be part of a valid solution
- You see it when clicking in the combobox of such a variable
- If existing variable settings are causing the inconsistency,
  then all values of a variable may become invalid
- After selecting a different value for a variable, 
   click "Calculate valid options..." again

At present, we support only part of the MiniZinc language:
- See comments in sample.mzn for information on supported features
- Follow those conventions in your own mzn files for diagnosis
