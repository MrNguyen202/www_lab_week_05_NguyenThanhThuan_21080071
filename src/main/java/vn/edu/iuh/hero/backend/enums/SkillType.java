package vn.edu.iuh.hero.backend.enums;

import lombok.Getter;

@Getter
public enum SkillType {
    TECHNICAL_SKILL("Kỹ năng kỹ thuật"),
    SOFT_SKILL("Kỹ năng mềm"),
    UNSPECIFIC("Không xác định");

    private final String displayName;

    SkillType(String displayName) {
        this.displayName = displayName;
    }

}
