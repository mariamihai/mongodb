package rc.springbootmongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rc.springbootmongodb.domain.Hotel;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

}
