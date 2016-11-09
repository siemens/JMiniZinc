package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.util.*;

import org.hamcrest.CoreMatchers;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author z003pczy (Rosu Mara) Class for a node from the HSDAG tree
 *
 */
public class TreeNode {

	// list with constraints from minimal conflict set
	private List<Constraint> data;
	// parent node
	private TreeNode parent;
	// kids
	private Map<Constraint, TreeNode> childrens;

	public TreeNode(List<Constraint> data) {
		super();
		this.data = data;
		childrens = new HashMap<Constraint, TreeNode>();
	}

	public void addChild(Constraint c, TreeNode child) {
		if (c != null) {
			child.parent = this;
			childrens.put(c, child);
		}

	}

	public TreeNode getParentNode() {
		return this.parent;
	}

	public TreeNode getChildrenByConstraint(Constraint c) {
		if (childrens.containsKey(c)) {
			return childrens.get(c);
		}
		return null;
	}

	public List<Constraint> getData() {
		return this.data;
	}

}
