package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id // javax.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    //private long userId;
    @ManyToOne // N:1
    private User user;

    //private String bookName;
    private long bookId;

//    private String bookName;

    private boolean isReturn;

    public long getBookId() {
        return bookId;
    }

//    public String getBookName() {
//        return bookName;
//    }

    //JPA 기본 생성자 필수*
    protected UserLoanHistory(){

    }

    public UserLoanHistory(User user, long bookId) {
        //this.userId = userId;
        this.user = user;
        this.bookId = bookId;
        this.isReturn = false;
    }

    public void doReturn(){
        this.isReturn = true;
    }


}
