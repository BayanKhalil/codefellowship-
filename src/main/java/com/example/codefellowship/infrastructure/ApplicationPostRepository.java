package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPostRepository extends JpaRepository<Post,Long> {

}
