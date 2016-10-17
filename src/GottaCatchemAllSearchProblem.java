import java.util.ArrayList;

public class GottaCatchemAllSearchProblem extends SearchProblem{
	
	private Maze maze;
	
	public GottaCatchemAllSearchProblem(Maze maze, PokemonState initial_state,String[] operators){
		this.maze = maze;
		this.setInitial_state(initial_state);
		this.setOperators(operators);
	}
	
	

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	@Override
	boolean goal_test(Object state) {
		if((((PokemonState)state).getX() == maze.getEnding_X() && ((PokemonState)state).getY() == maze.getEnding_Y())
				&& ((PokemonState)state).getEgg_hatch() <= 0 
				&& ((PokemonState)state).getPokemons().isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	int path_cost(SearchTreeNode node) {
		// TODO Auto-generated method stub
		return node.getPath_cost_from_root();
	}

	@Override
	ArrayList<SearchTreeNode> expand(SearchTreeNode node) {
		
		ArrayList<SearchTreeNode>result = new ArrayList<SearchTreeNode>();
		SearchTreeNode new_node = null;
		PokemonState new_state = null;
		for(int i = 0;i<getOperators().length;i++){
			switch(getOperators()[i]){
			case "R": 
				new_state = new PokemonState(((PokemonState)node.getState()).getX(),
						((PokemonState)node.getState()).getY(),
						(((PokemonState)node.getState()).getDirection()+1) % 4,
						((PokemonState)node.getState()).getPokemons(),
						((PokemonState)node.getState()).getEgg_hatch());
				new_node = new SearchTreeNode(node, node.getDepth()+1, node.getPath_cost_from_root() + 1, "R", new_state);
				result.add(new_node.cloneNode());
				;break;
			case "L": 
				new_state = new PokemonState(((PokemonState)node.getState()).getX(),
						((PokemonState)node.getState()).getY(),
						(((((PokemonState)node.getState()).getDirection()-1) % 4)+4) % 4,
						((PokemonState)node.getState()).getPokemons(),
						((PokemonState)node.getState()).getEgg_hatch());
				new_node = new SearchTreeNode(node, node.getDepth()+1, node.getPath_cost_from_root() + 1, "L", new_state);
				result.add(new_node.cloneNode());
				;break;
			case "F": 
				if((maze.canMoveForward(((PokemonState)node.getState()).getX(),
						((PokemonState)node.getState()).getY(),
						((PokemonState)node.getState()).getDirection()))){
					int new_x = ((PokemonState)node.getState()).getX();
					int new_y = ((PokemonState)node.getState()).getY();
					switch(((PokemonState)node.getState()).getDirection()){
						case 0: new_y--;break;
						case 1: new_x++;break;
						case 2: new_y++;break;
						case 3: new_x--;break;
					}
					ArrayList<Pokemon>pokemons = (((PokemonState) node.getState()).getPokemons());
					if(maze.hasPokemon(new_x, new_y)){
						for(int k = 0;k<pokemons.size();k++){
							if(pokemons.get(k).isEqual(new Pokemon(new_x,new_y))){
								pokemons.remove(k);
								break;
							}
						}
					}
					new_state = new PokemonState(new_x, new_y,
							(((PokemonState)node.getState()).getDirection()),
							pokemons,
							((PokemonState)node.getState()).getEgg_hatch()-1);
					new_node = new SearchTreeNode(node, node.getDepth()+1, node.getPath_cost_from_root() + 2, "F", new_state);
					result.add(new_node.cloneNode());
				}
//				else{
//					System.out.println("Can't move");
//				}
						;break;
			}
		}
		return result;
	}

}
