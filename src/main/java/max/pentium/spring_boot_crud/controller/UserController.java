package max.pentium.spring_boot_crud.controller;

import max.pentium.spring_boot_crud.model.User;
import max.pentium.spring_boot_crud.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(name);
        model.addAttribute("user", user);
        return "user";
    }

}
