package com.yalkay.notificationservice.listener;

import com.yalkay.notificationservice.config.RabbitConfig;
import com.yalkay.notificationservice.model.Account;
import com.yalkay.notificationservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SignupMessageListener {

    private static final Logger logger  = LoggerFactory.getLogger(SignupMessageListener.class);
    private RestTemplate restTemplate;

    @Value("${eventservice.url}")
    private String eventserviceUrl;

    public SignupMessageListener(){

    }

    @Autowired
    public SignupMessageListener(RestTemplate restTemplate){

        this.restTemplate   =   restTemplate;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_SIGNUP)
    public void processOrder(Account accountCreated) {

       logger.info("User sign up details received for "+accountCreated.getName());

        // Get latest 10 events published using eventservice
        ResponseEntity<List<Event>> response = restTemplate.exchange(
                eventserviceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Event>>(){});
        List<Event> events = response.getBody();

        events.stream().forEach(e->System.out.println(e.getEventName()));


        //TODO: Use and send sing up message email template and include recent 10 events published by organizers

    }
}
