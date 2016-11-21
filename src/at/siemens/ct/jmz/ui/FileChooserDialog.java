package at.siemens.ct.jmz.ui;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserDialog {

	private File mznFile;

	public FileChooserDialog() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    "MiniZinc files", "mzn");
			chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(chooser.getSelectedFile().getName());
			mznFile = chooser.getSelectedFile();
		}
		
	}

	public String getFile()
	{
		return mznFile.getAbsolutePath();
	}

}
