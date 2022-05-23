package App.Parsers;

import App.Entities.Camera;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraJSONParser implements JSONParser<Camera> {

    private ObjectMapper objectMapper;

    public CameraJSONParser() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Camera mapObject(String path) {
        try {
            return objectMapper.readValue(new File(path), Camera.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Camera> mapObjects(String path) {
        try {
            return objectMapper.readValue(new File(path), new TypeReference<List<Camera>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Camera> parseFile(String path) {
        if (isValid(path)) {
            if (isArray(path)) {
                return mapObjects(path);
            }
            List<Camera> cameraList = new ArrayList<>();
            cameraList.add(mapObject(path));
            return cameraList;
        }
        return null;
    }
}
