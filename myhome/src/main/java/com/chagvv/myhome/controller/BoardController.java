package com.chagvv.myhome.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chagvv.myhome.model.Board;
import com.chagvv.myhome.repository.BoardRepository;
import com.chagvv.myhome.service.BoardService;
import com.chagvv.myhome.validator.BoardValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	private BoardRepository boardRepository;
	private BoardValidator boardValidatory;
	private BoardService boardService;
	
	@GetMapping("/list")
	public String list(Model  model,@PageableDefault(page = 0,size = 5) Pageable pageable,@RequestParam(required = false,defaultValue = "") String searchText) {
		
		int pageNumberSize=4;
	
		//List<Board> boards = boardRepository.findAll();
		
		//Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
		//Page<Board> boards = boardRepository.findAll(pageable);
		Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
		int page = pageable.getPageNumber();
		int startPage = (page/pageNumberSize)*pageNumberSize+1;

		//int startPage = Math.max(1, pageable.getPageNumber() - pageNumberSize);
		int endPage = Math.min(boards.getTotalPages(),startPage+pageNumberSize-1);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("boards", boards);
		return "board/list";
	}
	
	@GetMapping("/form")
	public String form(Model  model, @RequestParam(required = false) Long id) {
		
		if(id==null) {
			model.addAttribute("board", new Board());
		}else {
			Board board = boardRepository.findById(id).orElse(null);
			model.addAttribute("board", board);
		}
		return "board/form";
	}
	
	@PostMapping("/form")
	public String formSubmit(@Valid Board board, BindingResult bindingResult) {
		
		boardValidatory.validate(board, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "board/form";
		}
		
		boardService.save(board);
		
		return "redirect:/board/list";
	}
}
