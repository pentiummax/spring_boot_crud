package max.pentium.spring_boot_crud.controller;

import max.pentium.spring_boot_crud.model.Role;
import max.pentium.spring_boot_crud.model.User;
import max.pentium.spring_boot_crud.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User user) {
        if (user.getPassword().matches("^\\s*$")) {
            user.setPassword(userService.getUser(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("all_roles", Role.values());
        return "admin";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("all_roles", Role.values());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = {"/delete"})
    public String deleteUserById(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }
}
