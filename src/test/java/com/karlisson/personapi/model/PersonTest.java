package com.karlisson.personapi.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class PersonTest {

    @Test
    void addAddress() {

        Address address = new Address(null, "logradouro 1", "57111000", "123", "Rio de Janeiro");
        Person person = new Person(null, "Joana", LocalDate.parse("25/05/1986", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        person.addAddress(address);
        Assert.notEmpty(person.getAddressList(), "");
    }

}
