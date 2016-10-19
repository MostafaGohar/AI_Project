
public class Pokemon {

	private int x;
	private int y;
	
	public Pokemon(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean isEqual(Pokemon p){
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
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
	public boolean equals(Object p){
		if(this.x == ((Pokemon)p).x && this.y == ((Pokemon)p).y)
			return true;
		return false;
	}
	public int hashCode(){
		return 0;
	}
}
