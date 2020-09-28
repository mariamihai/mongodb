package rc.springbootmongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rc.springbootmongodb.domain.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    List<Hotel> findByPricePerNightLessThan(Integer maxPrice);

}
