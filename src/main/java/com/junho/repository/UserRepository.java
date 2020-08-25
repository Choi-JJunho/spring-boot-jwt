package com.junho.repository;

import com.junho.domain.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JPARepository를 확장하는 인터페이스 UserRepository를 정의한다.
// <Entity, ID> 값이 들어가게 된다.
@Repository
public interface UserRepository extends JpaRepository<DAOUser, Long> {
    /**
     * 단순 상속을 통해서 제공하는 기능
     * save() / findOne() / findAll() / count() / delete()
     *
     * Query Method를 추가하여 Spring에게 알릴 수 있음.
     * findBy~ : 쿼리를 요청하는 메소드
     * countBy~ : 쿼리 결과 레코드 수를 요청하는 메소드
     *
     * 세부적인 쿼리 키워드는 다음을 참조
     * http://docs.spring.io/spring-data/jpa/docs/1.10.1.RELEASE/reference/html/#jpa.sample-app.finders.strategies
     */

    DAOUser findByUsername(String username);
}
