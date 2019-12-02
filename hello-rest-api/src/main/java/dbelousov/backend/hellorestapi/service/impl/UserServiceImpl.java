package dbelousov.backend.hellorestapi.service.impl;

import dbelousov.backend.hellorestapi.io.entity.UserEntity;
import dbelousov.backend.hellorestapi.io.repositories.UserRepository;
import dbelousov.backend.hellorestapi.service.UserService;
import dbelousov.backend.hellorestapi.shared.Utils;
import dbelousov.backend.hellorestapi.shared.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new UsernameNotFoundException(id);

        if (userDto.getFirstName() != null) {
            userEntity.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            userEntity.setLastName(userDto.getLastName());
        }

        userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto deleteUser(String id) {

        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new UsernameNotFoundException(id);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        userRepository.delete(userEntity);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException(userId);

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {

        List<UserDto> returnValue = new ArrayList<>();

        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
        List<UserEntity> usersPage = userPage.getContent();

        for (UserEntity userEntity : usersPage) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
