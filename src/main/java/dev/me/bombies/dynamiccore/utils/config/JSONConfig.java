package dev.me.bombies.dynamiccore.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.me.bombies.dynamiccore.constants.JSONConfigFile;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class JSONConfig {
    /**
     * The configuration file itself
     */
    private final JSONConfigFile file;

    /**
     * Constructor for a JSON configuration file. If the file doesn't already exist
     * it will be created and the default configuration will be initialized automatically
     * @param file Specific JSON file to construct
     */
    public JSONConfig(JSONConfigFile file) {
        try {
            if (!Files.exists(Path.of(file.toString()))) {
                new File(file.toString()).createNewFile();
                this.initConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.file = file;
    }

    /**
     * Initialize JSON config
     */
    protected abstract void initConfig();

    protected JSONObject getJSONObject() {
        return new JSONObject(getJSON());
    }

    /**
     * Get the JSON config in a string
     * @return Stringified JSON config
     */
    public String getJSON() {
        return GeneralUtils.getFileContent(file.toString());
    }

    /**
     * Set the file content of a specific JSON configuration file
     * @param object JSONObject containing all the JSON info
     */
    public void setJSON(JSONObject object) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(object.toString());
            GeneralUtils.setFileContent(file.toString(), gson.toJson(je));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the path in which the JSON file is location
     * @return Stringified path of JSON file
     */
    public String getPath() {
        return file.toString();
    }

    /**
     * Check if a JSON Array has a specific object
     * @param array JSON Array to be searched
     * @param object Object to search for
     * @return True if the object is found, else vice versa.
     */
    public boolean arrayHasObject(JSONArray array, Object object) {
        for (int i = 0; i < array.length(); i++)
            if (array.get(i).equals(object))
                return true;
        return false;
    }

    /**
     * Gets the index of a specific object in a JSON array.
     * @param array Array to be searched
     * @param object Object to search for
     * @return Index where the object is found. -1 Will be returned
     * if some unexpected error occurs
     * @throws NullPointerException Thrown when the object couldn't be found in the array
     */
    public int getIndexOfObjectInArray(JSONArray array, Object object) {
        if (!arrayHasObject(array, object))
            throw new NullPointerException("There was no such object found in the array!");

        for (int i = 0; i < array.length(); i++)
            if (array.get(i).equals(object))
                return i;
        return -1;
    }
}
