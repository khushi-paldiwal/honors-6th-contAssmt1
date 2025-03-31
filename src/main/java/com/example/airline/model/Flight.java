package com.example.airline.model;

public class Flight {
    private Long id;
    private String airline;
    private String source;
    private String destination;
    private String departureTime;

    public Flight(Long id, String airline, String source, String destination, String departureTime) {
        this.id = id;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
}
