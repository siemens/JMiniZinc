/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

/**
 * 
 * @author Copyright Siemens AG, 2017
 *
 */
public class DiagnosisException extends Exception {

  private static final long serialVersionUID = 1620929690602094866L;

  public DiagnosisException(String message) {
    super(message);
  }

  public DiagnosisException(String message, Throwable cause) {
    super(message, cause);
  }

}
