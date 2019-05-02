package com.lk.eventservice.service;

import com.lk.eventservice.model.Account;
import com.lk.eventservice.respository.EventRepository;
import com.lk.eventservice.model.Event;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService{

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Event createEvent(Event event) {
        Event eventCreated =   eventRepository.save(event);
        return eventCreated;
    }

    public Event updateEvent(Event event) {
        Event eventUpdated  =   eventRepository.save(event);
        return eventUpdated;
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).get();
    }

    public List<Event> getEvents() {
        List<Event> events  =   new ArrayList<>();
        eventRepository.findAll().forEach(event -> events.add(event));

        return events;
    }

    public List<Event> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    @HystrixCommand(fallbackMethod = "findCachedUserById")
    public Account getOrganizer(Long organizerId, String jwtToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);

        HttpEntity entityReq = new HttpEntity(headers);

        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:8763/accountservice/api/v1/users/"+organizerId,
                HttpMethod.GET,
                entityReq,
                new ParameterizedTypeReference<Account>(){});
        Account account = response.getBody();

        return account;

    }

    public Account findCachedUserById(Long userId, String token){

        return new Account(0,"DUMMY","DUMMY","DUMMY","DUMMY","DUMMY");

    }
}
