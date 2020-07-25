package org.galati2.springtime.controllers;

import org.galati2.springtime.model.PendingUser;
import org.galati2.springtime.model.User;
import org.galati2.springtime.repository.UserRepository;
import org.galati2.springtime.service.EmailService;
import org.galati2.springtime.service.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String registerUser() {

        return "registration/register";
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public String registerUser(String username, String password, String emailAddress) {
        User user = new User();
        user.setEmailAddress(emailAddress);
        user.setPassword(encoder().encode(password));
        user.setUsername(username);

        userRepository.save(user);

        PendingUser pendingUser = new PendingUser();
        String activationCode = randomStringGenerator.getAlphaNumericString(20);

        emailService.sendHTML("buhaidebalta.15@gmail.com", user.getEmailAddress(), "Please confirm account" , activationCode) ;

        pendingUser.setActivationCode(activationCode);
        pendingUser.setUser(user);

        //TODO use expiration date
        // TODO do not allow to login if it is a pending user

        return "redirect:/login";
    }
}

