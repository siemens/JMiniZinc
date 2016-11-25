package at.siemens.ct.jmz.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserDialog {

	private File mznFile;

	public FileChooserDialog() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MiniZinc files", "mzn");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getSelectedFile().getName());
			mznFile = chooser.getSelectedFile();
		}
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			chooser.setVisible(false);
			//JOptionPane.showMessageDialog(null, "Task Canceled");
		}

	}

	public String getFile() {
		if (mznFile != null) {
			return mznFile.getAbsolutePath();
		}
		return "";

	}

}
