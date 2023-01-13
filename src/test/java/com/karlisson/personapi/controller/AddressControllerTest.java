package com.karlisson.personapi.controller;

import com.karlisson.personapi.controllers.AddressController;
import com.karlisson.personapi.controllers.PersonController;
import com.karlisson.personapi.model.Address;
import com.karlisson.personapi.model.Person;
import com.karlisson.personapi.repositories.PersonRepositorie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    AddressController addressController;

    @Autowired
    PersonController personController;

    @Autowired
    PersonRepositorie personRepositorie;

    @Test
    void findByPerson() {
        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address1 = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");
        Address address2 = new Address(null, "Logradouro 2", "1654784", "4321", "Nova Deli");

        person = personRepositorie.save(person);
        addressController.save(address1, person.getId()).getStatusCode().value();
        addressController.save(address2, person.getId()).getStatusCode().value();

        int status = addressController.findByPerson(person.getId()).getStatusCode().value();

        Assertions.assertEquals(200, status);
    }

    @Test
    void findByPersonNotFound() {
        int status = addressController.findByPerson(15L).getStatusCode().value();
        Assertions.assertEquals(404, status);
    }

    @Test
    void save() {

        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");

        person = personRepositorie.save(person);

        int status = addressController.save(address, person.getId()).getStatusCode().value();

        Assertions.assertEquals(201, status);
    }

    @Test
    void saveNotFound() {
        Address address = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");
        int status = addressController.save(address,15L).getStatusCode().value();
        Assertions.assertEquals(404, status);
    }

    @Test
    void findMainAddress() {
        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address1 = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");
        Address address2 = new Address(null, "Logradouro 2", "1654784", "4321", "Nova Deli", true);

        person = personRepositorie.save(person);

        addressController.save(address1, person.getId()).getStatusCode().value();
        addressController.save(address2, person.getId()).getStatusCode().value();

        ResponseEntity response = personController.findById(person.getId());
        person = (Person) response.getBody();
        int status = response.getStatusCode().value();

        List<Address> addressList = person.getAddressList();

        Assertions.assertEquals(200, status);
        Assertions.assertEquals(false, addressList.get(0).getMain());
        Assertions.assertEquals(true, addressList.get(1).getMain());
    }

    @Test
    void findMainAddressNotFound() {
        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address1 = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");
        Address address2 = new Address(null, "Logradouro 2", "1654784", "4321", "Nova Deli", true);

        person = personRepositorie.save(person);

        addressController.save(address1, person.getId()).getStatusCode().value();
        addressController.save(address2, person.getId()).getStatusCode().value();

        int status = addressController.findMainAddress(15L).getStatusCode().value();

        Assertions.assertEquals(404, status);
    }

    @Test
    void changeMainAddress() {
        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address1 = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");
        Address address2 = new Address(null, "Logradouro 2", "1654784", "4321", "Nova Deli");
        Address address3 = new Address(null, "Logradouro 3", "1654784", "4321", "Salvador");

        person = personRepositorie.save(person);

        addressController.save(address1, person.getId()).getBody();
        addressController.save(address2, person.getId()).getBody();
        address3 = (Address) addressController.save(address3, person.getId()).getBody();

        ResponseEntity response = addressController.changeMainAddress(person.getId(), address3.getId());
        person = (Person) response.getBody();
        int status = response.getStatusCode().value();

        Assertions.assertEquals(false, person.getAddressList().get(0).getMain());
        Assertions.assertEquals(false, person.getAddressList().get(1).getMain());
        Assertions.assertEquals(true, person.getAddressList().get(2).getMain());
        Assertions.assertEquals(200, status);
    }

    @Test
    void changeMainAddressNotFound() {
        Person person = new Person(null, "Karlos", LocalDate.parse("12/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Address address1 = new Address(null, "Logradouro 1", "4874561", "1234", "Riachao");

        person = personRepositorie.save(person);

        addressController.save(address1, person.getId()).getBody();

        int status = addressController.changeMainAddress(15L, address1.getId()).getStatusCode().value();

        Assertions.assertEquals(404, status);
    }

}
