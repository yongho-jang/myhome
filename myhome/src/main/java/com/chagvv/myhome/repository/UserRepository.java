package com.chagvv.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chagvv.myhome.model.User;

public interface UserRepository  extends JpaRepository<User, Long> {

}
