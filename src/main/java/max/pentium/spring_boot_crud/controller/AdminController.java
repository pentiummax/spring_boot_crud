package max.pentium.spring_boot_crud.controller;

import max.pentium.spring_boot_crud.model.Role;
import max.pentium.spring_boot_crud.model.User;
import max.pentium.spring_boot_crud.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String admin() {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping(value = "/users/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping(value = "/users/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("all_roles", Role.values());
        return "new";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = {"/users/{id}/edit"})
    public String edit(Model model, @PathVariable long id) {
        User user = null;
        try {
            user = userService.getUser(id);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
        }
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("all_roles", Role.values());
        return "edit";
    }

    @PatchMapping(value = {"/users/{id}"})
    public String updateUser(@PathVariable long id,
                             @ModelAttribute("user") User user) {
        if (user.getPassword().matches("^\\s*$")) {
            user.setPassword(userService.getUser(id).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = {"/users/{id}"})
    public String deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
