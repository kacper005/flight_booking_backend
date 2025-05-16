package edu.ntnu.flightbookingbackend.config;

import edu.ntnu.flightbookingbackend.enums.FlightStatus;
import edu.ntnu.flightbookingbackend.enums.Role;
import edu.ntnu.flightbookingbackend.model.Airline;
import edu.ntnu.flightbookingbackend.model.Airport;
import edu.ntnu.flightbookingbackend.model.Feedback;
import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.AirlineRepository;
import edu.ntnu.flightbookingbackend.repository.AirportRepository;
import edu.ntnu.flightbookingbackend.repository.FeedbackRepository;
import edu.ntnu.flightbookingbackend.repository.FlightRepository;
import edu.ntnu.flightbookingbackend.repository.PriceRepository;
import edu.ntnu.flightbookingbackend.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Handles initialization of data in the database at startup. */
@Component
public class DataInitializer {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AirlineRepository airlineRepository;
  private final AirportRepository airportRepository;
  private final PriceRepository priceRepository;
  private final FlightRepository flightRepository;
  private final FeedbackRepository feedbackRepository;

  /**
   * Constructor for DataInitializer.
   *
   * @param userRepository User repository for accessing user data
   * @param passwordEncoder Password encoder for encoding passwords
   * @param airlineRepository Airline repository for accessing airline data
   * @param airportRepository Airport repository for accessing airport data
   * @param priceRepository Price repository for accessing price data
   * @param flightRepository Flight repository for accessing flight data
   * @param feedbackRepository Feedback repository for accessing feedback data
   */
  public DataInitializer(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AirlineRepository airlineRepository,
      AirportRepository airportRepository,
      PriceRepository priceRepository,
      FlightRepository flightRepository,
      FeedbackRepository feedbackRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.airlineRepository = airlineRepository;
    this.airportRepository = airportRepository;
    this.priceRepository = priceRepository;
    this.flightRepository = flightRepository;
    this.feedbackRepository = feedbackRepository;
  }

  /** Creates default users if they do not already exist in the database. */
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

    if (!userRepository.existsByEmail("johndoe@hotmail.com")) {
      User user = new User();
      user.setEmail("johndoe@hotmail.com");
      user.setPassword(passwordEncoder.encode("johniscool11"));
      user.setPhone("+4798765222");
      user.setFirstName("John");
      user.setLastName("Doe");
      user.setDateOfBirth("01.01.1991");
      user.setCountry("Norway");
      user.setGender("Male");
      user.setRole(Role.USER);
      user.setCreatedAt(LocalDateTime.now());
      userRepository.save(user);
      System.out.println("Regular user John Doe created.");
    }

    if (!userRepository.existsByEmail("bob.n@yahoo.com")) {
      User user = new User();
      user.setEmail("bob.n@yahoo.com");
      user.setPassword(passwordEncoder.encode("qwerty123"));
      user.setPhone("+4798674221");
      user.setFirstName("Bob");
      user.setLastName("Normann");
      user.setDateOfBirth("02.02.2002");
      user.setCountry("Norway");
      user.setGender("Male");
      user.setRole(Role.USER);
      user.setCreatedAt(LocalDateTime.now());
      userRepository.save(user);
      System.out.println("Regular user Bob Normann created.");
    }

