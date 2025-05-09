package edu.ntnu.flightbookingbackend.config;

import edu.ntnu.flightbookingbackend.enums.Role;
import edu.ntnu.flightbookingbackend.model.Airline;
import edu.ntnu.flightbookingbackend.model.Airport;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Handles initialization of data in the database at startup.
 */
@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final PriceRepository priceRepository;
    private final FlightRepository flightRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, AirlineRepository airlineRepository, AirportRepository airportRepository, PriceRepository priceRepository, FlightRepository flightRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.priceRepository = priceRepository;
        this.flightRepository = flightRepository;
    }

    /**
     * Creates default users if they do not already exist in the database.
     */
    public void initializeUsers() {
        if (!userRepository.existsByEmail("chuck@gmail.com")) {
            User admin = new User();
            admin.setEmail("chuck@gmail.com");
            admin.setPassword(passwordEncoder.encode("Nunchucks2024"));
            admin.setPhone("+4799887766");
            admin.setFirstName("Chuck");
            admin.setLastName("Norris");
            admin.setDateOfBirth("02.02.1992");
            admin.setCountry("United States");
            admin.setGender("Male");
            admin.setRole(Role.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("Admin user Chuck created.");
        }

        if (!userRepository.existsByEmail("dave@gmail.com")) {
            User user = new User();
            user.setEmail("dave@gmail.com");
            user.setPassword(passwordEncoder.encode("Dangerous2024"));
            user.setPhone("+4798765432");
            user.setFirstName("Dave");
            user.setLastName("Norman");
            user.setDateOfBirth("01.01.1991");
            user.setCountry("Norway");
            user.setGender("Male");
            user.setRole(Role.USER);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            System.out.println("Regular user Dave created.");
        }
    }

    /**
     * Creates default airlines if they do not already exist in the database.
     */
    private void addAirlineIfNotExists(String name, String code, String country, String logoFileName) {
        if (!airlineRepository.existsByCode(code)) {
            Airline airline = new Airline();
            airline.setName(name);
            airline.setCode(code);
            airline.setCountry(country);
            airline.setLogoFileName(logoFileName);
            airlineRepository.save(airline);
            System.out.println("Airline " + name + " created.");
        }
    }

    public void initializeAirlines() {
        addAirlineIfNotExists("Delta Airlines", "DL", "United States", "delta");
        addAirlineIfNotExists("Norwegian Air Shuttle", "DY", "Norway", "norwegian");
        addAirlineIfNotExists("KLM Royal Dutch Airlines", "KL", "Netherlands", "klm");
        addAirlineIfNotExists("British Airways", "BA", "United Kingdom", "british_airways");
        addAirlineIfNotExists("Swiss International Air Lines", "LX", "Switzerland", "swiss");
        addAirlineIfNotExists("ITA Airways", "AZ", "Italy", "ita_airways");
        addAirlineIfNotExists("American Airlines", "AA", "United States", "american_airlines");
        addAirlineIfNotExists("Lufthansa", "LH", "Germany", "lufthansa");
        addAirlineIfNotExists("Air France", "AF", "France", "air_france");
        addAirlineIfNotExists("Emirates", "EK", "United Arab Emirates", "emirates");
        addAirlineIfNotExists("Qatar Airways", "QR", "Qatar", "qatar_airways");
        addAirlineIfNotExists("Singapore Airlines", "SQ", "Singapore", "singapore_airlines");
        addAirlineIfNotExists("Wizz", "W6", "Hungary", "wizz");
        addAirlineIfNotExists("LOT", "LO", "Poland", "lot");
        addAirlineIfNotExists("Turkish Airlines", "TK", "Turkey", "turkish_airlines");
        addAirlineIfNotExists("Brussels Airlines", "SN", "Belgium", "brussels_airlines");
        addAirlineIfNotExists("United Airlines", "UA", "United States", "united_airlines");
    }

    private void addAirportIfNotExists(String name, String code, String city, String country) {
        if (!airportRepository.existsByCode(code)) {
            Airport airport = new Airport();
            airport.setName(name);
            airport.setCode(code);
            airport.setCity(city);
            airport.setCountry(country);
            airportRepository.save(airport);
            System.out.println("Airport " + name + " created.");
        }
    }

    public void initializeAirports() {
        addAirportIfNotExists("John F. Kennedy International Airport", "JFK", "New York", "United States");
        addAirportIfNotExists("Los Angeles International Airport", "LAX", "Los Angeles", "United States");
        addAirportIfNotExists("Oslo Gardermoen Airport", "OSL", "Oslo", "Norway");
        addAirportIfNotExists("Ålesund Airport", "AES", "Ålesund", "Norway");
        addAirportIfNotExists("Amsterdam Schiphol Airport", "AMS", "Amsterdam", "Netherlands");
        addAirportIfNotExists("London Heathrow Airport", "LHR", "London", "United Kingdom");
        addAirportIfNotExists("Zurich Airport", "ZRH", "Zurich", "Switzerland");
        addAirportIfNotExists("Leonardo da Vinci-Fiumicino Airport", "FCO", "Rome", "Italy");
        addAirportIfNotExists("Charles de Gaulle Airport", "CDG", "Paris", "France");
        addAirportIfNotExists("Dallas/Fort Worth International Airport", "DFW", "Dallas", "United States");
        addAirportIfNotExists("O'Hare International Airport", "ORD", "Chicago", "United States");
        addAirportIfNotExists("Frankfurt Airport", "FRA", "Frankfurt", "Germany");
        addAirportIfNotExists("Haneda Airport", "HND", "Tokyo", "Japan");
        addAirportIfNotExists("Dubai International Airport", "DXB", "Dubai", "United Arab Emirates");
        addAirportIfNotExists("Hamad International Airport", "DOH", "Doha", "Qatar");
        addAirportIfNotExists("Sydney Kingsford Smith Airport", "SYD", "Sydney", "Australia");
        addAirportIfNotExists("Singapore Changi Airport", "SIN", "Singapore", "Singapore");
    }

    private void addPrice(String classType, float price, String provider, String currency) {
        if (!priceRepository.existsByPriceAndPriceProviderName(price, provider)) {
            Price p = new Price();
            p.setClassType(classType);
            p.setPrice(price);
            p.setPriceProviderName(provider);
            p.setCurrency(currency);
            priceRepository.save(p);
            System.out.println("Price " + classType + " saved.");
        }
    }

    public void initializePrices() {
        addPrice("Economy", 150, "SkyScanner", "USD");
        addPrice("Economy", 175, "Expedia", "USD");
        addPrice("Economy Flex", 1200, "Momondo", "NOK");
        addPrice("Economy Flex", 1400, "Kayak", "NOK");
        addPrice("Economy", 90, "Orbitz", "EUR");
        addPrice("Economy", 110, "CheapOair", "EUR");
        addPrice("Premium Economy", 350, "OneTravel", "GBP");

    }
}
