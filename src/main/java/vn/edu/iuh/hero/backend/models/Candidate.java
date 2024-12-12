package vn.edu.iuh.hero.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.hero.backend.enums.Role;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate extends User {
    @Column(name = "dob", nullable = true)
    private LocalDate dob;

    @Column(name = "email_address", nullable = true)
    private String emailAddress;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    private String avatar;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true) // Cho phép null
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = true)
    private Address address;

    public Candidate(Long id, String email, String password, Role role, LocalDate dob, String emailAddress, String fullName, String phone, Address address) {
        super(id, email, password, role);
        this.dob = dob;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "dob=" + dob +
                ", emailAddress='" + emailAddress + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address=" + address +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}