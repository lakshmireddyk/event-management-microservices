#**Event Management System (EMS): Microservices patterns demo application** 

It is intended to aid the demonstration of Microservices patterns using Spring Boot 2.1.4 and Spring Cloud.
Event Management System is a platform for event organizers and users to collaborate on events to be conducted.
 

##Contents

The repository contains six folders, one for each microservice of the system:

* **configuration-service** is one of the Microservice based on Spring Boot. It provides centralized configurations for all other microservices in the application. It is the one must be up and running before any other microservice.
* **account-service** is one of the Microservice based on Spring Boot REST API. It provides REST endpoints to get currently logged In user and specific user details by userId
* **discovery-service** is the Eureka Server, which is contacted by other microservices and the API gateway. This is Spring Boot service.
* **edge-service** is the Routing Service, implemented with Zuul. It connects with Eureka Server (discovery-service)for service discovery, and performs load balancing with Ribbon. Also implementing Spring Security for all microservices using JWT Access token. This is also Spring Boot based service.
* **event-service** is one of the Microservice based on Spring Boot REST API. It provides REST endpoints to manage events by event organizers.
* **notification-service** is one of the Microservice based on Spring Boot REST API. It provides message queue listeners to receive and process sign up notification messages sent by Authentication module (part of Gateway service).

## How to execute the application

I don't have automated scripts to run yet, have to run services manually. Also before we run services, we have to do few installations and setup as below

### Installations & Setup

* **RabbitMQ** Download and install [RabbitMQ](https://www.rabbitmq.com/download.html). We use it as Message Broker to receive and process sign up notification. When you have installed it, you need to run the RabbitMQ server. By default it will use guest/guest as username/password.
* **Elasticsearch**
  * Download, install and follow the instructions [Elasticsearch] (https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-install.html).
  * By default, it would start at (http://localhost:9200).

* **Kibana** 
  * Download, install and follow the instructions [Kibana] (https://www.elastic.co/guide/en/kibana/current/index.html).
  * Open config/kibana.yml in an text editor and set elasticsearch.url to point at our Elasticsearch instance. elasticsearch.hosts: ["http://localhost:9200"]
  * Run bin\kibana.bat from command prompt.
  * By default Kibana will start on port 5601 and Kibana UI will be available at http://localhost:5601

* **Logstash**
  * Download, install and follow the instructions [Kibana] (https://www.elastic.co/guide/en/kibana/current/index.html).
  * Use the configuration file called logstash.conf from repository and copy it to /bin folder. Also replace **<YOUR_LOCAL_WORKSPACE_LOCATION>** with your local path based on OS.
  * I have used **index => "microservice-%{+YYYY.MM.dd}"** as the index while sending structred data from logstash. So while creating index in kibana use "microservice".
  * Finally **run bin/logstash -f logstash.conf** to start logstash
		
* **MySQl** 
  *	Download and install [MySQL](https://dev.mysql.com/downloads/mysql/)
  * Create new database called **"ems"**
  * Import the schemas from **/db** folder
  * YOu can find the database details for each service in centralized configuration repo **event-management-microservices-config**
		


Now by this time RabbitMQ, MySQL and ELK stack is up and running. 

### Running services

Run the configuration-service first then discovery-service and then remainng all services in any order

* **Configuration Service** Navigate to configuration-service folder and run ./mvnw spring-boot:run
* **Discovery Service** Navigate to discovery-service folder and run ./mvnw spring-boot:run
* **API Gateway/Edge Service** Navigate to edge-service folder and run ./mvnw spring-boot:run
* **Account Service** Navigate to account-service folder and run ./mvnw spring-boot:run
* **Event Service** Navigate to event-service folder and run ./mvnw spring-boot:run
* **Notification Service** Navigate to notification-service folder and run ./mvnw spring-boot:run		
		

       		



