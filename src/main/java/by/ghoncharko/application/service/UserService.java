package by.ghoncharko.application.service;


import by.ghoncharko.application.dto.UserDto;
import by.ghoncharko.application.entity.User;
import by.ghoncharko.application.entity.UserRole;
import by.ghoncharko.application.error.LoginNotUniqueRegisterError;
import by.ghoncharko.application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public User saveUserAsClient(UserDto userDto){
        Optional<User> userOptional= userRepository.findUserByUserLogin(userDto.getUserLogin());
        if(userOptional.isPresent()){
            throw new LoginNotUniqueRegisterError("Login " + userDto.getUserLogin());
        }
        User user = User.builder().
                userLogin(userDto.getUserLogin()).
                userPassword(passwordEncoder.encode(userDto.getUserPassword())).
                userBalance(50000.0).
                userRole(new UserRole(2L,"CLIENT")).
                build();
        return userRepository.save(user);
    }
    @Transactional
    public Optional<User> findUserByUserLogin(String login){
        return userRepository.findUserByUserLogin(login);
    }
}
