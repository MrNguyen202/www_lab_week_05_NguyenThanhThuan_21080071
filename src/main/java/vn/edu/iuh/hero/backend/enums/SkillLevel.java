/*
 * @ (#) SkillLevel.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.enums;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import lombok.Getter;

@Getter
public enum SkillLevel {
    MASTER("Master"),
    BEGINNER("Beginner"),
    ADVANCED("Advanced"),
    INTERMEDIATE("Intermediate"),
    PROFESSIONAL("Professional");

    private final String displayName;

    SkillLevel(String displayName) {
        this.displayName = displayName;
    }

}
