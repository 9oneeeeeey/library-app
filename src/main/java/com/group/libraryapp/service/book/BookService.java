package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository ;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository,UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional // org.springframework.transaction
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        //1. 책 정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        //2. 대출기록 정보 확인하여 대출중인지 확인
        if(userLoanHistoryRepository.existsByBookIdAndIsReturn(book.getId(), false)){
            //3. 대출중인 경우 예외 발생
            throw new IllegalArgumentException("대출되어 있는 책입니다.");
        }

        //4. 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.loanBook(book.getId());

        //5. 유저 정보와 책 정보를 기반으로 userLoanHistory 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getId()));

    }


    @Transactional
    public void returnBook(BookReturnRequest request){

        //1. 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        //2. 책 정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);


        user.returnBook(book.getId());

        //3. 대출기록 정보 확인하여 대출중인지 확인
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookId(user.getId(), book.getId())
//                .orElseThrow(IllegalArgumentException::new);
//
//        //4. 반납
//        history.doReturn();

        //@Transaction -> 영속성 컨텍스트의 변경 감지 기능으로 entity 객체(userLoanHistory)가 변화가 생기면 자동으로 저장 처리되므로 생략 가능
        //userLoanHistoryRepository.save(history);

    }
}
