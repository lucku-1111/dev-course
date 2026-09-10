package com.example.spring.kotlinpart4.service

import com.example.spring.kotlinpart4.domain.entity.Board
import com.example.spring.kotlinpart4.domain.repository.BoardRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoardService (private val repository: BoardRepository) {

    fun getBoards(page: Int, size: Int, keyword: String?): Page<Board> {

        val pageable: Pageable = PageRequest.of(page - 1, size, Sort.by("id").descending())

        return if ( keyword.isNullOrBlank() ) {
            repository.findAll(pageable)
        } else {
            repository.findByTitleContaining(keyword, pageable)
        }
    }

}