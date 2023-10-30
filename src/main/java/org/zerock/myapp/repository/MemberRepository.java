package org.zerock.myapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.zerock.myapp.domain.Member;

// MemberRepository 인터페이스는 Spring Data JPA의 CrudRepository 인터페이스를 상속받습니다.
// CrudRepository는 기본적인 CRUD(Create, Read, Update, Delete) 작업을 수행하는 메서드들을 제공합니다.
public interface MemberRepository extends CrudRepository<Member, String> {
    // <Member, String>은 제네릭 타입입니다.
    // Member는 이 리포지토리에서 관리할 도메인 타입을 나타냅니다.
    // String은 Member 도메인의 주 키 타입을 나타냅니다.

    // 이 인터페이스를 구현하는 클래스(구현체)는 Spring Data JPA에 의해 자동으로 생성됩니다.
    // 따라서 별도의 구현 클래스를 작성할 필요가 없습니다.

    // 필요한 쿼리 메서드를 추가적으로 선언할 수 있습니다.
    // 예: findByUsername(String username);
}

