
public class BDPHPlace implements IPlace{

    String name;
    int latitude;
    int longitude;
    
    public BDPHPlace(String name, int latitude, int longitude) {
    	this.name = name;
    	this.latitude = latitude;
    	this.longitude = longitude;
    }


	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
