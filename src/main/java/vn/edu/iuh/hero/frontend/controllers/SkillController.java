/*
 * @ (#) SkillController.java    1.0    12/13/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/13/2024
 * @Version: 1.0
 *
 */

import jakarta.servlet.http.HttpSession;
import org.eclipse.angus.mail.imap.protocol.MODSEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.models.Skill;
import vn.edu.iuh.hero.backend.services.impls.SkillService;

@Controller
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/insert")
    public ModelAndView insertSkill(
            @RequestParam("skillName") String name,
            @RequestParam("skillDescription") String description,
            @RequestParam("type") SkillType level,
            HttpSession session
    ) {
        ModelAndView modelAndView = new ModelAndView();
        Skill skill = new Skill();
        skill.setSkillName(name);
        skill.setSkillDescription(description);
        skill.setType(level);
        skillService.add(skill);
        modelAndView.setViewName("redirect:/company/viewPostJob");
        return modelAndView;
    }
}
