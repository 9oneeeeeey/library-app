package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    * @Trancsactional
    * 아래 있는 함수가 시작될 때 start transaction;
    * 함수가 예외 없이 잘 수행됐다면 commit;
    * 예외가 발생했다면 rollback;
    * */
    @Transactional // org.springframework.transaction
    public void saveUser(UserCreateRequest request){
        userRepository.save(new User(request.getName(), request.getAge()));
        //throw new IllegalArgumentException();
    }

    @Transactional(readOnly = true) // org.springframework.transaction
    public List<UserResponse> getUser(){
        List<User> users = userRepository.findAll(); /* findAll() -> select * from user; */
        return users.stream()
                //.map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional // org.springframework.transaction
    public void updateUser(UserUpdateRequest request){
        User user = userRepository.findById(request.getId()) /* findById -> select * from user where id = ? */
                .orElseThrow(IllegalArgumentException::new);
        /* -> .orElseThrow(IllegalArgumentException::new) 로 간결하게 수정
        if(user == null){
            throw new IllegalArgumentException(String.format("잘못된 user_name(%s)이 들어왔습니다", request.getUser_name()));
        }
        */

        user.updateName(request.getName());
        //userRepository.save(user);
    }

    @Transactional // org.springframework.transaction
    public void deleteUser(String name){
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
