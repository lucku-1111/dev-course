package com.example.spring.kotlinpart4.controller

import com.example.spring.kotlinpart4.service.BoardService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/boards")
class BoardApiController(private val boardService: BoardService) {



}