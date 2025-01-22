package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service    //스프링 부트에 서비스 객체 생성.
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;   //게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article=dto.toEntity();
        if(article.getId()!=null){  //id는 데이터 생성할 때 굳이 넣을 필요 없음. article 객체에 id가 존재한다면 null을 반환
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. DTO -> 엔티티 변환
        Article article=dto.toEntity();    //dto를 엔티티로 변환
        log.info("id:{}, article:{}",id,article.toString());
        //2. 타깃 조회하기
        Article target=articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target==null || id != article.getId()){
            //400. 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article:{}",id,article.toString());
            return null;

        }
        //4. 업데이트 및 정상 응답(200)하기.
        target.patch(article);
        Article updated=articleRepository.save(target);    //article 엔티티 DB에 저장
        return updated;
    }

    public Article delete(Long id) {
        Article target=articleRepository.findById(id).orElse(null);
        if(target==null){
            return null;
        }
        articleRepository.delete(target);

        return target;
    }

    @Transactional      //메서드가 중간에 실패하더라도 롤백을 통해 이전 상태로 돌아갈 수 있음
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article>articleList=dtos.stream()        //dtos를 스트림화함
                .map(dto->dto.toEntity())       //map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑한다.
                .collect(Collectors.toList());           //이렇게 매핑한 것을 리스트로 묶는다.
        //2. 엔티티묶음을 DB에 저장하기
        articleList.stream()
                .forEach(article->articleRepository.save(article));     //article이 하나씩 올때마다 articleRepository를 통해 Db에 저장
        //4. 강제 예외 발생시키기
        articleRepository.findById(-1L)     //id가 -1인 데이터찾기
                .orElseThrow(()->new IllegalArgumentException("결제 실패!"));    //찾는 데이터 없으면 예외 발생
        //4. 결과 값 반환하기
        return articleList;

    }
}
