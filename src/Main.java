import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Main {


	public static Object SearchAlgorithm(SearchProblem searchProblem, String QingFunc){
		ArrayDeque<SearchTreeNode> nodes = new ArrayDeque<SearchTreeNode>();
		SearchTreeNode root = new SearchTreeNode(null,0,0,null,searchProblem.getInitial_state());
		SearchTreeNode node;
		nodes.add(root);
		int heuristic = 0;
		int iterative_depth_count = 0;
		ArrayList<SearchTreeNode>expanded_nodes = null;
		ArrayList<SearchTreeNode> stns = new ArrayList<SearchTreeNode>();
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
			if(!stns.isEmpty()){
				stns.remove(0);
			}
			if(searchProblem.goal_test(node.getState()))
				return node;
			switch(QingFunc.substring(0,2)){
			case "BF": 
//				stns.addAll(searchProblem.expand(node));
//				System.out.println("AASAs");
//				System.out.println("node " +((PokemonState)node.getState()).getDirection());
//				
//				for(int g = 0;g<stns.size();g++){
//					System.out.println("nodes " +((PokemonState)stns.get(g).getState()).getX() +" " +((PokemonState)stns.get(g).getState()).getY()
//							+" dir "+"nodes dir " +((PokemonState)stns.get(g).getState()).getDirection());
//
//				}
				nodes.addAll(searchProblem.expand(node));
				
//				System.out.println("AASAs");
//				System.out.println("node " +((PokemonState)node.getState()).getDirection());
//				
//				for(int g = 0;g<stns.size();g++){
//					System.out.println("nodes " +((PokemonState)stns.get(g).getState()).getX() +" " +((PokemonState)stns.get(g).getState()).getY()
//							+" dir "+"nodes dir " +((PokemonState)stns.get(g).getState()).getDirection());
//
//				}
					
				
				
				
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
				nodes = sortDeque(nodes);
				
				
				
				;break;
			case "GR": 
			case "AS":
				heuristic = Integer.parseInt(QingFunc.substring(2));
				ArrayList<SearchTreeNode> nodes_expanded = searchProblem.expand(node);
				switch(heuristic){
				
				case 1: 
					for(int ne = 0;ne<nodes_expanded.size();ne++){
						nodes_expanded.get(ne)
						.setHeuristic_cost(firstHeuristic(nodes_expanded.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				case 2:
					for(int ne = 0;ne<nodes_expanded.size();ne++){
						nodes_expanded.get(ne)
						.setHeuristic_cost(secondHeuristic(nodes_expanded.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				case 3:
					for(int ne = 0;ne<nodes_expanded.size();ne++){
						nodes_expanded.get(ne)
						.setHeuristic_cost(thirdHeuristic(nodes_expanded.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				}
				nodes.addAll(nodes_expanded);
				if(QingFunc.substring(0,2).equals("AS")){
					nodes = sortDeque(nodes);
				}else{
					nodes = sortDequeGreedy(nodes);
				}
				;break;
			}
		}
		
	}
	
	private static ArrayDeque<SearchTreeNode> sortDequeGreedy(ArrayDeque<SearchTreeNode> nodes) {
		
		ArrayDeque<SearchTreeNode> nodes_temp = nodes.clone();
		ArrayList<SearchTreeNode> nodes_array = new ArrayList<SearchTreeNode>();
		for(int nt = 0; nt<nodes.size();nt++){
			nodes_array.add((SearchTreeNode) nodes_temp.pop());
		}
		
		
		ArrayList<SearchTreeNode> result = new ArrayList<SearchTreeNode>();
		
		for(int i = 0;i<nodes.size();i++){
			SearchTreeNode sorted_node = nodes_array.get(0);
			//nodes_array.remove(0);
			int index = 0;
			
			for(int j = 0;j<nodes_array.size();j++){
				if(sorted_node.getHeuristic_cost()
				> nodes_array.get(j).getHeuristic_cost()){
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
		return nodes;
	}
	public static void main(String[]args){
		Maze maze = new Maze();
		maze.display();
		String [] ops = {"R", "F", "L"};
		if(maze.getPokemons().isEmpty())
			System.out.println("No Pokemon");
		GottaCatchemAllSearchProblem gcasp = new GottaCatchemAllSearchProblem(maze,
				new PokemonState(maze.getStarting_X(),maze.getStarting_Y(),0,maze.getPokemons(),maze.getEgg_hatch()),
				ops);
		System.out.println(maze.getEgg_hatch());

		SearchTreeNode resultNode = ((SearchTreeNode)SearchAlgorithm(gcasp, "AS3"));
		SearchTreeNode node = resultNode;
		System.out.println(((PokemonState)node.getState()).getX()+ " | "+((PokemonState)node.getState()).getY()+" D "+((PokemonState)node.getState()).getDirection());
		while(node.getParent() != null){
			node = node.getParent();
			System.out.println(((PokemonState)node.getState()).getX()+ " | "+((PokemonState)node.getState()).getY()+" D "+((PokemonState)node.getState()).getDirection());
		}
		
		
	}
	
	public static ArrayDeque<SearchTreeNode> sortDeque(ArrayDeque<SearchTreeNode> nodes){
		ArrayDeque<SearchTreeNode> nodes_temp = nodes.clone();
		ArrayList<SearchTreeNode> nodes_array = new ArrayList<SearchTreeNode>();
		for(int nt = 0; nt<nodes.size();nt++){
			nodes_array.add((SearchTreeNode) nodes_temp.pop());
		}
		
		
		ArrayList<SearchTreeNode> result = new ArrayList<SearchTreeNode>();
		
		for(int i = 0;i<nodes.size();i++){
			SearchTreeNode sorted_node = nodes_array.get(0);
			//nodes_array.remove(0);
			int index = 0;
			
			for(int j = 0;j<nodes_array.size();j++){
				if(sorted_node.getPath_cost_from_root() + sorted_node.getHeuristic_cost()
				> nodes_array.get(j).getPath_cost_from_root() + nodes_array.get(j).getHeuristic_cost()){
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
		return nodes;
	}
	
	public static int firstHeuristic(SearchTreeNode node, Maze maze){
		
		return getDistance(((PokemonState)node.getState()).getX(),
				((PokemonState)node.getState()).getY(),
				maze.getEnding_X(),
				maze.getEnding_Y());
	}
	
	public static int secondHeuristic(SearchTreeNode node, Maze maze){

		int maxDistance = 0;
		for(int i = 0;i<((PokemonState)node.getState()).getPokemons().size();i++){
			int dist = getDistance(((PokemonState)node.getState()).getX(),
					((PokemonState)node.getState()).getY(),
					((PokemonState)node.getState()).getPokemons().get(i).getX(),
					((PokemonState)node.getState()).getPokemons().get(i).getY());
			if(dist > maxDistance)
				maxDistance = dist;
		}
		return maxDistance;
	}
	private static int thirdHeuristic(SearchTreeNode node, Maze maze) {
		
		int maxDistance = 0;
		for(int i = 0;i<((PokemonState)node.getState()).getPokemons().size();i++){
			int dist = getDistance(((PokemonState)node.getState()).getX(),
					((PokemonState)node.getState()).getY(),
					((PokemonState)node.getState()).getPokemons().get(i).getX(),
					((PokemonState)node.getState()).getPokemons().get(i).getY())
					+ 
					getDistance(((PokemonState)node.getState()).getPokemons().get(i).getX(),
					((PokemonState)node.getState()).getPokemons().get(i).getY(),
					maze.getEnding_X(),
					maze.getEnding_Y());
			if(dist > maxDistance)
				maxDistance = dist;
		}
		return maxDistance;
	}
	
	public static int getDistance(int x1, int y1, int x2, int y2){
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
}
