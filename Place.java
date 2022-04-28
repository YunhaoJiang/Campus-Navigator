

public class Place implements IPlace {
    final private  String name;
    private int X;
    private int Y;

    public Place(String name, int X, int Y) {
        this.name = name;
        this.X = X;
        this.Y = Y;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getX() {
        return this.X;
    }

    @Override
    public int getY() {
        return this.Y;
    }




}
