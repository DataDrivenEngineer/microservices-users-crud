package dbelousov.backend.hellorestapi.service;

import dbelousov.backend.hellorestapi.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
    UserDto updateUser(UserDto user, String id);
    UserDto deleteUser(String id);
    List<UserDto> getUsers(int page, int limit);
}
