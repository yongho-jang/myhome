package com.chagvv.myhome.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	@Size(min=2, max=30, message = "제목은 2자 이상 입력 하세요!")
	private String title;
	@Lob
	private String content;
	
	
	@ManyToOne()
	@JoinColumn(name="user_id",referencedColumnName = "id")
	@JsonIgnore
	private User user;
}
