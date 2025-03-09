package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     //Rest 컨트롤러 선ㅇ언
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    //1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    //<List<CommentDto>>: DB에서 조회한 댓글 엔티티 목록은 List<Comment>이지만
    //엔티티를 DTO로 변환하면 List<CommentDto>가 되기 때문
    //ResponseEntity 클래스를 활용
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        //서비스에 위임
        List<CommentDto> dtos=commentService.comments(articleId);     //서비스에 댓글 조회를 위임하기 위해 CommentService의 comments(articleId) 메서드를 호출
        //결과에 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);     //댓글 조회 실패할 경우 스프링 부트가 예외 처리를 한다고 가정하고, 성공하는 경우만 응답 보냄.

    }
    //2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto>create(@PathVariable Long articleId, @RequestBody CommentDto dto){    //@RequestBody: HTTP 요청의 BODY로부터 JSON 데이터를 자바 객체로 변환해줌
        //서비스에 위임
        CommentDto createdDto=commentService.create(articleId,dto);    //댓글이 소속될 부모 게시글의 id인 articleId와 생성 데이터인 dto를 넘김
        //결과에 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);    //생성한 댓글 데이터인 createdDto를 실어서 보냄
    }

    //3. 댓글 수정
    @PatchMapping("/api/comments/{id}")   //여기서 id는 수정 대상인 댓글의 id
    public ResponseEntity<CommentDto>update(@PathVariable Long id,@RequestBody CommentDto dto){
        //서비스에 위임
        CommentDto updatedDto=commentService.update(id,dto);     //수정할 댓글의 iㅇ와 수정 데이터인 dto를 넘김
        //결과에 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    //4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto>delete(@PathVariable Long id){
        //서비스에 위임
        CommentDto deletedDto=commentService.delete(id);
        //결과에 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
