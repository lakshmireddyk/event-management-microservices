package com.lk.eventservice.respository;

import com.lk.eventservice.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event,Long> {

    List<Event> findByOrganizerId(Long id);

}
