package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Objects;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user;
        try {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            user = usersRepository.findByLogin(login);
            if (user.isStatus()) {
                throw new AlreadyAuthenticatedException("User has already been authenticated");
            }
        } catch (EntityNotFoundException e) {
            System.err.println("login is not found");
            throw e;
        }
        if (Objects.requireNonNull(user).getPassword().equals(password)) {
            user.setStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
