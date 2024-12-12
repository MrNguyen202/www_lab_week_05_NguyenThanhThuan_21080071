package vn.edu.iuh.hero.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.hero.backend.enums.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends User{

    @Column(name = "about", length = 2000)
    private String about;

    @Column(name = "email_address", nullable = true)
    private String emailAddress;

    @Column(name = "comp_name", nullable = false)
    private String compName;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "web_url")
    private String webUrl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true) // Cho phép null
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = true)
    @ToString.Exclude
    private Address address;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("jobs")
    @ToString.Exclude
    private List<Job> jobs;

    public Company(Long id, String email, String password, Role role, String about, String emailAddress, String compName, String phone, Address address, String webUrl) {
        super(id, email, password, role);
        this.about = about;
        this.emailAddress = emailAddress;
        this.compName = compName;
        this.phone = phone;
        this.address = address;
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "Company{" +
                "about='" + about + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", compName='" + compName + '\'' +
                ", phone='" + phone + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", address=" + address +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}