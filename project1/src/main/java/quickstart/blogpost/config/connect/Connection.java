package quickstart.blogpost.config.connect;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ResourceBundle;

public class Connection {

    private final static ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");
    private final static String URI = "mongodb.uri";

    private static String getString(String key) {
        return PROPERTIES.getString(key);
    }

    private static ConnectionString getConnectionString(String key) {
        return new ConnectionString(getString(key));
    }

    public static MongoClient getConnection() {
        return MongoClients.create(getString(URI));
    }

    public static MongoClient getConnectionForPOJOs() {
        // Include codec to handle the translation to and from BSON
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());

        // Add the default codec registry which contains all the default codecs
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(getConnectionString(URI))
                .codecRegistry(codecRegistry)
                .build();

        return MongoClients.create(clientSettings);
    }
}