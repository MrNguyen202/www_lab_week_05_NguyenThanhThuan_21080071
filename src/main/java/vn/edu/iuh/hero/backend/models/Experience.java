/*
 * @ (#) Experience.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.models;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @Column(name = "exp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    @Column(name = "company_name", length = 100)
    private String companyName;
    @Column(name = "role", length = 100)
    private String role;
    @Column(name = "work_description", length = 1000)
    private String workDescription;

    @ManyToOne
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

}
