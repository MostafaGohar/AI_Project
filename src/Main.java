import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Main {


	public static Object SearchAlgorithm(SearchProblem searchProblem, String QingFunc){
		ArrayDeque<SearchTreeNode> nodes = new ArrayDeque<SearchTreeNode>();
		SearchTreeNode root = new SearchTreeNode(null,0,0,null,searchProblem.getInitial_state());
		((GottaCatchemAllSearchProblem)searchProblem).getHashStates().add((PokemonState)root.getState());
		SearchTreeNode node;
		nodes.add(root);
		int heuristic = 0;
		int iterative_depth_count = 0;
		ArrayList<SearchTreeNode>expanded_nodes = null;

		while(true){

			if(nodes.isEmpty() && QingFunc != "ID"){
				return null;
			}else{
				if(nodes.isEmpty()){
					nodes = new ArrayDeque<SearchTreeNode>();
					root = new SearchTreeNode(null,0,0,null,searchProblem.getInitial_state());
					nodes.add(root);
					((GottaCatchemAllSearchProblem)searchProblem).getHashStates().clear();
				
					iterative_depth_count++;

				}
			}

			node = nodes.removeFirst();

			if(searchProblem.goal_test(node.getState()))
				return node;
			switch(QingFunc.substring(0,2)){
			case "BF": 
				nodes.addAll(searchProblem.expand(node));
				;break;
			case "DF": 

				expanded_nodes = searchProblem.expand(node);
				for(int i = expanded_nodes.size()-1;i>=0;i--){
					nodes.addFirst(expanded_nodes.get(i));
				}
				//System.out.println(((PokemonState)node.getState()).getX() +" | "+((PokemonState)node.getState()).getY())
				;break;
			case "ID":
				if(!(node.getDepth()+1 > iterative_depth_count)){
					
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
				expanded_nodes = searchProblem.expand(node);
				switch(heuristic){
				
				case 1: 
					for(int ne = 0;ne<expanded_nodes.size();ne++){
						expanded_nodes.get(ne)
						.setHeuristic_cost(firstHeuristic(expanded_nodes.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				case 2:
					for(int ne = 0;ne<expanded_nodes.size();ne++){
						expanded_nodes.get(ne)
						.setHeuristic_cost(secondHeuristic(expanded_nodes.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				case 3:
					for(int ne = 0;ne<expanded_nodes.size();ne++){
						expanded_nodes.get(ne)
						.setHeuristic_cost(thirdHeuristic(expanded_nodes.get(ne)
								,((GottaCatchemAllSearchProblem)searchProblem).getMaze()));
					}
					;break;
				}
				nodes.addAll(expanded_nodes);
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
	public static Maze GenMaze(){
		Maze maze = new Maze();
		return maze;
	}
	public static ArrayList<Object> Search(Maze maze, String strategy, boolean visualize){
		ArrayList<Object> result = new ArrayList<Object>();
		ArrayList<String> moves = new ArrayList<String>();
		String [] ops = {"R", "F", "L"};
		if(maze.getPokemons().isEmpty())
			System.out.println("No Pokemon");
		GottaCatchemAllSearchProblem gcasp = new GottaCatchemAllSearchProblem(maze,
				new PokemonState(maze.getStarting_X(),maze.getStarting_Y(),0,maze.getPokemons(),maze.getEgg_hatch()),
				ops);
		System.out.println("Egg_hatch: "+maze.getEgg_hatch());
		
		SearchTreeNode resultNode = ((SearchTreeNode)SearchAlgorithm(gcasp, strategy));
		if(resultNode != null)
		{
			SearchTreeNode node = resultNode;
			if(visualize)
				printMazeWithAgentAt(maze, (PokemonState)node.getState());
			System.out.println(((PokemonState)node.getState()).getX()+ " | "+((PokemonState)node.getState()).getY()+" D "+((PokemonState)node.getState()).getDirection());
			while(node.getParent() != null){
				moves.add(node.getOperator());
				node = node.getParent();
				if(visualize)
					printMazeWithAgentAt(maze, (PokemonState)node.getState());
				System.out.println(((PokemonState)node.getState()).getX()+ " | "+((PokemonState)node.getState()).getY()+" D "+((PokemonState)node.getState()).getDirection());
			}
			
		}
		
		result.add(moves);
		result.add(resultNode.getPath_cost_from_root());
		result.add(gcasp.getExpanded_nodes());
		return result;
	}
	public static void printMazeWithAgentAt(Maze maze, PokemonState state){
		int x = state.getX();
		int y = state.getY();
		int d = state.getDirection();
		for (int i = 0; i < maze.getY(); i++) {
			// draw the north edge -- & 1 to check if the cell has a north edge or not (north bit is 1)
			for (int j = 0; j < maze.getX(); j++) {
				System.out.print((maze.getMaze()[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge -- & 8 to check if the cell has a west edge or not (west bit is 8)
			for (int j = 0; j < maze.getX(); j++) {
				if(j == x && i == y){
					switch(d){
					case 0: System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| ^ " : "  ^ ");
					;break;
					case 1: System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| > " : "  > ");
					;break;
					case 2: System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| v " : "  v ");
					;break;
					case 3: System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| < " : "  < ");
					;break;
					}
				}else{
					if(j == maze.getStarting_X() && i == maze.getStarting_Y()){
						System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| S " : "  S ");
					}else{
						if(j == maze.getEnding_X() && i == maze.getEnding_Y()){
							System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| E " : "  E ");
						}else{
							if((maze.getMaze()[j][i] & 16) != 0 && state.getPokemons().contains(new Pokemon(j, i)))
							{
								System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "| P " : "  P ");
							}else{
								System.out.print((maze.getMaze()[j][i] & 8) == 0 ? "|   " : "    ");
							}
						}
					}
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < maze.getX(); j++) {
			System.out.print("+---");
		}
		System.out.println("+");
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
		return Math.max(maxDistance, ((PokemonState)node.getState()).getEgg_hatch());
	}
	
	public static int getDistance(int x1, int y1, int x2, int y2){
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[]args){
		Maze  maze = GenMaze();
		
//		int [][] customMaze = new int[4][4];
//		customMaze[0][0] = 20;customMaze[0][1] = 6;customMaze[0][2] = 5;customMaze[0][3] = 20;
//		customMaze[1][0] = 10;customMaze[1][1] = 9;customMaze[1][2] = 12;customMaze[1][3] = 12;
//		customMaze[2][0] = 6;customMaze[2][1] = 5;customMaze[2][2] = 10;customMaze[2][3] = 13;
//		customMaze[3][0] = 24;customMaze[3][1] = 10;customMaze[3][2] = 3;customMaze[3][3] = 25;
//		maze.setMaze(customMaze);
//		ArrayList<Pokemon>customPokemons = new ArrayList<Pokemon>();
//		customPokemons.add(new Pokemon(3,0));customPokemons.add(new Pokemon(3,3));customPokemons.add(new Pokemon(0,3));customPokemons.add(new Pokemon(0,0));
//		maze.setPokemons(customPokemons);
//		maze.setEgg_hatch(5);
//		maze.setStarting_X(1);maze.setStarting_Y(2);
//		maze.setEnding_X(0);maze.setEnding_Y(1);
		maze.display();
		//maze.print();
		
		ArrayList<Object> result = Search(maze,"BF",true);
		
		for(int i = ((ArrayList<String>)result.get(0)).size() - 1; i>=0;i--){
			System.out.print(((ArrayList<String>)result.get(0)).get(i)+" | ");
		}
		System.out.println("");
		System.out.println("Expanded nodes = "+result.get(2));
		System.out.println("Actions performed = "+result.get(1));
		
	}
	
}
