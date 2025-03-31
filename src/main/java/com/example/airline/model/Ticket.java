package com.example.airline.model;

public class Ticket {
    private Long id;
    private Long flightId;
    private String passengerName;

    public Ticket(Long id, Long flightId, String passengerName) {
        this.id = id;
        this.flightId = flightId;
        this.passengerName = passengerName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
}
