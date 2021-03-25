package Meeting3.Deserialization;

        import java.util.Map;
        import java.util.Set;

        import org.testng.annotations.Test;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.JsonMappingException;
        import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAllKeysFromJsonObject {

    @Test
    public void getAllKeysFromJsonObjectUsingMap() throws JsonMappingException, JsonProcessingException {

        String jsonObject = "{\r\n" + "  \"firstName\": \"Animesh\",\r\n" + "  \"lastName\": \"Prashant\"\r\n" + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserializing into a Map
        Map<String, String> parsedJsonObject = objectMapper.readValue(jsonObject,
                new TypeReference<Map<String, String>>() {
                });
        // Get all keys
        Set<String> allKeys = parsedJsonObject.keySet();
        System.out.println("All keys are : ");
        allKeys.stream().forEach(k -> System.out.println(k));

    }

}