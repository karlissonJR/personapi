package com.karlisson.personapi.controller;

import com.karlisson.personapi.controllers.PersonController;
import com.karlisson.personapi.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class PersonControllerTests {

    @Autowired
    PersonController personController;

    @Test
    void findAll() {
        int status = personController.findAll().getStatusCode().value();
        List<Person> persons = (List) personController.findAll().getBody();

        Assertions.assertEquals(200, status);
        Assertions.assertEquals(0, persons.size());
    }

    @Test
    void findById() {
        Person person = new Person(null, "Joana", LocalDate.parse("25/05/1986", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        personController.save(person).getStatusCode().value();

        ResponseEntity response = personController.findById(1L);
        Person newPerson = (Person) response.getBody();
        int status = response.getStatusCode().value();
        person.setId(newPerson.getId());

        Assertions.assertEquals(person, newPerson);
        Assertions.assertEquals(200, status);
    }

    @Test
    void findByIdNotFound() {
        int status = personController.findById(15L).getStatusCode().value();

        Assertions.assertEquals(404, status);
    }

    @Test
    void save() {
        Person person = new Person(null, "Joana", LocalDate.parse("25/05/1986", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        int status = personController.save(person).getStatusCode().value();

        Assertions.assertEquals(201, status);
    }

    @Test
    void update() {
        Person person = new Person(null, "Joana", LocalDate.parse("25/05/1986", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        person = (Person) personController.save(person).getBody();

        person.setName("Juliana");
        Person updatedPerson = (Person) personController.update(person, person.getId()).getBody();

        Assertions.assertEquals("Juliana", updatedPerson.getName());
    }
}
