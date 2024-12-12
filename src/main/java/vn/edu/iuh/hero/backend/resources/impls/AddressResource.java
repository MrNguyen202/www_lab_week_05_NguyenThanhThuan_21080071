/*
 * @ (#) AddressResource.java    1.0    11/16/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.resources.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/16/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.hero.backend.models.Address;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressResource implements IResources<Address, Long> {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    @Override
    public ResponseEntity<Response> add(Address address) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Long aLong, Address address) {
        return null;
    }

    @Override
    public ResponseEntity<Response> delete(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findById(Long aLong) {
        return null;
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<Response> findAll() {
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Get all address success!", addressService.findAll()));
    }
}
