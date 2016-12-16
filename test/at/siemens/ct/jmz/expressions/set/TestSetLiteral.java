/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link SetLiteral}.
 *
 * @author © Siemens AG, 2016
 */
public class TestSetLiteral {

	@Test
	public void testContains() {
		SetLiteral set = SetLiteral.fromIntegers(1, 2, 3, 4, 5);
		Assert.assertTrue(set.contains(1));
		Assert.assertTrue(set.contains(2));
		Assert.assertTrue(set.contains(3));
		Assert.assertTrue(set.contains(4));
		Assert.assertTrue(set.contains(5));
	}

	@Test
	public void testDoesNotContain() {
		SetLiteral set = SetLiteral.fromIntegers(1, 2, 3, 4, 5);
		Assert.assertFalse(set.contains(0));
		Assert.assertFalse(set.contains(-1));
		Assert.assertFalse(set.contains(Integer.MIN_VALUE));
		Assert.assertFalse(set.contains(Integer.MAX_VALUE));
		Assert.assertFalse(set.contains(6));
	}

}
