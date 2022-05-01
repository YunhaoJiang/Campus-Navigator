public class FDPlace implements IPlace {

	public String name;
	public int x;
	public int y;

	public FDPlace(String name, int x, int y) {
		this.name = name;
		this.x  = x;
		this.y = y;
	}
 
	
	public int compareTo(IPlace o) {
		return (int) Math.abs(Math.sqrt(Math.pow(this.x - o.getX(), 2) + Math.pow(this.y - o.getY(), 2)));
	}

	@Override
	public String getName() {
		return this.name;
	}
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
