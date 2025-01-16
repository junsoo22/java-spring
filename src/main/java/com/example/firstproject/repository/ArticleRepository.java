package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,Long> {   //CrudRepository: JPA에서 제공하는 인터페이스로 이를 상속해 엔티티를 관리(쑤정, 생성, 조회, 삭제)할 수 있음

}
