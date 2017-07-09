package main.speechrecognition.recognizers.watson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by alvaro on 6/22/17.
 */
public class WatsonConfiguration {

    public static final String CONFIG_PROPERTIES_PATH = "config/config.properties";
    private InputStream input;
    private Properties properties;

    public WatsonConfiguration() {
        properties = new Properties();
        input = loadPropertyFile();
        load();
    }

    private void load() {
        if (input != null) {
            try {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private InputStream loadPropertyFile() {
        try {
            input = new FileInputStream(CONFIG_PROPERTIES_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return input;
    }

    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value.isEmpty()) {
            value = defaultValue;
        }
        return value;
    }

    public String getUsername() {
        return getProperty("username");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getEndPoint() {
        return getProperty("endpoint");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
