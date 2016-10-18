
public class SearchTreeNode {

	private SearchTreeNode parent;
	private int depth;
	private int path_cost_from_root;
	private String operator;
	private Object state;
	private int heuristic_cost;
	
	

	public SearchTreeNode(SearchTreeNode parent, int depth, int path_cost_from_root, String operator, Object state){
		this.parent = parent;
		this.depth = depth;
		this.path_cost_from_root = path_cost_from_root;
		this.operator = operator;
		this.state = state;
		this.heuristic_cost = 0;
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
	
	public SearchTreeNode cloneNode(){
		SearchTreeNode new_node = new SearchTreeNode(parent, depth, path_cost_from_root, operator, state);
		return new_node;
	}
	public int getHeuristic_cost() {
		return heuristic_cost;
	}

	public void setHeuristic_cost(int heuristic_cost) {
		this.heuristic_cost = heuristic_cost;
	}

}
