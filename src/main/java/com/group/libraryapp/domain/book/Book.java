package com.group.libraryapp.domain.book;

import javax.persistence.*;

@Entity
public class Book {

    @Id // javax.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // JPA의 경우 기본생성자 필수
    protected Book(){
    }

    // service에서 호출하기 위해 생성자 생성
    public Book(String name) {
        if(name == null && name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }

        this.name = name;
    }
}
