import java.util.Collections;
import java.util.Arrays;
 
public class Maze {
	private final int x;
	private final int y;
	//
	//Maze is an 2D array of integers,
	//to check if a cell has a North opening, OR it with 1, 2 for South, 4 for East and 8 for South
	//to check if a cell has a pokemon, OR it with 16
	private final int[][] maze;

	int starting_X;
	int starting_Y;
	int ending_X;
	int ending_Y;
 
	public Maze(int x, int y, int starting_X, int starting_Y, int ending_X, int ending_Y) {
		this.x = x;
		this.y = y;
		this.starting_X = starting_X;
		this.starting_Y = starting_Y;
		this.ending_X = ending_X;
		this.ending_Y = ending_Y;
		maze = new int[this.x][this.y];
		generateMaze(0, 0);
		generatePokemon();
	}

	private void generatePokemon() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if(Math.random()<0.1 &&
					!(i == starting_Y && j == starting_X) && 
					!(i == ending_Y && j == ending_X))
				{
					maze[j][i] |= 16;
				}
			}
		}
	}

	public void display() {
		for (int i = 0; i < y; i++) {
			// draw the north edge -- & 1 to check if the cell has a north edge or not (north bit is 1)
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge -- & 8 to check if the cell has a west edge or not (west bit is 8)
			for (int j = 0; j < x; j++) {
				if(j == starting_X && i == starting_Y){
					System.out.print((maze[j][i] & 8) == 0 ? "| S " : "  S ");
				}else{
					if(j == ending_X && i == ending_Y){
						System.out.print((maze[j][i] & 8) == 0 ? "| E " : "  E ");
					}else{
						if((maze[j][i] & 16) != 0)
						{
							System.out.print((maze[j][i] & 8) == 0 ? "| P " : "  P ");
						}else{
							System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
						}
					}
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	private void print() {
		for(int i = 0;i<x;i++){
			for(int j = 0;j<y;j++){
				if(maze[i][j] <= 9)
					System.out.print(" | "+ maze[i][j] +" | ");
				else
					System.out.print(" | "+ maze[i][j] +"| ");
			}
			System.out.println("");
		}
	}
 
	private void generateMaze(int cx, int cy) {
		Direction[] dirs = Direction.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (Direction dir : dirs) {
			int nx = cx + dir.dx; //get the x of the new cell
			int ny = cy + dir.dy; //get the y of the new cell
			//if the cell is inside the maze && has not been assigned a value yet
			if ((nx>=0 && nx<x) && (ny>=0 && ny<y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
 
	private enum Direction {
		//North, South, East, West all have 'bits' which are 1,2,4,8
		//these numbers are chosen to help in making a condition to check if the cell has an edge in a certain direction by just
		//ORing the cell number with the bit of the direction in question
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private Direction opposite;
 
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private Direction(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
 
	public int[][] getMaze() {
		return maze;
	}
	public int getStarting_X() {
		return starting_X;
	}

	public void setStarting_X(int starting_X) {
		this.starting_X = starting_X;
	}

	public int getStarting_Y() {
		return starting_Y;
	}

	public void setStarting_Y(int starting_Y) {
		this.starting_Y = starting_Y;
	}

	public int getEnding_X() {
		return ending_X;
	}

	public void setEnding_X(int ending_X) {
		this.ending_X = ending_X;
	}

	public int getEnding_Y() {
		return ending_Y;
	}

	public void setEnding_Y(int ending_Y) {
		this.ending_Y = ending_Y;
	}
	
	public static void main(String[] args) {
		int x = (int)(Math.random()*25);
		if(x<5)x=5;
		int y = (int)(Math.random()*25);
		if(y<5)y=5;
		
		int starting_X_temp =(int)(Math.random()*x);
		int starting_Y_temp =(int)(Math.random()*y);
		int ending_X_temp = 0;
		int ending_Y_temp = 0;
		while(true){
			ending_X_temp =(int)(Math.random()*x);
			ending_Y_temp =(int)(Math.random()*y);
			if(!(starting_X_temp == ending_X_temp && starting_Y_temp == ending_Y_temp))
				break;
		}
		Maze maze = new Maze(x, y, starting_X_temp, starting_Y_temp, ending_X_temp, ending_Y_temp);
		maze.display();
		maze.print();
	}
 
}