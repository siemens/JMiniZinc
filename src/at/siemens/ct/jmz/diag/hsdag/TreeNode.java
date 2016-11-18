package at.siemens.ct.jmz.diag.hsdag;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author z003pczy (Rosu Mara) Class for a node from the HSDAG tree
 */
public class TreeNode {	
	/**
	 *  List with constraints for minimal conflict set. Can be null.
	 */
	private List<Constraint> data;
	
	private List<Constraint> initialConstraintsSet;
		
	/**
	 *  parent node	
	 */
	private TreeNode parent;
	
	/**
	 * This is the constraint associated to the arch which comes to this node. Can be null for parent nodes.
	 */
	private Constraint constraint = null; 
	
	public TreeNode(List<Constraint> data, List<Constraint> initialConstraintsSet) {
		super();
		this.data = data;		
		this.initialConstraintsSet = initialConstraintsSet;
	}

	public void addChild(Constraint c, TreeNode child) {		
		child.parent = this;
		child.constraint = c;		
	}

	public TreeNode getParentNode() {
		return this.parent;
	}
	
	public List<Constraint> getData() {
		return this.data;
	}
		
	public String toString(){		
		StringBuilder sb = new StringBuilder();		
		sb.append("Data: ");
		if (data == null){
			sb.append("-");
		} else {
			for(Constraint c : data){
				sb.append(c.getConstraintName()).append("; ");
			}
		}
		sb.append("\n");
						
		sb.append("InitialConstraintsSet: ");
		if (initialConstraintsSet == null){
			sb.append("-");
		} else {
			for(Constraint c : initialConstraintsSet){
				sb.append(c.getConstraintName()).append("; ");
			}
		}
		sb.append("\n");
		
		if (constraint == null){
			sb.append("Constraint: null");
		} else {
			sb.append("Constraint: " + constraint.getConstraintName());
		}
		
		return sb.toString();
	}
	
	public Constraint getConstraint(){
		return constraint;  
	}

	public List<Constraint> getInitialConstraintsSet() {
		return initialConstraintsSet;
	}	
}
