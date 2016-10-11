import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Main {


	public Object SearchAlgorithm(SearchProblem searchProblem, String QingFunc){
		ArrayDeque<SearchTreeNode> nodes = new ArrayDeque<SearchTreeNode>();
		SearchTreeNode root = new SearchTreeNode(null,0,0,null,searchProblem.getInitial_state());
		SearchTreeNode node;
		nodes.add(root);
		
		int iterative_depth_count = 0;
		ArrayList<SearchTreeNode>expanded_nodes = null;
		
		while(true){
			
			if(nodes.isEmpty() && QingFunc != "ID"){
				return false;
			}else{
				if(nodes.isEmpty()){
					nodes = new ArrayDeque<SearchTreeNode>();
					root = new SearchTreeNode(null,0,0,null,searchProblem.getInitial_state());
					nodes.add(root);
				
					iterative_depth_count++;

				}
			}
			
			node = nodes.removeFirst();
			if(searchProblem.goal_test(node.getState()))
				return node;
			switch(QingFunc){
			case "BF": 
				nodes.addAll(searchProblem.expand(node)) 
				
				;break;
			case "DF": 
				expanded_nodes = searchProblem.expand(node);
				for(int i = expanded_nodes.size()-1;i>=0;i--){
					nodes.addFirst(expanded_nodes.get(i));
				}
				;break;
			case "ID":
				if(!(node.getDepth() >= iterative_depth_count)){
					
					expanded_nodes = searchProblem.expand(node);
					for(int i = expanded_nodes.size()-1;i>=0;i--){
						nodes.addFirst(expanded_nodes.get(i));
					}
				}
				;break;
			case "UC": 
				nodes.addAll(searchProblem.expand(node));

				SearchTreeNode[] nodes_array1 = (SearchTreeNode[]) nodes.toArray();
				ArrayList<SearchTreeNode> nodes_array = new ArrayList<SearchTreeNode>(Arrays.asList(nodes_array1));
				ArrayList<SearchTreeNode> result = new ArrayList<SearchTreeNode>();
				for(int i = 0;i<nodes.size();i++){
					SearchTreeNode sorted_node = nodes_array.get(0);
					int index = 0;
					
					for(int j = 1;j<nodes.size();j++){
						if(sorted_node.getPath_cost_from_root() > nodes_array.get(j).getPath_cost_from_root()){
							sorted_node = nodes_array.get(j);
							index = j;
						}
					}
					result.add(sorted_node);
					nodes_array.remove(index);
				}
				
				nodes = new ArrayDeque<SearchTreeNode>();
				for(int i = 0;i<result.size();i++){
					nodes.add(result.get(i));
				}
				
				
				;break;
			case "GR": ;break;
			case "AS": ;break;
			}
		}
		
		
		
		
		
	}
	
}
