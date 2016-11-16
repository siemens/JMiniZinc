/**
 * 
 */
package at.siemens.ct.jmz.conflictDetection.mznParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import at.siemens.ct.common.utils.FileUtils;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.Element;

/**
 * @author z003pczy (Mara Rosu) parses output from MiniZincIde (.mzn files)
 */

public class MiniZincCP {

	private ModelBuilder modelBuilder ;

	/**
	 * creates an MiniZincCP object according to the .mzn file
	 * 
	 * @param mznFile
	 *            - the .mzn File
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public MiniZincCP(File mznFile) throws IOException, IllegalArgumentException, IllegalAccessException {
		modelBuilder = new ModelBuilder();
		parseMZN(mznFile);
	}

	/**
	 * Parses a .mzn file and populates lists with decision variables and
	 * constraints
	 * 
	 * @param mznFile
	 *            - the MiniZinc file for the CP (constraint problem)
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void parseMZN(File mznFile) throws IOException, IllegalArgumentException, IllegalAccessException {
		List<String> linesFromMznFile = FileUtils.readLines(mznFile);
		Element mznElement;
		
		MiniZincElementFactory mznElementFactory= new MiniZincElementFactory();
		for (String line : linesFromMznFile) {
			mznElement  = mznElementFactory.getElementFromLine(line);
			if(mznElement!=null)
			{
				modelBuilder.add(mznElement);
			}
			
		}

		System.out.println();
	}

	public ModelBuilder getModelBuilder() {
		return modelBuilder;
	}



}
