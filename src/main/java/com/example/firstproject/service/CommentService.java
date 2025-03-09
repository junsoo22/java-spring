package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service       //서비스로 선언
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;      //댓글 리파지터리 객체 주입

    @Autowired
    private ArticleRepository articleRepository;      //게시글 리파지터리 객체 주입

    public List<CommentDto> comments(Long articleId) {
//        //1. 댓글 조회
//        List<Comment> comments=commentRepository.findByArticleId(articleId);
//        //2. 엔티티->DTO 변환
//        List<CommentDto>dtos=new ArrayList<CommentDto>();
//        for(int i=0;i<comments.size();i++){     //조회한 댓글 수만큼 반복하기
//            Comment c=comments.get(i);      //조회한 댓글 엔티티 하나씩 가져오기
//            CommentDto dto=CommentDto.createCommentDto(c);      //엔티티를 DTO로 변환
//            dtos.add(dto);      //변환한 DTO를 dtos 리스트에 삽입
//        }
//        //3. 결과 반환
//        return dtos;

        //1.댓글 조회
        //2. 엔티티->DTO 변환
        //3. 결과 반환
        return commentRepository.findByArticleId(articleId)       //댓글 엔티티 목록 바로 가져옴
                .stream()
                .map(comment->CommentDto.createCommentDto(comment))       //.map(a->b): 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑
                .collect(Collectors.toList());       //스트림을 리스트로 변환

    }

    @Transactional      //문제가 발생했을 때 롤백하도록
    public CommentDto create(Long articleId, CommentDto dto) {

        //1. 게시글 조회 및 예외 발생
        Article article=articleRepository.findById(articleId)
                .orElseThrow(()->new IllegalArgumentException("댓글 생성 실패! "+"대상 게시글이 없습니다."));    //부모 게시글이 없다면 orElseThrow() 메서드로 예외 발생시킴
        //2. 댓글 엔티티 생성
        Comment comment=Comment.createComment(dto,article);       //댓글 DTO(dto)와 게시글 엔티티(article)를 입력받아 댓글 엔티티를 만듬.
        //3. 댓글 엔티티를 DB에 저장
        Comment created=commentRepository.save(comment);
        //4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

        //1. 댓글 조회 및 예외발생
        Comment target=commentRepository.findById(id)        //수정할 댓글 가져오기
                .orElseThrow(()->new IllegalArgumentException("댓글 수정 실패!"+"대상 댓글이 없습니다."));
        //2. 댓글 수정
        target.patch(dto);      //patch 메서드 호출해 기존 댓글 엔티티에 수정 정보를 추가

        //3. DB로 갱신
        Comment updated=commentRepository.save(target);
        //4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long id) {

        //1. 댓글 조회 및 예외 발생
        Comment target=commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패!"+"대상이 없습니다."));
        //2. 댓글 삭제
        commentRepository.delete(target);
        //3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
