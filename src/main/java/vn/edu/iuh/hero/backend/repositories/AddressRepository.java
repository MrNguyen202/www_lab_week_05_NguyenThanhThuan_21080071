package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
  }