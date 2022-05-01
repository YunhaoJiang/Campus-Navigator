import java.util.List;
import java.io.FileNotFoundException;

/**
 * Instances of classes that implement this interface can be used to load a list of places from a
 * specified tsv source file.
 * The following parts are used to load these show attributes:
 *        name: what the place is called
 *        latitude: geographical latitude of the place
 *        longitude: geographical longitude of the place
 */
public interface IPlaceLoader {

    /**
     * This method returns the array of places described in the tsv file
     * @param filePath the file path of the tsv file
     * @return the array of places described in the tsv file
     * @throws FileNotFoundException if the filepath is not a tsv file or cannot be read
     */
    List<IPlace> loadPlaces(String filePath) throws FileNotFoundException;

}
