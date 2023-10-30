package org.zerock.myapp.security;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.myapp.domain.Member;
import org.zerock.myapp.domain.Role;
import org.zerock.myapp.repository.MemberRepository;

@Log4j2
@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@AutoConfigureMockMvc
@SpringBootTest
public class SpringSecurityTests {
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder passwordEncoder;

    @Setter(onMethod_ = @Autowired)
    private MemberRepository memberRepo;

//    @Disabled
    @Tag("fast")
    @Order(1)
    @DisplayName("1. contextLoads")
    @Timeout(3L)
    @Test
    void contextLoads() {
        log.trace("contextLoads() invoked.");

        Member member = new Member();
        member.setId("member");
        member.setPassword(this.passwordEncoder.encode("member123"));
        member.setName("MEMBER");
        member.setRole(Role.ROLE_MEMBER);
        member.setEnabled(1);

        this.memberRepo.save(member);

        Member manager = new Member();
        manager.setId("manager");
        manager.setPassword(this.passwordEncoder.encode("manager123"));
        manager.setName("MANAGER");
        manager.setRole(Role.ROLE_MANAGER);
        manager.setEnabled(1);

        this.memberRepo.save(manager);

        Member admin = new Member();
        admin.setId("admin");
        admin.setPassword(this.passwordEncoder.encode("admin123"));
        admin.setName("ADMIN");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setEnabled(1);

        this.memberRepo.save(admin);

    } // contextLoads

} // end class
