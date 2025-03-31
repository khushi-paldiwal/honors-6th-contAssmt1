package com.example.airline.controller;

import com.example.airline.model.Ticket;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final List<Ticket> tickets = new ArrayList<>();

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        ticket.setId((long) (tickets.size() + 1));
        tickets.add(ticket);
        return ticket;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {   // ✅ NEW ENDPOINT
        return tickets;
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return tickets.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        tickets.removeIf(t -> t.getId().equals(id));
        return "Ticket cancelled successfully.";
    }
}
