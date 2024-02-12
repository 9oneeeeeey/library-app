package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    /* select * from user_loan_history where book_id = ? and is_return = ? */
    boolean existsByBookIdAndIsReturn(long bookId, boolean isReturn);

    Optional<UserLoanHistory> findByUserIdAndBookId(long userId, long bookId);

}
