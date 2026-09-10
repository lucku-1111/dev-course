package com.example.spring.kotlinpart4.controller

import com.example.spring.kotlinpart4.dto.BoardCreateRequest
import com.example.spring.kotlinpart4.dto.BoardPageResponse
import com.example.spring.kotlinpart4.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/boards")
class BoardApiController(private val boardService: BoardService) {

    @GetMapping
    fun getBoards(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "keyword", required = false) keyword: String?
    ): BoardPageResponse = BoardPageResponse.from(boardService.getBoards(page, size, keyword))

    @PostMapping
    fun createBoard(@RequestBody request: BoardCreateRequest) {
        boardService.createBoard(request)
    }

}