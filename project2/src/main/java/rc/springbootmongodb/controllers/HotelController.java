package rc.springbootmongodb.controllers;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rc.springbootmongodb.domain.Hotel;
import rc.springbootmongodb.domain.QHotel;
import rc.springbootmongodb.repositories.HotelRepository;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelRepository hotelRepository;

    @GetMapping("/all")
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel findById(@PathVariable("id") String id) throws Exception {
        return hotelRepository.findById(id).orElseThrow(Exception::new);
    }

    @GetMapping("/price/{maxPrice}")
    public List<Hotel> getByPricePerNightLessThan(@PathVariable("maxPrice") Integer maxPrice) {
        return hotelRepository.findByPricePerNightLessThan(maxPrice);
    }

    @GetMapping("/address/city/{city}")
    public List<Hotel> getByCity(@PathVariable("city") String city) {
        return hotelRepository.findByCity(city);
    }

    @GetMapping("/address/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country) {
        QHotel qHotel = new QHotel("hotel");

        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        return (List<Hotel>) hotelRepository.findAll(filterByCountry);
    }

    @GetMapping("/recommendation")
    public List<Hotel> getRecommended() {
        final int maxPrice = 100;
        final int minRating = 7;

        QHotel qHotel = new QHotel("hotel");

        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        return (List<Hotel>) hotelRepository.findAll(filterByPrice.and(filterByRating));
    }

    @PostMapping
    public Hotel insert(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @PutMapping("/{id}")
    public Hotel update(@PathVariable("id") String id, @RequestBody Hotel newHotel) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        existingHotel.setName(newHotel.getName());
        existingHotel.setPricePerNight(newHotel.getPricePerNight());
        existingHotel.setAddress(newHotel.getAddress());
        existingHotel.setReviews(newHotel.getReviews());

        return hotelRepository.save(existingHotel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        hotelRepository.deleteById(id);
    }
}
