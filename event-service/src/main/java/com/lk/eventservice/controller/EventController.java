package com.lk.eventservice.controller;

import com.lk.eventservice.service.EventService;
import com.lk.eventservice.model.Account;
import com.lk.eventservice.model.Event;
import com.lk.eventservice.model.Organizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Api(value="Event Microservice", description="Operations to manage events")
@RestController
@RequestMapping("/api/v1")
public class EventController {

    private Logger logger   = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @ApiOperation(value = "Create new Event", response = HttpStatus.class)
    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(@Valid @RequestBody Event event, UriComponentsBuilder builder){
        logger.info("create event request ->"+event.toString());
        Event eventCreated  =   eventService.createEvent(event);
        if(eventCreated==null){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        HttpHeaders httpHeaders =   new HttpHeaders();
        httpHeaders.setLocation(builder.path("/events/{eventId}").buildAndExpand(eventCreated.getEventId()).toUri());

        return new ResponseEntity<Void>(httpHeaders,HttpStatus.CREATED);

    }

    @ApiOperation(value = "Update Event", response = HttpStatus.class)
    @PutMapping("/events")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event){
        logger.info("update event request ->"+event.toString());
        Event eventUpdated  =   eventService.createEvent(event);
        return new ResponseEntity<Event>(eventUpdated,HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Event", response = HttpStatus.class)
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long eventId){
            logger.info("delete event request with eventId ->"+eventId);
            eventService.deleteEvent(eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get Event", response = Event.class)
    @GetMapping("/events/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId, @RequestHeader("Authorization") String token){

        logger.info("Get event request with eventId -> "+eventId);
        logger.info("Get event request with token -> "+token);
        Event event =   eventService.getEventById(eventId);

        Account account =   eventService.getOrganizer(event.getOrganizerId(), token);

        Organizer   organizer   =   new Organizer(account.getName(), account.getEmail(), account.getContact());
        event.setOrganizer(organizer);

        return new ResponseEntity<>(event,HttpStatus.OK);

    }

    @ApiOperation(value = "Get All Events", response = List.class)
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(){
        logger.info("All events request received");
        List<Event> events  =   eventService.getEvents();

        return new ResponseEntity<List<Event>>(events,HttpStatus.OK);

    }

    @ApiOperation(value = "Get All Events for organizer", response = List.class)
    @GetMapping("/events/organizer/{id}")
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable("id") Long organizerId){
        logger.info("Events request for organizer with id -> "+organizerId);
        List<Event> events  =   eventService.getEventsByOrganizer(organizerId);

        return new ResponseEntity<>(events,HttpStatus.OK);
    }


}
