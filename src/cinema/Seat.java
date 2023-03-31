package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Seat {
    private int row;
    private int column;
    private int price;
    private boolean isAvailable;

    private UUID token;

    public Seat(int row, int column, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        this.token = null;
        price = row >= 5 ? 8 : 10;
    }

    public Seat(@JsonProperty("row") int row, @JsonProperty("column") int column) {
        this.row = row;
        this.column = column;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }


    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    @JsonIgnore
    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

}

