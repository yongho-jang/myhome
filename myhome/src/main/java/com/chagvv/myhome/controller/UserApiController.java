package com.chagvv.myhome.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chagvv.myhome.model.Board;
import com.chagvv.myhome.model.User;
import com.chagvv.myhome.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
class UserApiController {

  private final UserRepository repository;

  @GetMapping("/Users")
  List<User> all() {
	  return repository.findAll();
  }

  @PostMapping("/Users")
  User newUser(@RequestBody User newUser) {
    return repository.save(newUser);
  }

  // Single item

  @GetMapping("/Users/{id}")
  User one(@PathVariable Long id) {

    return repository.findById(id)
      .orElse(null);
  }

  @PutMapping("/Users/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

    return repository.findById(id)
      .map(user -> {
//        user.setTitle(newUser.getTitle());
//        user.setContent(newUser.getContent());
//    	  user.setBoards(newUser.getBoards());
    	  user.getBoards().clear();
    	  user.getBoards().addAll(newUser.getBoards());
    	  for(Board board :user.getBoards()) {
    		  board.setUser(user);
    	  }
        return repository.save(user);
      })
      .orElseGet(() -> {
        newUser.setId(id);
        return repository.save(newUser);
      });
  }

  @DeleteMapping("/Users/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
}