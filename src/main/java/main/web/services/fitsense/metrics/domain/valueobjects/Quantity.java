package main.web.services.fitsense.metrics.domain.valueobjects;

public record Quantity(Double quantity) {
    public  Quantity {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null or empty");
        }
    }
}
