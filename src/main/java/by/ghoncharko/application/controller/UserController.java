package by.ghoncharko.application.controller;


import by.ghoncharko.application.dto.UserDto;
import by.ghoncharko.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam("userLogin") String userLogin, @RequestParam("userPassword") String userPassword) {
        UserDto userDto = new UserDto();
        userDto.setUserLogin(userLogin);
        userDto.setUserPassword(userPassword);
        userService.saveUserAsClient(userDto);
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
