package com.example.spring.kotlinpart4.domain.repository

import com.example.spring.kotlinpart4.domain.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {

    // 쿼리 메서드
    // - 메서드 이름을 스프링 데이터가 해석해서 SQL을 만들어준다.
    // findBy + title + containing : where title like %?%
    fun findByTitleContaining(keyword: String, pageable: Pageable): Page<Board>

}