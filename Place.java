// --== CS400 Project three W3 ==--
// Name: Sangho Jeon
// Role: Data Wrangler
// CSL Username: sangho
// Email: sjeon36@wisc.edu
// Lecture #: 002 @1:00pm

public class Place implements IPlace{
  private final String name;
  private final int x;
  private final int y;

  public Place(String name, int x, int y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  /**
   * constructor args
   * Place(String name, double latitude, double longitude)
   * name - name of the place
   * latitude - latitude of the place geographically
   * longitude - longitude of the place geographically
   */
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
