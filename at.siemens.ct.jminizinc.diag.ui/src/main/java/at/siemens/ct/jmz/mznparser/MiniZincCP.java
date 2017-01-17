/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.common.utils.FileUtils;
import at.siemens.ct.jmz.ui.Displayable;

/**
 * Parses output from MiniZincIde (.mzn files)
 * @author z003pczy (Mara Rosu) 
 */

public class MiniZincCP {

	private List<Displayable> elementsFromFile;

	/**
	 * creates an MiniZincCP object according to the .mzn file
	 * 
	 * @param mznFile
	 *            - the .mzn File
	 * @throws Exception
	 */
	public MiniZincCP(File mznFile) throws Exception {
		elementsFromFile = new ArrayList<Displayable>();
		parseMZN(mznFile);
	}

	/**
	 * Parses a .mzn file and populates lists with decision variables and
	 * constraints
	 * 
	 * @param mznFile
	 *            - the MiniZinc file for the CP (constraint problem)
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void parseMZN(File mznFile) throws Exception {
		List<String> linesFromMznFile = FileUtils.readLines(mznFile);
		Displayable mznElement;

		MiniZincElementFactory mznElementFactory = new MiniZincElementFactory();
		for (String line : linesFromMznFile) {
			mznElement = mznElementFactory.getElementFromLine(line);
			if (mznElement != null) {
				elementsFromFile.add(mznElement);
			}

		}

	}

	public List<Displayable> getElementsFromFile() {
		return elementsFromFile;
	}

	public Displayable getDecisionVariableByName(String name) {

		for (Displayable typeInst : elementsFromFile) {
			if (typeInst.getName().equals(name))
				return typeInst;
		}
		return null;

	}

}
