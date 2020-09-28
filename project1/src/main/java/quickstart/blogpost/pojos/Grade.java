package quickstart.blogpost.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    private ObjectId id;

    @BsonProperty(value = "student_id")
    private Double studentId;

    @BsonProperty(value = "class_id")
    private Double classId;

    private List<Score> scores;
}
