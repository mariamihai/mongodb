package rc.springbootmongodb.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rc.springbootmongodb.domain.Address;
import rc.springbootmongodb.domain.Hotel;
import rc.springbootmongodb.domain.Review;
import rc.springbootmongodb.repositories.HotelRepository;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DBSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;

    @Override
    public void run(String... args) {
        Hotel hotel1 = Hotel.builder()
                .name("Hotel 1")
                .pricePerNight(130)
                .address(Address.builder().city("Paris").country("France").build())
                .reviews(Arrays.asList(Review.builder().userName("John").rating(9).approved(true).build(),
                        Review.builder().userName("Mary").rating(8).approved(false).build()))
                .build();
        Hotel hotel2 = Hotel.builder()
                .name("Hotel 2")
                .pricePerNight(90)
                .address(Address.builder().city("London").country("UK").build())
                .reviews(Collections.singletonList(Review.builder().userName("Alex").rating(8).approved(false).build()))
                .build();
        Hotel hotel3 = Hotel.builder()
                .name("Hotel 3")
                .pricePerNight(200)
                .address(Address.builder().city("Rome").country("Italy").build())
                .build();

        // Drop existing hotels if it exists
        hotelRepository.deleteAll();

        // Add the new hotels
        hotelRepository.saveAll(Arrays.asList(hotel1, hotel2, hotel3));
    }
}
