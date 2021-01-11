package com.chagvv.myhome.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chagvv.myhome.model.Board;
import com.chagvv.myhome.model.User;
import com.chagvv.myhome.repository.BoardRepository;
import com.chagvv.myhome.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService {
	
	BoardRepository boardRepository;
	UserRepository userRepository;
	
	public void save(Board board) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		board.setUser(user);
		boardRepository.save(board);
	}
}
