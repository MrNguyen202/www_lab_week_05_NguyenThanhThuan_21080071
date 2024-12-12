/*
 * @ (#) User.java    1.0    12/9/2024
 *
 *
 */
 
package vn.edu.iuh.hero.backend.models;
/*
 * @Description: 
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/9/2024
 * @Version: 1.0
 *
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.hero.backend.enums.Role;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract  class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String email;
    protected String password;
    protected Role role;

    public User(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
