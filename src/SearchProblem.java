import java.util.ArrayList;

public abstract class SearchProblem {

	private Object initial_state;
	private String[] operators;
	abstract boolean goal_test(Object state);
	abstract ArrayList<SearchTreeNode> expand(SearchTreeNode node);
	
	public Object getInitial_state() {
		return initial_state;
	}
	public void setInitial_state(Object initial_state) {
		this.initial_state = initial_state;
	}
	public String[] getOperators() {
		return operators;
	}
	public void setOperators(String[] operators) {
		this.operators = operators;
	}
}
