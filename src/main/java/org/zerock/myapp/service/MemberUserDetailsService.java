package org.zerock.myapp.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.myapp.config.SecurityUser;
import org.zerock.myapp.domain.Member;
import org.zerock.myapp.repository.MemberRepository;

import java.util.Objects;

@Log4j2  // Log4j2 로깅 프레임워크의 로거를 사용하기 위한 애너테이션
@NoArgsConstructor  // 기본 생성자를 생성하는 Lombok 애너테이션

@Service  // Spring 컨테이너에 이 클래스를 Service로 등록하는 애너테이션
public class MemberUserDetailsService implements UserDetailsService {
    // Spring Security의 사용자 세부정보(UserDetails)를 제공하는 인터페이스를 구현

    @Setter(onMethod_= @Autowired)  // Setter 메서드 생성 및 Spring의 의존성 주입(DI) 애너테이션
    private MemberRepository memberRepo;  // MemberRepository 타입의 멤버 변수

    @Override  // 상위 인터페이스의 메서드를 오버라이드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 주어진 username으로 사용자 세부정보(UserDetails)를 로드하는 메서드

        log.trace("loadUserByUsername({}) invoked", username);  // 로깅

        Objects.requireNonNull(this.memberRepo);  // memberRepo가 null인지 확인
        log.info("\t+ this.memberRepo: {}", this.memberRepo);  // 로깅

        // username으로 Member를 조회
        Member foundMember =
                this.memberRepo.<Member>findById(username).
                        orElseThrow(() -> new UsernameNotFoundException(
                                String.format("No %s Found.", username)  // 사용자가 없을 경우 예외 발생
                        ));

        // 조회된 Member 객체를 기반으로 SecurityUser 객체 생성 후 반환
        return new SecurityUser(foundMember);
    } // loadUserByUsername

} // end class
