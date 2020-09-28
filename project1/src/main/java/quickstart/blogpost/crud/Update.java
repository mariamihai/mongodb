package quickstart.blogpost.crud;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import quickstart.blogpost.config.Config;
import quickstart.blogpost.config.connect.Connection;

import static quickstart.blogpost.config.Config.PRETTY_PRINT;

public class Update {

    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnection();

        // Connecting to a specific collection
        MongoCollection<Document> gradeCollection = mongoClient.getDatabase("sample_training").getCollection("grades");

        updateOneDocument(gradeCollection);
        upsert(gradeCollection);

        updateManyDocuments(gradeCollection);

        findOneAndUpdate(gradeCollection);

        mongoClient.close();
    }

    private static void updateOneDocument(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10000);
        Bson updateOperation = Updates.set("Comments", "You should learn MongoDB!");

        UpdateResult updateResult = gradeCollection.updateOne(filter, updateOperation);
        System.out.println("Updating the doc with {\"student_id\":10000}. Adding comment.");
        System.out.println(updateResult);
        System.out.println(gradeCollection.find(filter).first().toJson(PRETTY_PRINT));
    }

    private static void upsert(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.and(Filters.eq("student_id", 10002d),
                                  Filters.eq("class_id", 10d));
        Bson updateOperation = Updates.push("comments", "You will learn a lot if you read the MongoDB blog!");

        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult updateResult = gradeCollection.updateOne(filter, updateOperation, options);

        System.out.println("\nUpsert document with {\"student_id\":10002.0, \"class_id\": 10.0} because it doesn't exist yet.");
        System.out.println(updateResult);
        System.out.println(gradeCollection.find(filter).first().toJson(PRETTY_PRINT));
    }

    private static void updateManyDocuments(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10001);
        Bson updateOperation = Updates.push("comments", "You will learn a lot if you read the MongoDB blog!");

        UpdateResult updateResult = gradeCollection.updateMany(filter, updateOperation);
        System.out.println("\nUpdating all the documents with {\"student_id\":10001}.");
        System.out.println(updateResult);
    }

    private static void findOneAndUpdate(MongoCollection<Document> gradeCollection) {
        Bson filter = Filters.eq("student_id", 10000);

        Bson update1 = Updates.inc("x", 10); // increment x by 10. As x doesn't exist yet, x=10.
        Bson update2 = Updates.rename("class_id", "new_class_id"); // rename variable "class_id" in "new_class_id".
        Bson update3 = Updates.mul("scores.0.score", 2); // multiply the first score in the array by 2.
        Bson update4 = Updates.addToSet("comments", "This comment is uniq"); // creating an array with a comment.
        Bson update5 = Updates.addToSet("comments", "This comment is uniq"); // using addToSet so no effect.
        Bson updates = Updates.combine(update1, update2, update3, update4, update5);

        // returns the old version of the document before the update.
        Document oldVersion = gradeCollection.findOneAndUpdate(filter, updates);
        System.out.println("\nFindOneAndUpdate operation. Printing the old version by default:");
        System.out.println(oldVersion.toJson(PRETTY_PRINT));

        // but I can also request the new version
        filter = Filters.eq("student_id", 10001);
        FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
        Document newVersion = gradeCollection.findOneAndUpdate(filter, updates, optionAfter);
        System.out.println("\nFindOneAndUpdate operation. But we can also ask for the new version of the doc:");
        System.out.println(newVersion.toJson(PRETTY_PRINT));
    }
}