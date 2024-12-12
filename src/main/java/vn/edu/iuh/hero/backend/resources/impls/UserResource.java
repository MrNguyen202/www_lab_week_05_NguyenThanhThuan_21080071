/*
 * @ (#) UserResource.java    1.0    12/9/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.resources.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/9/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.models.User;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource implements IResources<User, Long> {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<Response> add(User user) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Long aLong, User user) {
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

    @Override
    public ResponseEntity<Response> findAll() {
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Response> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(
                new Response(
                        HttpStatus.OK.value(),
                        "Get user by email success!",
                        userService.findByEmail(email)
                )
        );
    }
}
