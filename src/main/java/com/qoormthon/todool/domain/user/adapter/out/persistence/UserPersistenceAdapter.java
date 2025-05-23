package com.qoormthon.todool.domain.user.adapter.out.persistence;

import com.qoormthon.todool.domain.user.adapter.dto.response.SignUpUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.application.port.out.FindUserPort;
import com.qoormthon.todool.domain.user.application.port.out.SaveUserPort;
import com.qoormthon.todool.domain.user.domain.entity.UserInterestEntity;
import com.qoormthon.todool.domain.user.domain.model.User;
import com.qoormthon.todool.domain.user.exception.UserDuplicatedException;
import com.qoormthon.todool.domain.user.exception.UserFindException;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class UserPersistenceAdapter implements ExistsByUserPort, FindUserPort, SaveUserPort {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserPersistenceAdapter(UserRepository userRepository, UserMapper userMapper,
                                  UserInterestRepository userInterestRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userInterestRepository = userInterestRepository;
    }


    @Override
    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public User findByUserId(String userId) {
        if(!userRepository.existsByUserId(userId)) { throw new UserFindException(ErrorCode.USER_NOT_FOUND); }
        List<Long> userInterestIdList = userInterestRepository.findAllByUserId(userId).stream()
                .map(UserInterestEntity::getInterestId)
                .toList();
        User user = userMapper.UserEntityToUser(userRepository.findByUserId(userId), userInterestIdList);

        return user;
    }

    @Override
    public void saveUser(User user) {

        if(userRepository.existsByUserId(user.getUserId())) {
          throw new UserDuplicatedException(ErrorCode.DUPLICATED_USER);
        }
        userRepository.save(userMapper.UserToUserEntity(user));

    }
}
