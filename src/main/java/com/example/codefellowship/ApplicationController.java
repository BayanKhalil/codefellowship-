package com.example.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;


@Controller
public class ApplicationController {


    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    // Get splash page
    @GetMapping("/")
    public String splashPage(Model modle){
        modle.addAttribute("user", false);
        return "splash";
    }

    @GetMapping("/signUp")
    public String getSignUpPage(Model modle) {
        modle.addAttribute("user", false);
        return "signUp";
    }

    @GetMapping("/login")
    public String getLoginPage(Model modle) {
        modle.addAttribute("user", false);
        return "login";
    }


    @PostMapping("/signUp")
    public RedirectView createUser(String username, String password, String firstName, String lastName,
                                   String dateOfBirth, String bio ) {
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName,
                lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        // For autologin
        ApplicationUser user = applicationUserRepository.findUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        long id = newUser.getId();
        return new RedirectView("/users/"+id);

    }


    @GetMapping("/users/{id}")
    public String getMyProfile(@PathVariable long id, Model modle){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepository.findById(id).get();
        modle.addAttribute("viewedUser",currentUser);
        modle.addAttribute("username",userDetails.getUsername());
        return "userProfile";
    }

    @GetMapping("/userProfile")
    public String getUserProfilePage( Model modle){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepository.findUserByUsername(userDetails.getUsername());
        modle.addAttribute("viewedUser", currentUser);
        modle.addAttribute("username",userDetails.getUsername());
        modle.addAttribute("user", true);
        return "userProfile";
    }







}
