package com.karlisson.personapi.repositories;

import com.karlisson.personapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositorie extends JpaRepository<Person, Long> {
}
