package com.chagvv.myhome.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.chagvv.myhome.model.Board;
import com.chagvv.myhome.repository.BoardRepository;

@RestController
@RequestMapping("/api")
class BoardApiController {

  private final BoardRepository repository;

  BoardApiController(BoardRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/Boards")
  List<Board> all(@RequestParam(required=false, defaultValue = "") String title, 
		  @RequestParam(required=false, defaultValue = "")String content) {
	  if(StringUtils.isEmpty(title) && StringUtils.isEmpty(title)) {
		  return repository.findAll();
	  }else {
		  return repository.findByTitleOrContent(title,content);
	  }
  }
  // end::get-aggregate-root[]

  @PostMapping("/Boards")
  Board newBoard(@RequestBody Board newBoard) {
    return repository.save(newBoard);
  }

  // Single item

  @GetMapping("/Boards/{id}")
  Board one(@PathVariable Long id) {

    return repository.findById(id)
      .orElse(null);
  }

  @PutMapping("/Boards/{id}")
  Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

    return repository.findById(id)
      .map(Board -> {
        Board.setTitle(newBoard.getTitle());
        Board.setContent(newBoard.getContent());
        return repository.save(Board);
      })
      .orElseGet(() -> {
        newBoard.setId(id);
        return repository.save(newBoard);
      });
  }

  @DeleteMapping("/Boards/{id}")
  void deleteBoard(@PathVariable Long id) {
    repository.deleteById(id);
  }
}