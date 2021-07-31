package com.example.codefellowship;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
import com.example.codefellowship.infrastructure.ApplicationPostRepository;
import com.example.codefellowship.infrastructure.ApplicationUserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Controller
public class ApplicationController {


    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationPostRepository applicationPostRepository;


    // Get splash page
    @GetMapping("/")
    public String splashPage(Model model){
        model.addAttribute("user", false);
        return "splash";
    }

    @GetMapping("/signUp")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", false);
        return "signUp";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", false);
        return "login";
    }


    @PostMapping("/signUp")
    public RedirectView createUser(String username, String password, String firstName, String lastName,
                                   String dateOfBirth, String bio ) {
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName,
                lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        // For autologin
        ApplicationUser user = applicationUserRepository.findByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        long id = newUser.getId();
        return new RedirectView("/users/"+id);

    }


    @GetMapping("/users/{id}")
    public String getMyProfile(@PathVariable long id, Model model,Principal p){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepository.findById(id).get();
        model.addAttribute("viewedUser",currentUser);


        model.addAttribute("username", p.getName());

        return "myprofile";
    }

    @GetMapping("/myprofile")
    public String getUserProfilePage(Principal p, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(userDetails.getUsername());
       model.addAttribute("viewedUser", currentUser);
       model.addAttribute("user",true);
       model.addAttribute("username", p.getName());


        Iterable<Post> posts = applicationPostRepository.findAll();
        model.addAttribute("posts", posts);
        return "myprofile";
    }


//>>>>>>>>>>>>>>>>>>lab17<<<<<<<<<<<<<<<<<<<<<<
@PostMapping("/addPost")
public RedirectView createPost(@RequestParam  String body,Principal principal,Model model){
    ApplicationUser user = (ApplicationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    model.addAttribute("viewedUser",user);

    Post newPost = new Post(body,new Date());
    newPost.applicationUser = applicationUserRepository.findById(user.getId()).get();
    applicationPostRepository.save(newPost);

    return new RedirectView("/addPost");
}

    @GetMapping("/addPost")
    public String showPost( Model model,Principal principal){
        Iterable<Post> posts = applicationPostRepository.findAll();
        model.addAttribute("posts", posts);

        ApplicationUser user = (ApplicationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("viewedUser",user);

        model.addAttribute("username", principal.getName());

        return "/addPost";
    }

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>lab18<<<<<<<<<<<<<<<<<<<<<<<

    @GetMapping("/users")
    public String getAllUsers(Principal principal, Model model) {

        model.addAttribute("alluser", applicationUserRepository.findAll());
        model.addAttribute("username", principal.getName());

        return "Users";
    }
    @PostMapping ("/follow")
    public RedirectView followAUser(Principal principal, long followUser) {

        // user to follow and get current logged in user username
        ApplicationUser poster = applicationUserRepository.getById(followUser);

        // get current logged in user username
        ApplicationUser follower = applicationUserRepository.findByUsername(principal.getName());
        // add the curetn logged in user to the following of usertofollow
//        add usertofollow to current logged in user followers
        follower.followUser(poster);

        applicationUserRepository.save(follower);

        return new RedirectView("/myprofile");
    }

    @GetMapping("/feed")
    public String Feed(Principal principal, Model model) {


        ApplicationUser currentUser = applicationUserRepository.findByUsername(principal.getName());

        Set<ApplicationUser> followerList = currentUser.getUsersIFollowing();

        model.addAttribute("personThatIfollowList", followerList);

        model.addAttribute("username", principal.getName());
        return "feed";
    }

}
