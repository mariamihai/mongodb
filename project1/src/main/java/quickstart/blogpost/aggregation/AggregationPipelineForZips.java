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

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

import static quickstart.config.Config.PRETTY_PRINT;

public class AggregationPipelineForZips {

    public static void main(String[] args) {
        Config.setLoggingLevel();
        MongoClient mongoClient = Connection.getConnection();

        MongoCollection<Document> zips = mongoClient.getDatabase("sample_training")
                                                    .getCollection("zips");

        List<Document> results = zips.aggregate(Arrays.asList(match(), group(), project(), sort(), limit()))
                .into(new ArrayList<>());

        System.out.println("The most densely populated cities in Texas are: ");
        results.forEach(doc -> System.out.println(doc.toJson(PRETTY_PRINT)));
    }

    /*
    {
      state: "TX"
    }
     */
    private static Bson match() {
        return Aggregates.match(eq("state", "TX"));
    }

    /*
    {
      _id: "$city",
      totalPop: {
        $sum: "$pop"
      }
    }
     */
    private static Bson group() {
        return Aggregates.group("$city", sum("totalPop", "$pop"));
    }

    /*
    {
      _id: false,
      totalPop: true,
      city: "$_id"
    }
     */
    private static Bson project() {
        return Aggregates.project(fields(excludeId(),
                                  include("totalPop"),
                                  computed("city", "$_id")));
    }

    /*
    {
      totalPop: -1
    }
     */
    private static Bson sort() {
        return Aggregates.sort(descending("totalPop"));
    }

    /*
    {
      3
    }
     */
    private static Bson limit() {
        return Aggregates.limit(3);
    }
}
