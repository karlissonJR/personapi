package com.karlisson.personapi.controllers;

import com.karlisson.personapi.controllers.exceptions.Error;
import com.karlisson.personapi.model.Address;
import com.karlisson.personapi.model.Person;
import com.karlisson.personapi.repositories.AddressRepositorie;
import com.karlisson.personapi.repositories.PersonRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/address/{personId}")
public class AddressController {

    @Autowired
    AddressRepositorie addressRepositorie;

    @Autowired
    PersonRepositorie personRepositorie;

    @GetMapping
    public ResponseEntity findByPerson(@PathVariable Long personId) {
        try {
            Person person = personRepositorie.findById(personId).get();
            List<Address> addressList = person.getAddressList();
            return ResponseEntity.status(HttpStatus.OK).body(addressList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Address address, @PathVariable Long personId) {
        try {
            Person person = personRepositorie.findById(personId).get();
            address = addressRepositorie.save(address);
            person.addAddress(address);
            personRepositorie.save(person);

            return ResponseEntity.status(HttpStatus.CREATED).body(address);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        }
    }

    @GetMapping("/main")
    public ResponseEntity findMainAddress(@PathVariable Long personId) {
        try {
            Person person = personRepositorie.findById(personId).get();
            Address main = person.getAddressList().stream()
                    .filter(address -> address.getMain())
                    .collect(Collectors.toList()).get(0);

            return ResponseEntity.status(HttpStatus.OK).body(main);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa sem endereço principal."));
        }
    }

    @PutMapping("/main/{id}")
    public ResponseEntity changeMainAddress(@PathVariable Long personId, @PathVariable Long id) {

        try {
            Person person = personRepositorie.findById(personId).get();

            boolean isMain = false;

            for (Address address : person.getAddressList()) {
                if (address.getMain() && address.getId() != id) {
                    address.setMain(false);
                } else if (address.getId() == id) {
                    isMain = true;
                    address.setMain(true);
                }
            }

            if (!isMain) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Endereço não cadastrado."));
            }

            person = personRepositorie.save(person);
            return ResponseEntity.status(HttpStatus.OK).body(person);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Pessoa não cadastrada."));
        }
    }

}
