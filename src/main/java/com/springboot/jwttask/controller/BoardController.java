//package com.springboot.jwttask.controller;
//
//import com.springboot.jwttask.dto.BoardDto.BoardRequestDto;
//import com.springboot.jwttask.dto.BoardDto.BoardResponseDto;
//import com.springboot.jwttask.service.BoardService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/board-api")
//public class BoardController {
//
//    private Logger logger = LoggerFactory.getLogger(BoardController.class);
//
//    private final BoardService boardService;
//    @Autowired
//    public BoardController(BoardService boardService){
//        this.boardService = boardService;
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<BoardResponseDto> getBoard(Long id){
//        BoardResponseDto boardResponseDto = boardService.getContent(id);
//        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
//    }
//    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
//    @PostMapping("/save")
//    public ResponseEntity<BoardResponseDto> saveBoard(@RequestBody BoardRequestDto boardRequestDto ){
//        BoardResponseDto boardResponseDto = boardService.saveBoard(boardRequestDto);
//        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
//    }
//    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
//    @PutMapping("/change")
//    public ResponseEntity<BoardResponseDto> saveBoard(@RequestBody BoardChangeDto boardChangeDto){
//        BoardResponseDto boardResponseDto = boardService.changeBoard(boardChangeDto.getId(),
//                boardChangeDto.getTitle(),boardChangeDto.getContent());
//        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
//    }
//    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
//    @DeleteMapping("/deleteBoard")
//    public ResponseEntity<String> saveGeustBoard(Long id){
//        boardService.deleteBoard(id);
//        return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
//    }
//}