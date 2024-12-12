package vn.edu.iuh.hero;

import com.neovisionaries.i18n.CountryCode;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import vn.edu.iuh.hero.backend.enums.Role;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.ids.CandidateSkillId;
import vn.edu.iuh.hero.backend.ids.JobSkillId;
import vn.edu.iuh.hero.backend.models.*;
import vn.edu.iuh.hero.backend.services.impls.*;

import java.util.List;
import java.util.Random;


@SpringBootApplication
public class NguyenThanhThuan21080071Lab5Application {

    public static void main(String[] args) {
        SpringApplication.run(NguyenThanhThuan21080071Lab5Application.class, args);
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobService jobService;

    @Autowired
    private CandidateSkillService candidateSkillService;

    @Autowired
    private JobSkillService jobSkillService;

    @Bean
    @Order(1)
    CommandLineRunner initDataCandidate(){
        return args -> {
            Faker faker = new Faker();
            Random rnd =new Random();
            for (int i = 0; i < 100; i++) {
                Address address = new Address();
                address.setStreet(faker.address().streetName());
                address.setCity(faker.address().city());
                address.setCountry(CountryCode.valueOf(faker.address().countryCode()));
                address.setNumber(faker.address().buildingNumber());
                address.setZipcode(faker.address().zipCode());
//                addressService.add(address);

                Candidate candidate = new Candidate();
                candidate.setEmail(faker.internet().emailAddress());
                candidate.setPassword(faker.internet().password());
                candidate.setRole(Role.CANDIDATE);
                candidate.setFullName(faker.name().fullName());
                candidate.setDob(faker.date().birthdayLocalDate(16, 60));
                candidate.setEmailAddress(faker.internet().emailAddress());
                candidate.setPhone(faker.phoneNumber().cellPhone());
                candidate.setAvatar(faker.avatar().image());

                candidate.setAddress(address);
                candidateService.add(candidate);
            }
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner initDataCompany(){
        return args -> {
            Faker faker = new Faker();
            Random rnd =new Random();
            for (int i = 0; i < 100; i++) {
                Address address = new Address();
                address.setStreet(faker.address().streetName());
                address.setCity(faker.address().city());
                address.setCountry(CountryCode.valueOf(faker.address().countryCode()));
                address.setNumber(faker.address().streetAddressNumber());
                address.setZipcode(faker.address().zipCode());
//                addressService.add(address);

                Company company = new Company();
                company.setEmail(faker.internet().emailAddress());
                company.setPassword(faker.internet().password());
                company.setRole(Role.COMPANY);
                company.setCompName(faker.company().name());
                company.setAbout(faker.lorem().paragraph(1));
                company.setEmailAddress(faker.internet().emailAddress());
                company.setPhone(faker.phoneNumber().cellPhone());
                company.setWebUrl(faker.internet().url());

                company.setAddress(address);
                companyService.add(company);

                for (int j = 0; j < 10; j++) {
                    Job job = new Job();
                    job.setJobName(faker.job().title());
                    job.setJobDesc(faker.lorem().paragraph(1));
                    job.setCompany(company);
                    jobService.add(job);
                }
            }
        };
    }

    @Bean
    @Order(3)
    CommandLineRunner initDataSkill(){
        return args -> {
            Faker faker = new Faker();
            Random rnd =new Random();
            for (int i = 0; i < 10; i++) {
                Skill skill = new Skill();
                skill.setSkillName(faker.job().keySkills());
                skill.setSkillDescription(faker.lorem().paragraph(1));
                //SOFT_SKILL,UNSPECIFIC, TECHNICAL_SKILL
                skill.setType(SkillType.values()[rnd.nextInt(SkillType.values().length)]);
                skillService.add(skill);
            }
        };
    }

    @Bean
    @Order(4)
    CommandLineRunner initDataCandidateSkill() {
        return args -> {
            Faker faker = new Faker();
            Random rnd = new Random();
            List<Candidate> candidates = candidateService.findAll();
            List<Skill> skills = skillService.findAll();

            if (skills.isEmpty()) {
                System.out.println("No skills available to assign!");
                return;
            }

            for (Candidate candidate : candidates) {
                int skillCount = rnd.nextInt(5) + 2; // Random từ 2 đến 6 kỹ năng
                for (int i = 0; i < skillCount; i++) {
                    // Chọn ngẫu nhiên một skill
                    Skill randomSkill = skills.get(rnd.nextInt(skills.size()));

                    // Tạo CandidateSkill và CandidateSkillId
                    CandidateSkill candidateSkill = new CandidateSkill();
                    CandidateSkillId candidateSkillId = new CandidateSkillId();

                    // Gán giá trị cho CandidateSkillId
                    candidateSkillId.setCanId(candidate.getId());
                    candidateSkillId.setSkillId(randomSkill.getId());

                    // Gán CandidateSkillId vào CandidateSkill
                    candidateSkill.setId(candidateSkillId);
                    candidateSkill.setCan(candidate);
                    candidateSkill.setSkill(randomSkill);
                    candidateSkill.setSkillLevel(SkillLevel.values()[rnd.nextInt(SkillLevel.values().length)]);
                    candidateSkill.setMoreInfos(faker.lorem().paragraph(1));

                    // Lưu vào cơ sở dữ liệu
                    candidateSkillService.add(candidateSkill);
                }
            }
        };
    }

    @Bean
    @Order(5)
    CommandLineRunner initDataJobSkill(){
        return args -> {
            Faker faker = new Faker();
            Random rnd =new Random();
            List<Job> jobs = jobService.findAll();
            List<Skill> skills = skillService.findAll();

            if (skills.isEmpty()) {
                System.out.println("No skills available to assign!");
                return;
            }

            for (Job job : jobs) {
                int skillCount = rnd.nextInt(5) + 2; // Random từ 2 đến 6 kỹ năng
                for (int i = 0; i < skillCount; i++) {
                    // Chọn ngẫu nhiên một skill
                    Skill randomSkill = skills.get(rnd.nextInt(skills.size()));

                    // Tạo JobSkill và JobSkillId
                    JobSkill jobSkill = new JobSkill();
                    JobSkillId jobSkillId = new JobSkillId();

                    // Gán giá trị cho JobSkillId
                    jobSkillId.setJobId(job.getId());
                    jobSkillId.setSkillId(randomSkill.getId());

                    // Gán JobSkillId vào JobSkill
                    jobSkill.setId(jobSkillId);
                    jobSkill.setJob(job);
                    jobSkill.setSkill(randomSkill);
                    jobSkill.setSkillLevel(SkillLevel.values()[rnd.nextInt(SkillLevel.values().length)]);
                    jobSkill.setMoreInfos(faker.lorem().paragraph(1));

                    // Lưu vào cơ sở dữ liệu
                    jobSkillService.add(jobSkill);
                }
            }
        };
    }
}
