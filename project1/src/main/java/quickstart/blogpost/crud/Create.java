package quickstart.blogpost.crud;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import quickstart.config.Config;
import quickstart.config.connect.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Create {

    private static Random random = new Random();


    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnection();

        // Connecting to a specific collection
        MongoCollection<Document> gradeCollection = mongoClient.getDatabase("sample_training").getCollection("grades");

        System.out.println("Number of documents from sample_training.grades collection is " + gradeCollection.countDocuments());

        insertNewStudent(gradeCollection);
        insertNewStudentWithMultipleGrades(gradeCollection);

        System.out.println("Number of documents after inserts is " + gradeCollection.countDocuments());

        mongoClient.close();
    }

    private static void insertNewStudent(MongoCollection<Document> gradeCollection) {
        // making sure student_id, class_id and score are doubles
        gradeCollection.insertOne(generateNewGrade(10000d, 1d));
    }

    private static void insertNewStudentWithMultipleGrades(MongoCollection<Document> gradeCollection) {
        List<Document> grades = new ArrayList<>();

        for(double classId = 1d; classId <= 10d; classId++) {
            grades.add(generateNewGrade(10001d, classId));
        }

        gradeCollection.insertMany(grades,
                                   // Making sure we don't get DuplicateKeyException for inserting documents with duplicate _id
                                   new InsertManyOptions().ordered(false));
    }

    private static Document generateNewGrade(double studentId, double classId) {
        // The Java driver would have generated the _id filed but it's a good practice to set it
        return new Document("_id", new ObjectId())
                                .append("student_id", studentId)
                                .append("class_id", classId)
                                .append("scores", createScores());
    }

    private static List<Document> createScores() {
        return Arrays.asList(
                new Document("type", "exam").append("score", random.nextDouble() * 100),
                new Document("type", "quiz").append("score", random.nextDouble() * 100),
                new Document("type", "homework").append("score", random.nextDouble() * 100),
                new Document("type", "homework").append("score", random.nextDouble() * 100));
    }
}
