package dev.n1t.authentication.repositories;

import dev.n1t.authentication.models.Address;
import dev.n1t.authentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<User> findAddressById(Integer id);
}
