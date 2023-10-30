package org.zerock.myapp.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.zerock.myapp.domain.Member;

@Log4j2

// SecurityUser는 Spring Security의 User 클래스를 상속받아 확장된 사용자 정의 클래스입니다.
public class SecurityUser extends User {
//     serialVersionUID는 직렬화에 사용되는 버전 관리 번호입니다.
//     직렬화와 역직렬화 시 객체의 버전 일관성을 확인하는데 사용됩니다.
    private static  final double serialVersionUID = 1L;

//     기본 생성자가 주석처리 되어 있습니다.
//     이 생성자는 사용자의 이름, 비밀번호 및 권한 목록을 받아 User 객체를 생성하는 역할을 했습니다.
//    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }

//     Member 객체를 인자로 받는 생성자입니다.
//     이 생성자를 통해, 주어진 Member 객체를 기반으로 SecurityUser 객체를 생성합니다.
    public SecurityUser(Member member){
//         super는 부모 클래스의 생성자를 호출하는 키워드입니다.
//         여기서는 Member 객체의 id와 password를 사용해 User 객체를 생성하며,
//         권한 목록은 Member의 role을 통해 생성됩니다.
        super(
                member.getId(),  // Member의 ID를 사용자 이름으로 사용
//                "{noop}"+member.getPassword(),  // "{noop}"는 암호화 없이 비밀번호를 그대로 사용하는 의미
                member.getPassword(),   // 암호화 된 비밀번호 사용
                AuthorityUtils.createAuthorityList(member.getRole().name())  // Member의 role을 사용해 권한 목록을 생성
        );
        // 생성자가 호출될 때 로그를 남깁니다.
        log.trace("SecurityUser({}) invoked", member);
    } // constructor

} // end class
