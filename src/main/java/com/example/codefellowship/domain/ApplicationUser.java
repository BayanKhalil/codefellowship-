package com.example.codefellowship.domain;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import java.util.List;
import java.util.Set;


@Entity
public class ApplicationUser implements UserDetails{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  long id;

    @Column(unique=true)
    String username;
    String password;
    String firstName;
    String lastName;
    String dateOfBirth;
    String bio;


    @OneToMany(mappedBy="applicationUser")
    public List<Post> posts;


    public ApplicationUser(){}
    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }


    @ManyToMany
    @JoinTable(
            name="postersAndFollowers",
            joinColumns = { @JoinColumn(name="follower") },
            inverseJoinColumns = { @JoinColumn(name = "poster")}
    )
    Set<ApplicationUser> usersIFollowing;

    @ManyToMany(mappedBy = "usersIFollowing" , fetch = FetchType.EAGER)
    Set<ApplicationUser> usersFollowingMe;

    public void followUser(ApplicationUser followedUser){

        usersIFollowing.add(followedUser);
    }


    public Set<ApplicationUser> getUsersIFollowing() {
        return usersIFollowing;
    }

    public Set<ApplicationUser> getUsersFollowingMe() {
        return usersFollowingMe;
    }

    public long getId() {
        return id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
