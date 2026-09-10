package com.example.spring.kotlinpart4.dto

import com.example.spring.kotlinpart4.domain.entity.Board
import org.springframework.data.domain.Page
import java.time.format.DateTimeFormatter

private val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

data class BoardPageResponse(
    val boards: List<BoardListItemResponse>,
    val page: Int,
    val totalPages: Int,
    val totalElements: Long,
    val last: Boolean,
) {
    // static
    companion object {
        fun from(page: Page<Board>): BoardPageResponse = BoardPageResponse(
            boards = page.content.map(BoardListItemResponse::from),
            page = page.number + 1,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            last = page.isLast,
        )
    }

}

data class BoardListItemResponse(
    val id: Long,
    val title: String,
    val userId: String,
    var created: String,
) {

    companion object {
        fun from(board: Board): BoardListItemResponse = BoardListItemResponse(
            // !! null아님을 보증한다.
            id = board.id!!,
            title = board.title,
            userId = board.userId,
            created = board.created.format(DATE_FORMAT)
        )
    }

}