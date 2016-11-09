package at.siemens.ct.jmz.conflictDetection.HSDAG;

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
	 * The list of children nodes. The order is important.
	 */
	//private List<TreeNode> childrens; //todo: is this necessary?
	
	/**
	 * This is used when visiting the nodes breadth-first.
	 */
	//private boolean isVisited = false;
	
	/**
	 *  parent node	
	 */
	private TreeNode parent;
	
	/**
	 * The left neighbor in children's list 
	 */
	//private TreeNode leftNeighbor;
	//private TreeNode rightNeighbor;
	
	/**
	 * This is the constraint associated to the arch which comes to this node. Can be null for parent nodes.
	 */
	private Constraint constraint = null; 
	
	public TreeNode(List<Constraint> data, List<Constraint> initialConstraintsSet) {
		super();
		this.data = data;
		//childrens = new ArrayList<TreeNode>();
		this.initialConstraintsSet = initialConstraintsSet;
	}

	public void addChild(Constraint c, TreeNode child) {		
		child.parent = this;
		child.constraint = c;
		/*int len = childrens.size();
		if (len > 0){
			TreeNode prevNode = childrens.get(len - 1);
			prevNode.rightNeighbor = child;
			child.leftNeighbor = prevNode;
		}*/
		
		//childrens.add(child);
		
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

	/*public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public TreeNode getLeft(){
		return leftNeighbor;
	}
	
	public TreeNode getRight(){
		return rightNeighbor;
	}*/
	
	public Constraint getConstraint(){
		return constraint;  
	}

	public List<Constraint> getInitialConstraintsSet() {
		return initialConstraintsSet;
	}	
}
