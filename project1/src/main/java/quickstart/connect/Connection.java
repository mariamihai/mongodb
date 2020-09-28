package quickstart.connect;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.json.JsonWriterSettings;

import java.util.ResourceBundle;

public class Connection {

    public final static JsonWriterSettings PRETTY_PRINT = JsonWriterSettings.builder().indent(true).build();
    private final static ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");


    private static String getString(String key) {
        return PROPERTIES.getString(key);
    }

    public static MongoClient getConnection() {
        return MongoClients.create(getString("mongodb.uri"));
    }
}
