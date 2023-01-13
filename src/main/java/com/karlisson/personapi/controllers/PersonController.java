package com.karlisson.personapi.controllers;

import com.karlisson.personapi.controllers.exceptions.Error;
import com.karlisson.personapi.model.Person;
import com.karlisson.personapi.repositories.PersonRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    PersonRepositorie personRepositorie;

    @GetMapping
    public ResponseEntity findAll() {
        List<Person> persons = personRepositorie.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            Person person = personRepositorie.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(person);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        }

    }

    @PostMapping
    public ResponseEntity save(@RequestBody Person person) {
        Person newPerson = personRepositorie.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Person person, @PathVariable Long id) {
        try {
            Person updatedPerson = personRepositorie.findById(id).get();

            if (person.getName() != null) {
                updatedPerson.setName(person.getName());
            }

            if (person.getBirthDate() != null) {
                updatedPerson.setBirthDate(person.getBirthDate());
            }

            personRepositorie.save(updatedPerson);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        }
    }

}
