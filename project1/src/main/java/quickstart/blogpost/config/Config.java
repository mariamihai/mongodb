package quickstart.blogpost.config;

import org.bson.json.JsonWriterSettings;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    public final static JsonWriterSettings PRETTY_PRINT = JsonWriterSettings.builder().indent(true).build();

    public static void setLoggingLevel() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
    }
}
