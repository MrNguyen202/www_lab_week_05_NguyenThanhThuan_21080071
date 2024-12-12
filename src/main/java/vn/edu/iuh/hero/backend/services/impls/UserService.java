/*
 * @ (#) UserService.java    1.0    12/9/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.services.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/9/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.models.User;
import vn.edu.iuh.hero.backend.repositories.UserRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IServices<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User delete(Long aLong) {
        return userRepository.findById(aLong).map(user -> {
            userRepository.delete(user);
            return user;
        }).orElse(null);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.of(userRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return Optional.of(userRepository.findByEmail(email)).orElse(null);
    }
}
