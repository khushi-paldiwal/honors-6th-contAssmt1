package com.example.airline.controller;

import com.example.airline.model.Flight;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final List<Flight> flights = new ArrayList<>(
            List.of(
                    new Flight(1L, "Air India", "Delhi", "Mumbai", "10:00 AM"),
                    new Flight(2L, "Indigo", "Bangalore", "Chennai", "12:30 PM")
            )
    );

    @GetMapping
    public List<Flight> getAllFlights(@RequestParam(required = false) String sort) {
        if ("asc".equalsIgnoreCase(sort)) flights.sort(Comparator.comparing(Flight::getDepartureTime));
        return flights;
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flights.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }
}