    if (!userRepository.existsByEmail("sarah.nypd@gmail.com")) {
      User user = new User();
      user.setEmail("sarag.nypd@gmail.com");
      user.setPassword(passwordEncoder.encode("BrooklynNine9"));
      user.setPhone("+189067534");
      user.setFirstName("Sarah");
      user.setLastName("Nydalen");
      user.setDateOfBirth("03.06.1989");
      user.setCountry("United States");
      user.setGender("Female");
      user.setRole(Role.USER);
      user.setCreatedAt(LocalDateTime.now());
      userRepository.save(user);
      System.out.println("Regular user Sarah Nydalen created.");
    }
  }

  /** Creates default airlines if they do not already exist in the database. */
  private void addAirlineIfNotExists(
      String name, String code, String country, String logoFileName) {
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

  /** Initializes the database with a set of predefined airlines. */
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

  /**
   * Adds an airport to the database if it does not already exist.
   *
   * @param name Name of the airport
   * @param code Code of the airport
   * @param city City where the airport is located
   * @param country Country where the airport is located
   */
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

  /** Initializes the database with a set of predefined airports. */
  public void initializeAirports() {
    addAirportIfNotExists(
        "John F. Kennedy International Airport", "JFK", "New York", "United States");
    addAirportIfNotExists(
        "Los Angeles International Airport", "LAX", "Los Angeles", "United States");
    addAirportIfNotExists("Oslo Gardermoen Airport", "OSL", "Oslo", "Norway");
    addAirportIfNotExists("Ålesund Airport", "AES", "Ålesund", "Norway");
    addAirportIfNotExists("Amsterdam Schiphol Airport", "AMS", "Amsterdam", "Netherlands");
    addAirportIfNotExists("London Heathrow Airport", "LHR", "London", "United Kingdom");
    addAirportIfNotExists("Zurich Airport", "ZRH", "Zurich", "Switzerland");
    addAirportIfNotExists("Leonardo da Vinci-Fiumicino Airport", "FCO", "Rome", "Italy");
    addAirportIfNotExists("Charles de Gaulle Airport", "CDG", "Paris", "France");
    addAirportIfNotExists(
        "Dallas/Fort Worth International Airport", "DFW", "Dallas", "United States");
    addAirportIfNotExists("O'Hare International Airport", "ORD", "Chicago", "United States");
    addAirportIfNotExists("Frankfurt Airport", "FRA", "Frankfurt", "Germany");
    addAirportIfNotExists("Haneda Airport", "HND", "Tokyo", "Japan");
    addAirportIfNotExists("Dubai International Airport", "DXB", "Dubai", "United Arab Emirates");
    addAirportIfNotExists("Hamad International Airport", "DOH", "Doha", "Qatar");
    addAirportIfNotExists("Sydney Kingsford Smith Airport", "SYD", "Sydney", "Australia");
    addAirportIfNotExists("Singapore Changi Airport", "SIN", "Singapore", "Singapore");
  }

  /**
   * Adds a price to the database if it does not already exist.
   *
   * @param classType Class type of the flight
   * @param price Price of the flight
   * @param provider Name of the price provider
   * @param currency Currency of the price
   */
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

  /** Initializes the database with a set of predefined prices. */
  public void initializePrices() {
    addPrice("Economy", 150, "SkyScanner", "USD");
    addPrice("Economy", 175, "Expedia", "USD");
    addPrice("Economy Flex", 1200, "Momondo", "NOK");
    addPrice("Economy Flex", 1400, "Kayak", "NOK");
    addPrice("Economy", 90, "Orbitz", "EUR");
    addPrice("Economy", 110, "CheapOair", "EUR");
    addPrice("Premium Economy", 350, "OneTravel", "GBP");
    addPrice("Premium Economy", 400, "Travelocity", "GBP");
    addPrice("Economy", 120, "Google Flights", "EUR");
    addPrice("Economy", 140, "JustFly", "EUR");
    addPrice("Economy", 80, "eDreams", "EUR");
    addPrice("Economy", 100, "Priceline", "EUR");
    addPrice("Main Cabin", 300, "American Airlines Website", "USD");
    addPrice("Main Cabin", 320, "Orbitz", "USD");
    addPrice("Economy", 450, "Lufthansa Website", "EUR");
    addPrice("Economy", 470, "Kayak", "EUR");
    addPrice("Economy", 800, "Air France Website", "EUR");
    addPrice("Economy", 820, "Expedia", "EUR");
    addPrice("Economy", 1000, "Emirates Website", "USD");
    addPrice("Economy", 1025, "SkyScanner", "USD");
    addPrice("Economy", 1500, "Qatar Airways Website", "USD");
    addPrice("Economy", 1520, "CheapOair", "USD");
    addPrice("Economy", 2000, "Singapore Airlines Website", "USD");
    addPrice("Economy", 2050, "Google Flights", "SGD");
  }

  /**
   * Seed data for flights.
   *
   * @param flightNumber Flight number for the flight
   * @param departureTime Departure time of the flight
   * @param arrivalTime Arrival time of the flight
   * @param roundTrip Whether the flight is a round trip or not
   * @param status Status of the flight
   * @param extraFeatures Extra features available on the flight
   * @param availableClasses Available classes on the flight
   * @param airlineId ID of the airline
   * @param departureAirportId ID of the departure airport
   * @param arrivalAirportId ID of the arrival airport
   * @param priceIds List of price IDs associated with the flight
   */
  record FlightSeedData(
      String flightNumber,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      boolean roundTrip,
      String status,
      String extraFeatures,
      String availableClasses,
      Integer airlineId,
      Integer departureAirportId,
      Integer arrivalAirportId,
      List<Integer> priceIds) {}

  /** Initializes the database with a set of predefined flights and prices. */
  public void initializeFlights() {
    List<FlightSeedData> flights =
        List.of(
            new FlightSeedData(
                "DL425",
                LocalDateTime.of(2025, 8, 15, 8, 0),
                LocalDateTime.of(2025, 8, 15, 11, 30),
                false,
                "SCHEDULED",
                "Complimentary Wi-Fi, Seat-back Screens, Free Snacks",
                "Economy",
                1,
                1,
                2,
                List.of(1, 2)),
            new FlightSeedData(
                "DY708",
                LocalDateTime.of(2025, 9, 5, 9, 0),
                LocalDateTime.of(2025, 9, 5, 10, 0),
                true,
                "SCHEDULED",
                "Free Breakfast, Seat Reservation, Fast Track",
                "Economy Flex",
                2,
                3,
                4,
                List.of(3, 4)),
            new FlightSeedData(
                "DY709",
                LocalDateTime.of(2025, 9, 12, 11, 0),
                LocalDateTime.of(2025, 9, 12, 12, 0),
                true,
                "SCHEDULED",
                "Free Breakfast, Seat Reservation, Fast Track",
                "Economy Flex",
                2,
                4,
                3,
                List.of(3, 4)),
            new FlightSeedData(
                "KL605",
                LocalDateTime.of(2025, 7, 21, 7, 0),
                LocalDateTime.of(2025, 7, 21, 8, 0),
                false,
                "SCHEDULED",
                "In-flight Magazine, Complimentary Meals, Extra Legroom",
                "Economy, Business",
                3,
                5,
                6,
                List.of(5, 6)),
            new FlightSeedData(
                "BA116",
                LocalDateTime.of(2025, 10, 10, 10, 0),
                LocalDateTime.of(2025, 10, 10, 13, 0),
                true,
                "SCHEDULED",
                "Lounge Access, Priority Boarding, Enhanced Entertainment System",
                "Premium Economy, Business",
                4,
                6,
                1,
                List.of(7, 8)),
            new FlightSeedData(
                "BA117",
                LocalDateTime.of(2025, 10, 17, 15, 0),
                LocalDateTime.of(2025, 10, 17, 18, 0),
                true,
                "SCHEDULED",
                "Lounge Access, Priority Boarding, Enhanced Entertainment System",
                "Premium Economy, Business",
                4,
                1,
                6,
                List.of(7, 8)),
            new FlightSeedData(
                "LX110",
                LocalDateTime.of(2025, 8, 1, 6, 0),
                LocalDateTime.of(2025, 8, 1, 7, 30),
                false,
                "SCHEDULED",
                "Swiss Chocolates, Complimentary Drinks, Priority Check-in",
                "Economy, Business",
                5,
                7,
                5,
                List.of(9, 10)),
            new FlightSeedData(
                "AZ560",
                LocalDateTime.of(2025, 11, 15, 9, 0),
                LocalDateTime.of(2025, 11, 15, 11, 0),
                true,
                "SCHEDULED",
                "Italian Cuisine, Reserved Overhead Space, ITA Airways Lounges",
                "Economy, Business",
                6,
                8,
                9,
                List.of(11, 12)),
            new FlightSeedData(
                "AZ561",
                LocalDateTime.of(2025, 11, 22, 12, 0),
                LocalDateTime.of(2025, 11, 22, 14, 0),
                true,
                "SCHEDULED",
                "Italian Cuisine, Reserved Overhead Space, ITA Airways Lounges",
                "Economy, Business",
                6,
                9,
                8,
                List.of(11, 12)),
            new FlightSeedData(
                "AA220",
                LocalDateTime.of(2025, 6, 15, 7, 0),
                LocalDateTime.of(2025, 6, 15, 9, 30),
                true,
                "SCHEDULED",
                "Wi-Fi, Extra legroom, Complimentary Snacks",
                "Main Cabin, Main Cabin Extra",
                7,
                10,
                11,
                List.of(13, 14)),
            new FlightSeedData(
                "AA221",
                LocalDateTime.of(2025, 6, 20, 10, 30),
                LocalDateTime.of(2025, 6, 20, 13, 0),
                true,
                "SCHEDULED",
                "Wi-Fi, Extra legroom, Complimentary Snacks",
                "Main Cabin, Main Cabin Extra",
                7,
                11,
                10,
                List.of(13, 14)),
            new FlightSeedData(
                "LH445",
                LocalDateTime.of(2025, 7, 1, 8, 45),
                LocalDateTime.of(2025, 7, 1, 15, 0),
                false,
                "SCHEDULED",
                "On-demand Video, Gourmet Meals, Lounge Access",
                "Economy, Premium Economy, Business",
                8,
                12,
                1,
                List.of(15, 16)),
            new FlightSeedData(
                "AF123",
                LocalDateTime.of(2025, 5, 10, 10, 0),
                LocalDateTime.of(2025, 5, 10, 23, 30),
                true,
                "SCHEDULED",
                "Michelin-starred Menus, Flat-bed Seats, Personal Coat Service",
                "Economy, Premium Economy, La Première",
                9,
                9,
                13,
                List.of(17, 18)),
            new FlightSeedData(
                "AF124",
                LocalDateTime.of(2025, 5, 24, 14, 0),
                LocalDateTime.of(2025, 5, 24, 19, 30),
                true,
                "SCHEDULED",
                "Michelin-starred Menus, Flat-bed Seats, Personal Coat Service",
                "Economy, Premium Economy, La Première",
                9,
                13,
                9,
                List.of(17, 18)),
            new FlightSeedData(
                "EK204",
                LocalDateTime.of(2025, 8, 15, 8, 0),
                LocalDateTime.of(2025, 8, 15, 12, 0),
                false,
                "SCHEDULED",
                "Shower Spas, Onboard Lounge, Private Suites",
                "Economy, Business, First Class",
                10,
                14,
                6,
                List.of(19, 20)),
            new FlightSeedData(
                "QR905",
                LocalDateTime.of(2025, 9, 1, 2, 0),
                LocalDateTime.of(2025, 9, 1, 20, 0),
                true,
                "SCHEDULED",
                "Award-winning Cuisine, 4000 Entertainment Options, Fully Lie-flat Beds",
                "Economy, Business Class, Qsuite",
                11,
                15,
                16,
                List.of(21, 22)),
            new FlightSeedData(
                "QR906",
                LocalDateTime.of(2025, 9, 15, 6, 0),
                LocalDateTime.of(2025, 9, 15, 14, 0),
                true,
                "SCHEDULED",
                "Award-winning Cuisine, 4000 Entertainment Options, Fully Lie-flat Beds",
                "Economy, Business Class, Qsuite",
                11,
                16,
                15,
                List.of(21, 22)),
            new FlightSeedData(
                "SQ26",
                LocalDateTime.of(2025, 10, 20, 9, 0),
                LocalDateTime.of(2025, 10, 20, 21, 0),
                true,
                "SCHEDULED",
                "Book the Cook Service, Standalone Beds, Givenchy Amenities",
                "Economy, Premium Economy, Suites",
                12,
                17,
                1,
                List.of(23, 24)),
            new FlightSeedData(
                "SQ27",
                LocalDateTime.of(2025, 10, 30, 11, 0),
                LocalDateTime.of(2025, 10, 30, 23, 0),
                true,
                "SCHEDULED",
                "Book the Cook Service, Standalone Beds, Givenchy Amenities",
                "Economy, Premium Economy, Suites",
                12,
                1,
                17,
                List.of(23, 24)));

    for (FlightSeedData seed : flights) {
      if (flightRepository.existsByFlightNumber(seed.flightNumber())) {
        continue;
      }

      Airline airline = airlineRepository.findById(seed.airlineId()).orElse(null);
      Airport dep = airportRepository.findById(seed.departureAirportId()).orElse(null);
      Airport arr = airportRepository.findById(seed.arrivalAirportId()).orElse(null);

      if (airline == null || dep == null || arr == null) {
        continue;
      }

      Flight flight = new Flight();
      flight.setFlightNumber(seed.flightNumber());
      flight.setDepartureTime(seed.departureTime());
      flight.setArrivalTime(seed.arrivalTime());
      flight.setRoundTrip(seed.roundTrip());
      flight.setStatus(FlightStatus.valueOf(seed.status()));
      flight.setExtraFeatures(seed.extraFeatures());
      flight.setAvailableClasses(seed.availableClasses());
      flight.setAirline(airline);
      flight.setDepartureAirport(dep);
      flight.setArrivalAirport(arr);

      Set<Price> prices =
          seed.priceIds().stream()
              .map(priceRepository::findById)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toSet());

      flight.setPrices(new ArrayList<>(prices));
      flightRepository.save(flight);
      System.out.println(" Flight " + seed.flightNumber() + " with prices saved.");
    }
  }

  /**
   * Builds a feedback object.
   *
   * @param user User who provided the feedback
   * @param rating Rating given by the user
   * @param comment Comment provided by the user
   * @return Feedback object
   */
  private Feedback buildFeedback(User user, int rating, String comment) {
    Feedback feedback = new Feedback();
    feedback.setUser(user);
    feedback.setRating(rating);
    feedback.setComment(comment);
    feedback.setCreatedAt(LocalDateTime.now().toString());
    return feedback;
  }

    /** Initializes the database with a set of predefined feedback. */
  public void initializeFeedback() {
    if (feedbackRepository.count() > 0) {
      System.out.println("Feedback already seeded.");
      return;
    }

    Optional<User> user2 = userRepository.findById(2);
    Optional<User> user3 = userRepository.findById(3);
    Optional<User> user4 = userRepository.findById(4);
    Optional<User> user5 = userRepository.findById(5);

    if (user2.isEmpty() || user3.isEmpty() || user4.isEmpty() || user5.isEmpty()) {
      System.out.println("Cannot seed feedback - users not found.");
      return;
    }

    List<Feedback> feedbacks =
        List.of(
            buildFeedback(user2.get(), 5, "Great experience! Easy to use and book flights."),
            buildFeedback(user3.get(), 4, "Good service, but could improve."),
            buildFeedback(user4.get(), 3, "Average experience. Nothing special, but okay."),
            buildFeedback(user5.get(), 2, "Not satisfied with the service."));
    feedbackRepository.saveAll(feedbacks);
    System.out.println("Feedback seeded.");
  }
}
