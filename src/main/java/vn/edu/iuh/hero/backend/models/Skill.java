package vn.edu.iuh.hero.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.hero.backend.enums.SkillType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @Column(name = "skill_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private SkillType type;

}