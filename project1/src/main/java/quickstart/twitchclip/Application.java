package quickstart.twitchclip;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Application {

    private static final String connectionString = "mongodb+srv://<user>:<pass>@cluster0.oebob.mongodb.net/";

    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        // Create a MongoDB connection
        try(MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Obtain all available databases
            useCursorForListingDatabaseStrings(mongoClient);
            transformCursor(mongoClient);
            printDatabasesAsDocuments(mongoClient);

            // Play with db
            MongoCollection<Document> cookies = mongoClient.getDatabase("xmas").getCollection("cookies");
            // Create and delete
            deleteDocuments(cookies);
//            createDocument(cookies);
            createDocuments(cookies);
            // Update
            updateDocuments(cookies);
            // Read
            findDocuments(cookies);
        }
    }

    private static void createDocument(MongoCollection<Document> cookies) {
        Document doc = new Document("name", "chocolate chips");
        cookies.insertOne(doc);
    }


    private static void createDocuments(MongoCollection<Document> cookies) {
        List<Document> cookieList = new ArrayList<>();

        List<String> ingredients = Arrays.asList("flour", "sugar", "eggs", "butter", "colouring");
        for(int i = 0; i < 10; i++) {
            cookieList.add(new Document("cookie_id", i)
                                    .append("color", "pink")
                                    .append("ingredients", ingredients));
        }

        cookies.insertMany(cookieList);
    }

    private static void deleteDocuments(MongoCollection<Document> cookies) {
        // Delete all existing cookies before adding the new ones
        cookies.deleteMany(new Document());

        // Delete only cookies of color pink
//        cookies.deleteMany(new Document("color", "pink"));

        // Delete based o n list
//        cookies.deleteMany(Filters.in("color", Arrays.asList("pink", "blue", "orange")));
    }

    private static void updateDocuments(MongoCollection<Document> cookies) {
        System.out.println("Update operations");

        // Update all documents
//        cookies.updateMany(new Document(), Updates.set("calories", 200));

        // Update all cookies onew by one to have a random calories number
        Random random = new Random(0);
        List<Document> cookieList = cookies.find().into(new ArrayList<>());
        cookieList.forEach(c -> {
            Object id = c.get("_id");

            // Returns previous version of the document
//            Document cookie = cookies.findOneAndUpdate(new Document("_id", id),
//                                                       Updates.set("calories", random.nextInt(1000)));

            // Returns current version of the document
            Document filter = new Document("_id", id);
            Bson update = Updates.set("calories", random.nextInt(1000));
            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document cookie = cookies.findOneAndUpdate(filter, update, findOneAndUpdateOptions);
            System.out.println(cookie.toJson());
        });
    }

    private static void findDocuments(MongoCollection<Document> cookies) {
        System.out.println("Find operations");

        List<Document> lowCaloriesCookies = cookies.find(Filters.lte("calories", 500)).into(new ArrayList<>());

        lowCaloriesCookies.forEach(d -> System.out.println(d.toJson()));
    }

    private static void useCursorForListingDatabaseStrings(MongoClient mongoClient) {
        System.out.println("useCursorForListingDatabaseStrings:");

        MongoIterable<String> strings = mongoClient.listDatabaseNames();
        MongoCursor<String> cursor = strings.cursor();

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void transformCursor(MongoClient mongoClient) {
        System.out.println("transformCursor:");

        List<String> databaseStrings = mongoClient.listDatabaseNames().into(new ArrayList<>());

        System.out.println(databaseStrings);
    }

    private static void printDatabasesAsDocuments(MongoClient mongoClient) {
        System.out.println("printDatabasesAsDocuments:");

        List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList<>());

        dbDocuments.forEach(doc -> System.out.println(doc.toJson()));
    }
}
