package by.ghoncharko.application.controller;


import by.ghoncharko.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/client")
public class ClientController {

    private final UserService userService;


    @GetMapping("/home")
    public String homePage() {
        return "сhome";
    }

    @GetMapping("/games")
    public String gamesPage() {
        return "games";
    }
}