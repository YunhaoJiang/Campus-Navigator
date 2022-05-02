import java.io.*;

import java.util.ArrayList;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlaceLoader implements IPlaceLoader {
    protected List<IPlace> location;
    
    public int imageSizeX = 1450;
    public int imageSizeY = 717;

    public PlaceLoader()
    {
        this.location = new ArrayList<>();
    }
   

    /**
     * This method returns the list of words described in the xml file
     *
     * @param filePath the file path of the xml file
     * @return the list of words described in the xml file
     * @throws FileNotFoundException if the filepath is not a xml file or cannot be read
     */
    @Override
    public List<IPlace> loadPlaces(String filePath) throws FileNotFoundException {
    	JSONParser parser = new JSONParser();
   	 
    	try {
     
    		Object obj = parser.parse(new FileReader(filePath));
     
    		JSONObject jsonObject = (JSONObject) obj;
     
    		
    		
    		//Object
    		JSONArray array = (JSONArray) jsonObject.get("places");
    		for(int i=0; i<array.size(); i++){
    			JSONObject result = (JSONObject) array.get(i);

    			
    			Place p = new Place((String)result.get("name"),
							(int)((Double)result.get("x")*imageSizeX+25),
							(int)((Double)result.get("y")*imageSizeY+108));
    			location.add(p);
    		}
    		
    		
    		
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
        

        return location;
    }
}
