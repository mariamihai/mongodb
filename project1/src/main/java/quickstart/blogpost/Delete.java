package quickstart.blogpost;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import quickstart.connect.Connection;

import static quickstart.connect.Connection.PRETTY_PRINT;

public class Delete {

    private static JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();

    public static void main(String[] args) {
        MongoClient mongoClient = Connection.getConnection();

        // Connecting to a specific collection
        MongoCollection<Document> gradeCollection = mongoClient.getDatabase("sample_training").getCollection("grades");

        deleteOneDocument(gradeCollection);
        findOneAndDelete(gradeCollection);
        deleteManyDocuments(gradeCollection);

//        deleteCollectionAndMetadata(gradeCollection);

        mongoClient.close();
    }


    private static void deleteOneDocument(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10000);
        DeleteResult deleteResult = gradeCollection.deleteOne(filter);

        System.out.println("Deleted: " + deleteResult);
    }

    private static void findOneAndDelete(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10002);
        Document document = gradeCollection.findOneAndDelete(filter);

        System.out.println(document.toJson(PRETTY_PRINT));
    }

    private static void deleteManyDocuments(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10000);
        DeleteResult deleteResult = gradeCollection.deleteMany(filter);

        System.out.println("Deleted: " + deleteResult);
    }

    private static void deleteCollectionAndMetadata(MongoCollection<Document> gradeCollection) {
        gradeCollection.drop();
    }
}