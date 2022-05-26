package App.Parsers;

import App.Entities.Camin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaminJSONParser implements JSONParser<Camin> {
    private ObjectMapper objectMapper;

    public CaminJSONParser() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Camin mapObject(String path) throws IOException {

        return objectMapper.readValue(new File(path), Camin.class);

    }

    @Override
    public List<Camin> mapObjects(String path) throws IOException {
        return objectMapper.readValue(new File(path), new TypeReference<List<Camin>>() {
        });
    }

    @Override
    public List<Camin> parseFile(String path) throws IOException {
        if (isValid(path)) {
            if (isArray(path)) {
                return mapObjects(path);
            }
            List<Camin> caminList = new ArrayList<>();
            caminList.add(mapObject(path));
            return caminList;
        }
        return null;
    }
}
