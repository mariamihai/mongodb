package quickstart.blogpost.aggregation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import quickstart.config.Config;
import quickstart.config.connect.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Sorts.descending;
import static quickstart.config.Config.PRETTY_PRINT;

public class AggregationPipelineForPosts {

    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnection();

        MongoCollection<Document> posts = mongoClient.getDatabase("sample_training")
                                                     .getCollection("posts");

        List<Document> results = posts.aggregate(Arrays.asList(unwind(), group(), sort(), limit(), project()))
                                      .into(new ArrayList<>());

        System.out.println("The most popular tags and their post titles are: ");
        results.forEach(doc -> System.out.println(doc.toJson(PRETTY_PRINT)));
    }

    /*
    {
      path: "$tags"
    }
     */
    private static Bson unwind() {
        return Aggregates.unwind("$tags");
    }

    /*
    {
      _id: "$tags",
      count: {
        $sum: 1
      },
      titles: {
        $push: "$title"
      }
    }
     */
    private static Bson group() {
        return Aggregates.group("$tags",
                                sum("count", 1L),
                                addToSet("titles", "$title"));
    }

    /*
    {
      count: -1
    }
    */
    private static Bson sort() {
        return Aggregates.sort(descending("count"));
    }

    /*
    {
      3
    }
     */
    private static Bson limit() {
        return Aggregates.limit(3);
    }

    /*
    {
      _id: false,
      city: "$_id",
      count: true,
      titles: true
    }
     */
    private static Bson project() {
        return Aggregates.project(fields(excludeId(),
                                  computed("tag", "$_id"),
                                  include("count", "titles")));
    }
}