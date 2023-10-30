package org.zerock.myapp.config;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.zerock.myapp.service.MemberUserDetailsService;

import java.util.Objects;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Log4j2
@NoArgsConstructor

@EnableWebSecurity  // 웹 보안을 활성화하기 위한 어노테이션.

@Configuration  // 해당 클래스가 스프링 설정 클래스임을 나타냄.
public class SecurityConfig {
    @Setter(onMethod_ = @Autowired)
    private MemberUserDetailsService memberUserDetailsService;

//     (1) Spring boot v2.7.x v3.1.x 버전에서 공통적으로 사용되는 인증 설정 예제.
//     인 메모리 방식으로 사용자 인증 정보를 설정.

//    (1) Spring boot v2.7.x v3.1.x Common
/*
   @Autowired
    public void authenticate(AuthenticationManagerBuilder auth) throws Exception{
        log.trace("authentication({}) invoked", auth);

        // Step-1. in-memory User Creation - @Since spring boot v2.7.x, v3.1.x
        auth.inMemoryAuthentication().
                withUser("manager").
                password("{noop}manager123").
                roles("MANAGER");

        auth.inMemoryAuthentication().
                withUser("admin").
                password("{noop}admin123").
                roles("ADMIN");

        auth.inMemoryAuthentication().
                withUser("member").
                password("{noop}member123").
                roles("MEMBER");

                String sql_1 = "SELECT id as username, '{noop}' || password AS password, enabled" +
                        "FROM member WHRER id = ?";

                String sql_2 = "SELECT id, role FROM member WHRER id  = ?";

                auth.jdbcAuthentication().
                        dataSource(this.dataSource).
                        userByUsernameQuery(sql_1).
                        authoritlesByUsernameQuery(sql_2);


    } // authenticate
*/

//     (2) Spring boot v2.7.x v3.1.x 버전에서의 사용자 세부 서비스 예제.
//     인 메모리 사용자 세부 정보 서비스를 설정.

//    (2) Spring boot v2.7.x v3.1.x Common
/*
    @Bean
    public UserDetailsService userDetailService(){
        log.trace("UserDetailsService() invoked");
        // Step-1. in-memory User Creation - @Since spring boot v2.7.x, v3.1.x
        // User.withDefaultPasswordEncoder() is considered unsafe
        // for production and is only intended for sample applications.
        // 'withDefaultPasswordEncoder()' is deprecated.
        UserDetails member =
            User.withDefaultPasswordEncoder().
                username("member").
                password("member123").
                roles("MEMBER").
                build();

        UserDetails manager =
                User.withDefaultPasswordEncoder().
                        username("manager").
                        password("manager123").
                        roles("MANAGER").
                        build();

        UserDetails admin =
                User.withDefaultPasswordEncoder().
                        username("admin").
                        password("admin123").
                        roles("ADMIN", "MANAGER").
                        build();

        return new InMemoryUserDetailsManager(member, manager, admin);
    }   // UserDetailsService
*/

//        ==================================================
//         1. 보안 필터 체인 설정
//        ==================================================

//     @Bean 어노테이션은 해당 메서드의 반환 값을 빈(Bean)으로 등록함을 나타냄

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        log.trace("securityFilterChain({}) invoked", http);
//        return null;
//    } // securityFilterChain // security 표준 메소드 시그니쳐

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector instropector) throws Exception{
        log.trace("securityFilterChain({}) invoked", http);
//        ==================================================
//         2. 사용자 세부 정보 서비스 유효성 검사
//        ==================================================
//         우리가 만든 UserDetailService 객체를 이용해서, 특정 사용자의 인증정보를 사용하도록 하는 설정
//         memberUserDetailsService가 null이 아님을 확인.
//         객체가 null이면 NullPointerException을 발생시킵니다.
        Objects.requireNonNull(this.memberUserDetailsService);
//        ==================================================
//         3. 사용자 세부 정보 서비스 설정.
//        ==================================================
//         custom userDetailsService를 설정하여 사용자의 세부 정보를 가져올 수 있습니다.
        http.userDetailsService(this.memberUserDetailsService);

//        ==================================================
//         4. URL 기반의 접근 제어 설정
//        ==================================================

//         1번째 방법과 2번째 방법은 주석 처리 되어 있으며, 현재는 3번째 방법을 사용하여
//         URL 기반의 접근 제어를 설정. 예: "/hello"는 모든 사용자에게 허용.

/*

//        1st.method - ok: bug cve-2023-34035 fix method.
//        - 이 방법은 `MvcRequestMatcher.Builder`를 사용하여 요청 매칭 규칙을 구성합니다.
//        - `MvcRequestMatcher`는 AntPath보다 고급 매칭 방법을 제공하며, 실제로 Spring MVC 설정을 고려합니다.
//          이는 서블릿 경로, 컨텍스트 경로 등과 같은 것들을 포함합니다.
//        - 이 방법은 특정 버그 (`cve-2023-34035`)의 해결책으로 표시되어 있습니다.
//        - 여러 URL을 기반으로 권한을 설정합니다. 예로, 루트("/")와 "/hello"는 모든 사용자에게 허용됩니다.
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(instropector);
        http.authorizeHttpRequests(
                customizer -> customizer.requestMatchers(mvcMatcherBuilder.pattern("/"),
                        mvcMatcherBuilder.pattern("/hello")).permitAll().
                        requestMatchers(mvcMatcherBuilder.pattern("/member")).authenticated().
                        requestMatchers(mvcMatcherBuilder.pattern("/manager")).hasRole("MANAGER").
                        requestMatchers(mvcMatcherBuilder.pattern("/admin")).hasRole("ADMIN").
                        anyRequest().permitAll()
        ); // root, hello에 대한 모든 권한을 허용하라.


//        2nd.method - ok: Within Spring Security v6.
//        - 이 방법은 Spring Security v6에서 사용되는 방식입니다.
//        - 이 방법은 `AntPathRequestMatcher`를 사용하여 요청 매칭 규칙을 구성합니다.
//        - AntPath는 간단한 패턴 매칭 방법을 제공하는데, 이는 주로 URL 패턴을 기반으로 합니다.
//        - 루트("/")와 "/hello"는 모든 사용자에게 허용되며, "/board/**" 경로는 ADMIN 권한을 가진 사용자만 접근 가능합니다.
        http.authorizeHttpRequests(
                customizer -> {
                    customizer.requestMatchers(new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/hello")).permitAll();
                    customizer.requestMatchers(new AntPathRequestMatcher("/member")).authenticated();
                    customizer.requestMatchers(new AntPathRequestMatcher("/manager")).hasAnyRole("MANAGER", "ADMIN");
                    customizer.requestMatchers(new AntPathRequestMatcher("/admin")).hasRole("ADMIN");
                    customizer.requestMatchers(new AntPathRequestMatcher("/board/**")).hasRole("ADMIN");

                    customizer.anyRequest().permitAll();
                }

        ); //authorizeHttpRequests
*/

//        - 이 방법도 `AntPathRequestMatcher`를 기반으로 요청 매칭 규칙을 구성하지만, 조금 더 간략한 형태로 작성되었습니다.
//        - 주로 URL 패턴을 기반으로 권한을 설정합니다. 예로, 루트("/")와 "/hello"는 모든 사용자에게 허용되며,
//          "/admin" 경로는 ADMIN 권한을 가진 사용자만 접근 가능합니다.
//         URL 기반의 접근 제어 설정.
//         "/hello"와 같은 경로는 모든 사용자에게 허용됩니다.
//         "/admin"은 ADMIN 권한을 가진 사용자만 접근 가능합니다.
                http.authorizeHttpRequests(
                (customizer) ->
                        customizer.
                                requestMatchers(antMatcher("/"), antMatcher("/hello")).permitAll().
                                requestMatchers(antMatcher("/member")).authenticated().
                                requestMatchers(antMatcher("/manager")).hasAnyRole("MANAGER", "ADMIN").
                                requestMatchers(antMatcher("/admin")).hasRole("ADMIN").
                                anyRequest().permitAll()
        ); //authorizeHttpRequests

//        ==================================================
//         5. CSRF 보호 비활성화
//        ==================================================
        // CSRF (Cross-Site Request Forgery) 보호 기능을 비활성화.
        // 웹 애플리케이션의 보안 취약점을 방지하기 위한 설정, 하지만 여기서는 비활성화하였습니다.
        http.csrf(AbstractHttpConfigurer::disable);

//        ==================================================
//         6. 로그인 페이지 및 성공 시 리다이렉트 페이지 설정
//        ==================================================
        // 로그인 페이지 및 로그인 성공 후 리다이렉트 될 페이지 설정.
        // 사용자가 로그인에 성공하면 "/loginSuccess" 페이지로 리다이렉트됩니다.
        http.formLogin(
                customizer -> customizer.loginPage("/login").
                        defaultSuccessUrl("/loginSuccess", true)
        );

//        ==================================================
//         7. 접근 거부 페이지 설정
//        ==================================================
        // 접근이 거부될 때 사용자를 리다이렉트 할 페이지 설정.
        http.exceptionHandling(customizer -> customizer.accessDeniedPage("/accessDenied"));

//        ==================================================
//         8. 로그아웃 설정
//        ==================================================
//        - `http.logout()` 메소드는 사용자의 로그아웃 관련 설정을 구성하는데 사용
//        - `invalidateHttpSession(true)`는 사용자가 로그아웃할 때 HTTP 세션을 무효화시킵니다.
//          이는 일반적으로 보안상의 이유로 사용되며, 사용자가 로그아웃하면 그 세션에 저장된 모든 데이터가 제거됩니다.
//        - `logoutSuccessUrl("/login")`는 사용자가 성공적으로 로그아웃한 후 리다이렉트 될 URL을 설정합니다.
//          여기서는 "/login" 경로로 사용자를 리다이렉트 시킵니다.
        http.logout(customizer ->
            customizer.invalidateHttpSession(true).logoutSuccessUrl("/login")
        );

/*
//        ==================================================
//         9. 세션 관리 설정
//        ==================================================
        http.sessionManagement(customizer ->
//        1. 기본값
//        세션이 필요할 때만 세션을 생성하도록 합니다. 이것은 Spring Security의 기본 세션 생성 정책입니다.
//        예를 들어, 사용자가 인증되었을 때 세션을 생성해야 하는 경우 이 정책을 사용합니다.
                customizer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//        2.
//        이 정책은 Spring Security에게 세션을 전혀 생성하거나 사용하지 않도록 지시합니다.
//        이 경우, 모든 인증은 매 요청마다 다시 수행되어야 합니다. 즉, 사용자는 매번 새로운 토큰이나 인증 방식을 제공해야 합니다.
//        주로 RESTful API와 같은 stateless 서비스에서 사용됩니다.
//                customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
*/

//        ==================================================
//         10. 보안 설정 반환
//        ==================================================
        // 보안 설정을 빌드하여 반환.
        return http.build();

    } // securityFilterChain

//        ================================================================
//         1. 비밀번호 인코딩을 위한 PasswordEncoder 객체 생성 / 반환
//        ================================================================

//    이 메서드는 스프링 시큐리티의 비밀번호 인코딩을 위한 PasswordEncoder 객체를 생성하여 반환합니다.
//    `createDelegatingPasswordEncoder()`를 사용하여, 다양한 인코딩 형식에 대한 대리(delegating) 인코더를 생성합니다.
//    대리 인코더는 다양한 인코딩 형식을 지원할 수 있게 해주며, 기본적으로 사용하는 인코딩 형식은 bcrypt입니다.
//    예를 들어, "{bcrypt}"로 시작하는 암호화된 비밀번호는 bcrypt 인코딩을 사용하여 확인됩니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.trace("passwordEncoder() invoked.");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    } // passwordEncoder

} // end class