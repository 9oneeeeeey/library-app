package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //User findByName(String name); /* select * from user where name = ? */

    /* Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스
    * orElseThrow 함수를 사용할 수 있음
    * */
    Optional<User> findByName(String name);
}
