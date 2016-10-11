import java.util.ArrayList;

public class PokemonState {

	private int x;
	private int y;
	private int direction;// 0 -> North, 1-> East, 2-> South, 3->West
	private ArrayList<Pokemon>Pokemons;// list of pokemons NOT YET acquired
	private int egg_hatch;//time needed for egg to hatch
	
	public PokemonState(int x, int y, int direction, ArrayList<Pokemon>Pokemons, int egg_hatch){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.Pokemons = Pokemons;
		this.egg_hatch = egg_hatch;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public ArrayList<Pokemon> getPokemons() {
		return Pokemons;
	}
	public void setPokemons(ArrayList<Pokemon> pokemons) {
		Pokemons = pokemons;
	}
	public int getEgg_hatch() {
		return egg_hatch;
	}
	public void setEgg_hatch(int egg_hatch) {
		this.egg_hatch = egg_hatch;
	}
	
}
