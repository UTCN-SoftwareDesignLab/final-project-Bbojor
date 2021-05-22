package com.example.demo.board;


import com.example.demo.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.example.demo.UrlMapping.BOARDS;
import static com.example.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(BOARDS)
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDTO> findAll() { return boardService.findAll();}

    @GetMapping(ENTITY)
    public BoardDTO getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping(ENTITY)
    public BoardDTO edit(@PathVariable Long id, @Valid @RequestBody BoardDTO boardDTO) { return boardService.edit(id, boardDTO); }

    @PostMapping
    public BoardDTO create(@Valid @RequestBody BoardDTO boardDTO) { return boardService.create(boardDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { boardService.delete(id);}
}
