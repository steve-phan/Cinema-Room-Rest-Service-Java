package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinemaService.getAvailableSeat();
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat seatRequest) {
        return cinemaService.purchaseSeat(seatRequest);

    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Map<String, UUID> ticketRequest) {
        UUID token = ticketRequest.get("token");
        return cinemaService.returnTicket(token);
    }

    @PostMapping("/stats")
    public ResponseEntity getStats(@RequestParam(value = "password", required = false) String password) {
        return cinemaService.getStatistics(password);
    }


}
