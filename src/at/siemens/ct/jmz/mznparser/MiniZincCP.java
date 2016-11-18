package at.siemens.ct.jmz.mznparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.common.utils.FileUtils;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.Variable;

/**
 * @author z003pczy (Mara Rosu) parses output from MiniZincIde (.mzn files)
 */

public class MiniZincCP {

	private List<Element> elementsFromFile;

	/**
	 * creates an MiniZincCP object according to the .mzn file
	 * 
	 * @param mznFile
	 *            - the .mzn File
	 * @throws IOException
	 */
	public MiniZincCP(File mznFile) throws IOException {
		elementsFromFile = new ArrayList<Element>();
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
	private void parseMZN(File mznFile) throws IOException {
		List<String> linesFromMznFile = FileUtils.readLines(mznFile);
		Element mznElement;

		MiniZincElementFactory mznElementFactory = new MiniZincElementFactory();
		for (String line : linesFromMznFile) {
			mznElement = mznElementFactory.getElementFromLine(line);
			if (mznElement != null) {
				elementsFromFile.add(mznElement);
			}

		}

	}

	public List<Element> getElementsFromFile() {
		return elementsFromFile;
	}

	public ArrayList<Variable<?, ?>> getDecisionVariables() {
		ArrayList<Variable<?, ?>> dvList = new ArrayList<Variable<?, ?>>();
		for (Element element : elementsFromFile) {
			if (element.isVariable()) {
				dvList.add((Variable<?, ?>) element);

			}
		}
		return dvList;
	}

	public TypeInst<?, ?> getDecisionVariableByName(String name) {

		for (Variable<?, ?> typeInst : getDecisionVariables()) {
			if (typeInst.getName().equals(name))
				return typeInst;
		}
		return null;

	}

}
