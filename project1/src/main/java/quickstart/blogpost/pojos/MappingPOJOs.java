package quickstart.blogpost.pojos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import quickstart.config.Config;
import quickstart.config.connect.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MappingPOJOs {

    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnectionForPOJOs();

        // Connecting to a specific collection
        MongoCollection<Grade> gradeCollection = mongoClient.getDatabase("sample_training")
                                                            .getCollection("grades", Grade.class);

        createNewGrade(gradeCollection);
        findGrade(gradeCollection);
        findOneAndReplace(gradeCollection);
        delete(gradeCollection);
    }

    private static void createNewGrade(MongoCollection<Grade> gradeCollection) {
        Score homeworkScore = Score.builder()
                .type("homework")
                .score(50d)
                .build();

        Grade grade = Grade.builder()
                .studentId(10003d)
                .classId(10d)
                .scores(Collections.singletonList(homeworkScore))
                .build();

        gradeCollection.insertOne(grade);
        System.out.println("Created new grade.");
    }

    private static void findGrade(MongoCollection<Grade> gradeCollection) {
        Grade grade = gradeCollection.find(Filters.eq("student_id", 10003d)).first();

        System.out.println("Found grade: " + grade);
    }

    private static void findOneAndReplace(MongoCollection<Grade> gradeCollection) {
        Grade grade = gradeCollection.find(Filters.eq("student_id", 10003d)).first();
        System.out.println("Initial grade: " + grade);

        List<Score> scores = new ArrayList<>(grade.getScores());
        Score newScore = Score.builder().type("exam").score(42d).build();
        scores.add(newScore);
        grade.setScores(scores);

        Document filterByGradeId = new Document("_id", grade.getId());
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
                                                                                .returnDocument(ReturnDocument.AFTER);
        Grade updatedGrade = gradeCollection.findOneAndReplace(filterByGradeId, grade, returnDocAfterReplace);

        System.out.println("Updated grade: " + updatedGrade);
    }

    private static void delete(MongoCollection<Grade> gradeCollection) {
        Grade grade = gradeCollection.find(Filters.eq("student_id", 10003d)).first();
        Document filterByGradeId = new Document("_id", grade.getId());

        DeleteResult deleteResult = gradeCollection.deleteOne(filterByGradeId);

        System.out.println("Delete result: " + deleteResult);
    }
}
