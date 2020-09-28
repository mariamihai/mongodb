package rc.springbootmongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    private String userName;
    private Integer rating;
    private Boolean approved;
}
