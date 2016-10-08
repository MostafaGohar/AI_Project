
public class SearchTreeNode {

	private SearchTreeNode parent;
	private int depth;
//	int heuristic_cost;
	private int path_cost_from_root;
	private String operator;
	private Object state;
	
	public SearchTreeNode(SearchTreeNode parent, int depth, int path_cost_from_root, String operator, Object state){
		this.parent = parent;
		this.depth = depth;
		this.path_cost_from_root = path_cost_from_root;
		this.operator = operator;
		this.state = state;
	}
	

	

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}


	public SearchTreeNode getParent() {
		return parent;
	}

	public void setParent(SearchTreeNode parent) {
		this.parent = parent;
	}

	
	public int getPath_cost_from_root() {
		return path_cost_from_root;
	}

	public void setPath_cost_from_root(int path_cost_from_root) {
		this.path_cost_from_root = path_cost_from_root;
	}
	
//	public int getHeuristic_cost() {
//		return heuristic_cost;
//	}
//
//	public void setHeuristic_cost(int heuristic_cost) {
//		this.heuristic_cost = heuristic_cost;
//	}
}