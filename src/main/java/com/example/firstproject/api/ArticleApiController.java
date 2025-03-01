package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
//    @Autowired
//    private ArticleRepository articleRepository;

    @Autowired      //생성객체를 가져와 연결. ArticleService 클래스에서 객체를 만들면 REST 컨트롤러에서 객체 주입하는 방식으로 서비스 객체 선언
    private ArticleService articleService;

    //Get
    @GetMapping("/api/articles")
    public List<Article>index(){
        return articleService.index();    //서비스를 통해 데이터를 가져오고, 서비스는 리퍼지토리를 통해 데이터 가져옴.
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //Post
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created=articleService.create(dto);
        return (created!=null) ? ResponseEntity.status(HttpStatus.OK).body(created):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    //PATCH
//    @PatchMapping("/api/articles/{id}")      //URL 요청 접수
//    //ResponseEntity: REST API의 응답을 위해 사용하는 클래스.
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){     //요청 URL의 id와 요청 메시지의 본문 데이터를 받아옴
//        //1. DTO -> 엔티티 변환
//        Article article=dto.toEntity();    //dto를 엔티티로 변환
//        log.info("id:{}, article:{}",id,article.toString());
//        //2. 타깃 조회하기
//        Article target=articleRepository.findById(id).orElse(null);
//        //3. 잘못된 요청 처리하기
//        if(target==null || id !=article.getId()){
//            //400. 잘못된 요청 응답!
//            log.info("잘못된 요청! id: {}, article:{}",id,article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//
//        }
//        //4. 업데이트 및 정상 응답(200)하기.
//        target.patch(article);
//        Article updated=articleRepository.save(article);    //article 엔티티 DB에 저장
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated =articleService.update(id,dto);
        return (updated!=null) ? ResponseEntity.status(HttpStatus.OK).body(updated):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    //DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id){
//        //1. 대상 찾기
//        Article target=articleRepository.findById(id).orElse(null);
//        //2. 잘못된 요청 처리하기
//        if(target==null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        //3. 대상 삭제하기
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//
//    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted=articleService.delete(id);
        //delete에 내용이 있다면 삭제됐다는 뜻이므로 상태에는 NO_content, 본문은 그냥 빌드만 해서 보냄.
        return(deleted!=null)?ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    //@RequestBody 어노테이션: POST요청 시 본문에 실어 보내는 데이터를 transactionTest() 메서드의 매개변수로 받아오는 역할을 함.
    public ResponseEntity<List<Article>>transactionTest(@RequestBody List<ArticleForm> dtos){   //여러 데이터 받으므로 List로 묶은 dtos 선언
        List<Article>createdList=articleService.createArticles(dtos);    //서비스 호출
        return (createdList!=null) ? ResponseEntity.status(HttpStatus.OK).body(createdList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}
