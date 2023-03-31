package cinema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    @Autowired
    private Cinema cinema;

    public Cinema getAvailableSeat() {
        return cinema;
    }

    public ResponseEntity purchaseSeat(Seat seat) {

        ResponseEntity response = getSeatInfo(seat.getRow(), seat.getColumn());
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            UUID token = UUID.randomUUID();
            changeSeatAvailable(seat, false, token);
            Seat purchasedSeat = (Seat) response.getBody();
            Map<String, Object> responseBody = Map.of(
                    "token", token,
                    "ticket", purchasedSeat
            );
            return ResponseEntity.ok(responseBody);

        }

        return response;
    }

    public synchronized void changeSeatAvailable(Seat seat, boolean availability, UUID token) {

        cinema.getAvailableSeats()
                .stream()
                .forEach(s -> {
                            if (s.getColumn() == seat.getColumn() && s.getRow() == seat.getRow()) {
                                s.setAvailable(availability);
                                s.setToken(token);
                            }
                        }
                );
    }

    public ResponseEntity getSeatInfo(int row, int column) {
        List<Seat> seats = cinema.getSeats();
        Optional<Seat> optionalSeat = seats.stream()
                .filter(seat -> seat.getRow() == row && seat.getColumn() == column).findFirst();


        if (!optionalSeat.isPresent()) {
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        if (!optionalSeat.get().isAvailable()) {
            return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(optionalSeat.get(), HttpStatus.ACCEPTED);
    }

    // Return Ticket

    public ResponseEntity returnTicket(UUID token) {
        Optional<Seat> optionalSeat = checkToken(token);
        if (!optionalSeat.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Wrong token!"));
        }

        Seat purchasedSeat = optionalSeat.get();
        Map<String, Object> responseBody = Map.of(
                "returned_ticket", purchasedSeat
        );

        purchasedSeat.setAvailable(true);
        purchasedSeat.setToken(null);


        return ResponseEntity.ok(responseBody);

    }

    public Optional<Seat> checkToken(UUID token) {
        return cinema.getPurChasedSeats()
                .stream()
                .filter(s -> s.getToken().equals(token)).findFirst();

    }

    public ResponseEntity purchasedTickets() {
        return ResponseEntity.ok(cinema.getPurChasedSeats());
    }

    // Get Statistics

    public ResponseEntity getStatistics(String password) {
        if (!"super_secret".equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error",
                    "The password is wrong!"
            ));
        }
        int availableSeats = cinema.getAvailableSeats().size();
        int purchasedSeats = cinema.getPurChasedSeats().size();
        int currentIncome = 0;
        for (Seat seat : cinema.getPurChasedSeats()) {
            currentIncome += seat.getPrice();
        }
        Map<String, Integer> statisticsBody = Map.of(
                "current_income", currentIncome,
                "number_of_available_seats", availableSeats,
                "number_of_purchased_tickets", purchasedSeats

        );

        return ResponseEntity.ok().body(statisticsBody);
    }
}
