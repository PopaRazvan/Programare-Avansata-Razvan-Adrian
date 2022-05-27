package app.parsers;

import app.entities.Camera;
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
    public Camera mapObject(String path) throws IOException {
        return objectMapper.readValue(new File(path), Camera.class);
    }

    @Override
    public List<Camera> mapObjects(String path) throws IOException {
        return objectMapper.readValue(new File(path), new TypeReference<List<Camera>>() {
        });
    }

    @Override
    public List<Camera> parseFile(String path) throws IOException {
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
