package quickstart.blogpost.config.connect;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.ResourceBundle;

public class Connection {

    private final static ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");

    private static String getString(String key) {
        return PROPERTIES.getString(key);
    }

    public static MongoClient getConnection() {
        return MongoClients.create(getString("mongodb.uri"));
    }

}