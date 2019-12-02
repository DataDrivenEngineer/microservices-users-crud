package dbelousov.backend.hellorestapi.service.impl;

import dbelousov.backend.hellorestapi.io.entity.UserEntity;
import dbelousov.backend.hellorestapi.io.repositories.UserRepository;
import dbelousov.backend.hellorestapi.shared.Utils;
import dbelousov.backend.hellorestapi.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Utils utils;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserEntity userEntity;
    String firstName = "Dmitry";
    String lastName = "Belousov";
    String userId = "sdjfhsdj36454sdss";
    String email = "test@test.com.test";
    String encryptedPassword = "dfdskfj4354dsfsdff";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setUserId(userId);
        userEntity.setEmail(email);
        userEntity.setEncryptedPassword(encryptedPassword);
    }

    @Test
    final void testGetUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com.test");
        assertNotNull(userDto);
        assertEquals("Dmitry", userDto.getFirstName());
        assertEquals("Belousov", userDto.getLastName());
        assertEquals("sdjfhsdj36454sdss", userDto.getUserId());
        assertEquals("test@test.com.test", userDto.getEmail());
    }

    @Test
    final void testGetUser_UserNameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    UserDto userDto = userService.getUser("test@test.com");
                });
    }

    @Test
    final void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setFirstName("Alec");
        userDto.setLastName("Baldwin");
        userDto.setEmail("alec@test.com");
        userDto.setPassword("12345");

        UserDto storedUserDetails = userService.createUser(userDto);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertEquals(userEntity.getEmail(), storedUserDetails.getEmail());
        assertEquals(userEntity.getEncryptedPassword(), storedUserDetails.getEncryptedPassword());
        assertNotNull(storedUserDetails.getUserId());
        verify(bCryptPasswordEncoder, times(1)).encode("12345");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}