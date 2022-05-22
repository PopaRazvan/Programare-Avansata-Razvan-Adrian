package App.FileInput;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JSONParser<T> {
    public T mapObject(String path);

    public List<T> mapObjects(String path);

    public List<T> parseFile(String path);

    public default boolean isValid(String path){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readTree(new File(path));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public default boolean isArray(String path){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode tree = mapper.readTree(new File(path));
            if(!tree.isArray()) return false;
        }catch (IOException e) {
            return false;
        }
        return true;
    }
}
