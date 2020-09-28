package rc.springbootmongodb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rc.springbootmongodb.domain.Hotel;
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