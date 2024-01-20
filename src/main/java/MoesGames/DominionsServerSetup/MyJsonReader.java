package MoesGames.DominionsServerSetup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import MoesGames.DominionsServerSetup.Nations.Nation;
import MoesGames.DominionsServerSetup.Nations.NationLibrary;

public class MyJsonReader {
	
	// Function to read the content of a file as a string
    private static String readFileAsString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }
    
    public static void main(String[] args) {
    	NationLibrary library = new NationLibrary();
		loadNationsFromJson("./resources/nations.json", library);
		library.printNations();
	}
    
    public static void loadNationsFromJson(String filePath, NationLibrary library) {
    	library.setNations(loadJsonInfoIntoNations(filePath));
    }
	
	
    public static ArrayList<Nation> loadJsonInfoIntoNations(String filePath) {
    	ArrayList<Nation> nationList = new ArrayList<>();
    	String content = "";
    	// read file
    	// Specify the path to your file
       // String filePath = "./resources/nations.json";

        try {
            // Read the contents of the file into a string
            content = readFileAsString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	
        // Your JSON string
        String jsonString = content;//"{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse JSON string to JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            JsonNode childrenArray = jsonNode.get("children");

            // Iterate through the array
            for (JsonNode childNode : childrenArray) {       
            	int idNumber = 0;
                String name = childNode.get("name").asText();
                int age = 0;
                switch (name.substring(0, 2)) {
				case "EA":
					age = 1;
					break;
				case "MA":
					age = 2;
					break;
				case "LA":
					age = 3;
					break;
				default:
					age = 0;
					break;
				}
                String idString = childNode.get("description").asText();   
                try {
                	idString = idString.substring(7);
					idNumber = Integer.parseInt(idString); 
				} catch (Exception e) {

				}
                if (age > 0 && idNumber > 0) {
					nationList.add(new Nation(age, name.substring(2), idNumber));
				}
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return nationList;
    }
}
