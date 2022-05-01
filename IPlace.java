/**
 * Instances of classes that implement this interface represents a place
 * that can be stored, sorted, and used with the accessors below.
 */
public interface IPlace extends Comparable<IPlace> {
    /**
     * constructor args
     * Place(String name, double latitude, double longitude)
     * name - name of the place
     * latitude - latitude of the place geographically
     * longitude - longitude of the place geographically
     */

    String getName(); // return the name of the place

}
