package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;
    private UsersServiceImpl usersService;
    User expectedUser;

    @BeforeEach
    public void setUp() {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
        expectedUser = new User("loginIsTrue", "passwordIsTrue");
    }

    @Test
    void isLoginAndPasswordCorrect() {
        Mockito.when(usersRepository.findByLogin("loginIsTrue")).thenReturn(expectedUser);
        boolean isAuthenticated = usersService.authenticate("loginIsTrue", "passwordIsTrue");
        assertTrue(isAuthenticated);
        Mockito.verify(usersRepository).update(expectedUser);
    }

    @Test
    void isPasswordNotCorrect() {
        Mockito.when(usersRepository.findByLogin("loginIsTrue")).thenReturn(expectedUser);
        boolean isAuthenticated = usersService.authenticate("loginIsTrue", "passwordIsFalse");
        assertFalse(isAuthenticated);
    }

    @Test
    void isLoginNotCorrect() {
        Mockito.when(usersRepository.findByLogin("loginIsFalse")).thenThrow(new EntityNotFoundException());
        try {
            boolean isAuthenticated = usersService.authenticate("loginIsFalse", "passwordIsTrue");
        } catch (EntityNotFoundException e) {
            Mockito.verify(usersRepository, never()).update(any());
        }
    }

    @Test
    void isUserIsNull() {
        expectedUser.setStatus(true);
        Mockito.when(usersRepository.findByLogin("loginIsTrue")).thenReturn(new User("loginIsTrue", "passwordIsTrue", true));
        assertThrows(AlreadyAuthenticatedException.class, () -> {
            boolean isAuthenticated = usersService.authenticate("loginIsTrue", "passwordIsTrue");
        });
    }

    @Test
    void isUserStatusUpdated() {
        Mockito.when(usersRepository.findByLogin("loginIsTrue")).thenReturn(expectedUser);
        boolean isAuthenticated = usersService.authenticate("loginIsTrue", "passwordIsTrue");
        Mockito.verify(usersRepository).update(expectedUser);
    }
}

