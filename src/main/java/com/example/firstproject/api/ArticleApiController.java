package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;
    //GET
    @GetMapping("/api/articles")
    public List<Article>index(){
        return articleRepository.findAll();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    //POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){   //@RequestBody: 본문에 실어 보내는 데이터를 create() 메서드의 매개변수로 받아 올 수 있음.
        Article article=dto.toEntity();
        return articleRepository.save(article);
    }
    //PATCH
    @PatchMapping("/api/articles/{id}")      //URL 요청 접수
    //ResponseEntity: REST API의 응답을 위해 사용하는 클래스.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){     //요청 URL의 id와 요청 메시지의 본문 데이터를 받아옴
        //1. DTO -> 엔티티 변환
        Article article=dto.toEntity();    //dto를 엔티티로 변환
        log.info("id:{}, article:{}",id,article.toString());
        //2. 타깃 조회하기
        Article target=articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target==null || id !=article.getId()){
            //400. 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article:{}",id,article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
        //4. 업데이트 및 정상 응답(200)하기.
        target.patch(article);
        Article updated=articleRepository.save(article);    //article 엔티티 DB에 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    //DELETE
}
