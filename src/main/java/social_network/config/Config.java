package social_network.config;

import java.io.FileReader;
import java.util.Properties;
import java.io.IOException;
public class Config {
    public static String CONFIG_LOCATION=Config.class.getClassLoader()
            .getResource("config.properties").getFile();

    public static Properties getProperties() {
        Properties properties = new Properties();
        try{
            properties.load(new FileReader(CONFIG_LOCATION));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config properties");
        }
    }
}
