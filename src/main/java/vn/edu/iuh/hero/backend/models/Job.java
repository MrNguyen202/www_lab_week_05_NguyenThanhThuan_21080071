package vn.edu.iuh.hero.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "job")
public class Job {
    @Id
    @Column(name = "job_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_desc", nullable = false, length = 2000)
    private String jobDesc;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    @ToString.Exclude
    private Company company;

    public Job(Long id, String jobDesc, String jobName, LocalDate expiredDate) {
        this.id = id;
        this.jobDesc = jobDesc;
        this.jobName = jobName;
        this.expiredDate = expiredDate;
    }
}