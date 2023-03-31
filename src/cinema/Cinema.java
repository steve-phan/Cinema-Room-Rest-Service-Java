package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Cinema {
    private final int totalRows = 9;
    private final int totalColumns = 9;
    private final List<Seat> availableSeats = IntStream.rangeClosed(1, totalRows)
            .boxed()
            .flatMap(row -> IntStream.rangeClosed(1, totalColumns)
                    .mapToObj(column -> new Seat(row, column, true)))
            .collect(Collectors.toList());


    public int getTotalRows() {
        return totalRows;
    }


    public int getTotalColumns() {

        return totalColumns;
    }

   @JsonIgnore
    public List<Seat> getSeats() {
        return List.copyOf(availableSeats);
    }


    public List<Seat> getAvailableSeats() {
        return availableSeats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }

//     @JsonIgnore
//     public int getTotalAvailableSeats() {
////        return getAvailableSeats().stream().
//     }

    @JsonIgnore
    public List<Seat> getPurChasedSeats() {
        return availableSeats.stream().filter(seat -> {
//            System.out.printf("The current is is %b\n", !seat.isAvailable());
            if(!seat.isAvailable()) {
                System.out.println(seat.getToken());
            }
          return  !seat.isAvailable();
        }).collect(Collectors.toList());

    }


}
