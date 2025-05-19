package com.qoormthon.todool.domain.user.adapter.out.persistence;

import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.application.port.out.FindUserPort;
import com.qoormthon.todool.domain.user.domain.model.User;
import com.qoormthon.todool.domain.user.exception.UserFindException;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserPersistenceAdapter implements ExistsByUserPort, FindUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserPersistenceAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    @Override
    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public User findByUserId(String userId) {
        if(!this.existsByUserId(userId)) { throw new UserFindException(("존재하지 않는 유저입니다.")); }
        return userMapper.UserEntityToUser(userRepository.findByUserId(userId));
    }
}
