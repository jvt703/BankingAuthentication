package dev.n1t.authentication.repositories;


import dev.n1t.authentication.models.User;
import dev.n1t.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressById(Long id);
}
