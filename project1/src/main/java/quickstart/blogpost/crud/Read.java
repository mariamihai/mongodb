package quickstart.blogpost.crud;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import quickstart.blogpost.config.Config;
import quickstart.blogpost.config.connect.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Read {

    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnection();

        // Connecting to a specific collection
        MongoCollection<Document> gradeCollection = mongoClient.getDatabase("sample_training").getCollection("grades");

        findSingleGradeForStudent(gradeCollection);
        findMultipleGradesForStudent(gradeCollection);

        readARangeOfDocuments(gradeCollection);

        implementPagination(gradeCollection);

        mongoClient.close();
    }

    private static void findSingleGradeForStudent(MongoCollection<Document> gradeCollection) {
        Document student = gradeCollection.find(new Document("student_id", 10000)).first();
        System.out.println("Student 10000 has one class with grades: " + student.toJson());

        // Read with filter
        student = gradeCollection.find(Filters.eq("student_id", 10000)).first();
        System.out.println("Student 10000 has one class with grades: " + student.toJson());
    }

    private static void findMultipleGradesForStudent(MongoCollection<Document> gradeCollection) {
        System.out.println("\nStudent 10001 has next classes: ");
        gradeCollection.find(new Document("student_id", 10001)).forEach((Consumer<? super Document>) d -> System.out.println(d.toJson()));
    }

    private static void readARangeOfDocuments(MongoCollection<Document> gradeCollection) {
        System.out.println("\nFinding a range of documents:");

        System.out.println("Without helpers");
        FindIterable<Document> iterable = gradeCollection.find(new Document("student_id", new Document("$gte", 10000)));
        for (Document value : iterable) {
            System.out.println(value.toJson());
        }

        System.out.println("With the Filters.gte() helper");
        List<Document> gradesForStudent = gradeCollection.find(Filters.gte("student_id", 10000)).into(new ArrayList<>());
        gradesForStudent.forEach(g -> System.out.println(g.toJson()));

        System.out.println("With Consumer");
        Consumer<Document> printConsumer = document -> System.out.println(document.toJson());
        gradeCollection.find(Filters.gte("student_id", 10000)).forEach(printConsumer);
    }

    private static void implementPagination(MongoCollection<Document> gradeCollection) {
        List<Document> docs = gradeCollection
                                    .find(Filters.and(Filters.eq("student_id", 10001),
                                                       Filters.lte("class_id", 5)))
                                    .projection(Projections.fields(
                                                    Projections.excludeId(),
                                                    Projections.include("class_id",
                                                            "student_id")))
                                    .sort(Sorts.descending("class_id"))
                                    .skip(2)
                                    .limit(2)
                                    .into(new ArrayList<>());

        System.out.println("Student sorted, skipped, limited and projected: ");
        for (Document student : docs) {
            System.out.println(student.toJson());
        }
    }
}