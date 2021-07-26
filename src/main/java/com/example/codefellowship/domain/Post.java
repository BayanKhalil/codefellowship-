package com.example.codefellowship.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String body;
    Date createdAt;


//    many post to one user
    @ManyToOne
    public ApplicationUser applicationUser;


    public Post(String body,Date createdAt) {
        this.body = body;
        this.createdAt = createdAt;


    }




    public Post(){}
    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
