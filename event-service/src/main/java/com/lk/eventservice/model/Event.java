package com.lk.eventservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long eventId;

    @NotNull
    @Size(min = 8, message = "Event name should be at least 2 characters in length")
    @Column(name="event_name")
    private String eventName;


    @NotNull(message="Please provide event start date")
    @Column(name="start_date")
    @Temporal(DATE)
    private Date startDate;

    @NotNull(message="Please provide event end date")
    @Temporal(DATE)
    @Column(name="end_date")
    private Date endDate;

    @NotNull(message = "Please provide organizer Id")
    @Column(name="organizer_id")
    private Long organizerId;

    @Transient
    private Organizer organizer;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                "eventName='" + eventName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", organizerId=" + organizerId +
                '}';
    }
}
