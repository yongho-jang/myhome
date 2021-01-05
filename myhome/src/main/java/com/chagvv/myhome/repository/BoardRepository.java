package com.chagvv.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chagvv.myhome.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
