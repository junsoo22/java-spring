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


    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //서비스를 통해 게시글 수정
        Article updated =articleService.update(id,dto);

        //수정되면 정상, 안되면 오류 응답
        return (updated!=null) ? ResponseEntity.status(HttpStatus.OK).body(updated):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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
