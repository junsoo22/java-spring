package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment,Long>{    //대상 엔티티는 Comment, 대표키(id)의 타입=Long
    //CRUD 작업+페이지 처리와 정렬 작업까지 할 때, JpaRepository 인터페이스 사용

    //특정 게시글의 모든 댓글 조회
    //JPQL: 객체지향쿼리 언어를 통해 복잡한 쿼리 처리를 지원->natvieQuery=true로 설정하면 SQL문 사용 가능
    @Query(value="Select * from comment where article_id=:articleId",nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);   //댓글의 묶음을 반환하므로 List형

    //특정 닉네임의 모든 댓글 조회
    //XML로 작성
    List<Comment> findByNickname(String nickname);
}
