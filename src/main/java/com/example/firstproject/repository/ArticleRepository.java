package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//라퍼지토리: 엔티티가 DB 속 테이블에 저장 및 관리될 수 있게 하는 인터페이스.
//2개의 제네릭 요소 받음. Article: 관리 대상 엔티티의 클래스 타입. Long: 관리 대상 엔티티의 대표값 타입. id의 타입이 Long이므로
public interface ArticleRepository extends CrudRepository<Article,Long> {   //CrudRepository: JPA에서 제공하는 인터페이스로 이를 상속해 엔티티를 관리(쑤정, 생성, 조회, 삭제)할 수 있음

    @Override
    ArrayList<Article> findAll();
}
