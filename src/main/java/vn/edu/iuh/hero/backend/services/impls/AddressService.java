/*
 * @ (#) AddressService.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.services.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.models.Address;
import vn.edu.iuh.hero.backend.repositories.AddressRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IServices<Address, Long> {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address add(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address delete(Long aLong) {
        return addressRepository.findById(aLong).map(address -> {
            addressRepository.delete(address);
            return address;
        }).orElse(null);
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        return Optional.of(addressRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
