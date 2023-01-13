package com.karlisson.personapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class AddressTest {

    @Test
    void mainAddress() {

        Address address1 = new Address(null, "logradouro 1", "57111000", "123", "Rio de Janeiro");
        Address address2 = new Address(null, "logradouro 2", "57111000", "123", "Rio de Janeiro");

        Person person = new Person(null, "Joana", LocalDate.parse("25/05/1986", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        person.addAddress(address1);
        person.addAddress(address2);

        Assertions.assertEquals(true, person.getAddressList().get(0).getMain());
        Assertions.assertEquals(false, person.getAddressList().get(1).getMain());
    }

}
