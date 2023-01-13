package com.karlisson.personapi.repositories;

import com.karlisson.personapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepositorie extends JpaRepository<Address, Long> {
}
