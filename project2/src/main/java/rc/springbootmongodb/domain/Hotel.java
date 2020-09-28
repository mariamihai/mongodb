package rc.springbootmongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Hotels")
public class Hotel {

    @Id
    private String id;
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Integer pricePerNight;
    private Address address;
    private List<Review> reviews;
}
