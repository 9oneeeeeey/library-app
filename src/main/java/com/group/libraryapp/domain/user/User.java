package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    protected User() {
    }

    /*
    * @Id -> 이 필드를 primary key로 간주
    * @GeneratedValue -> primary key는 자동 생성 ,
    * GenerationType.IDENTITY -> auto_increment
     * */
    @Id // javax.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 20, name = "name")
    private String name;

    private Integer age;

    /*
    * @OneToMany 1:N 관계로 연결
    * mappedBy 연결된 field
    * cascade -> 연관된 데이터 같이 삭제,
    * orphanRemoval -> 객체간 관계가 끊어진 데이터를 자동으로 제거
    * */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 1:N
    private List<UserLoanHistory> userLoanHistoryList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public long getId() {
        return id;
    }

    public User(String name, Integer age){
        if(name ==null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }

        this.name = name;
        this.age = age;

    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(long bookId){
        this.userLoanHistoryList.add(new UserLoanHistory(this, bookId));

    }

    public void returnBook(Long bookId){
        UserLoanHistory targetHistory = this.userLoanHistoryList.stream()
                .filter(history -> history.getBookId() == bookId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        targetHistory.doReturn();
    }
}
