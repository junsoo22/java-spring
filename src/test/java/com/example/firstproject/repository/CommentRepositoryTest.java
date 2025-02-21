package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import jakarta.activation.CommandMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest                     //해당 클래스를 JPA와 연동해 테스팅
class CommentRepositoryTest {
    @Autowired        //객체 주입
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")        //테스트 이름을 붙일 때 사용하는 어노테이션.
    void findByArticleId() {
        //case 1: 4번 게시글의 모든 댓글 조회
        {
            //1. 입력 데이터 준비
            Long articleId=4L;   //4번 게시글의 모든 댓글을 조회 하므로 4 넣어줌
            //2. 실제 데이터
            List<Comment>comments=commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(4L,"당신의 인생 영화는?","댓글 고");
            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");       //댓글 객체 생성
            Comment b=new Comment(2L,article,"Kim","아이 엠 셈");
            Comment c =new Comment(3L,article,"Choi","쇼생크 탈출");
            List<Comment>expected= Arrays.asList(a,b,c);    //댓글 객체를 하나의 리스트로 합침
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"4번 글의 모든 댓글 출력!");

        }
    }

    @Test
    void findByNickname() {
    }
}