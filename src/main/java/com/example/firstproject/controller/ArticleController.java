package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {
    @Autowired   //스프링부트가 미리 생성해 높은 객체를 가져다가 연결해줌. 의존성 주입
    private ArticleRepository articleRepository;


    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){   //폼 데이터를 DTO로 받기
        log.info(form.toString());     //로깅 코드 추가
        //System.out.println(form.toString());     //DTO에 폼 데이터가 잘 담겼는지 확인
        //1. DTO를 엔티티로 변환
        Article article=form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());
        //2. 라파지터리로 엔티티를 DB에 저장
        Article saved=articleRepository.save(article);   //article 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/"+saved.getId();    //id에 따라 articles/id 페이지를 재요청
    }

    @GetMapping("/articles/{id}")    //id는 변수로 사용됨
    public String show(@PathVariable Long id, Model model){   //매개변수로 id 받아오기   @Pathvariable: URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션
        log.info("id= "+id);    //id 잘 받았는지 확인하는 로그 찍기
        //1. id를 조회해 데이터 가져오기
        Article articleEntity=articleRepository.findById(id).orElse(null);   //orElse: id값으로 데이터를 찾을때 해당 id 값이없으면 null을 반환하라는 뜻

        //2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        //3. 뷰 페이지 설정하기
        return "articles/show";   //뷰페이지는 articles라는 디렉터리 안에 show라는 파일이 있다고 가정하고 반환
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 모델에 데이터 등록하기. 가져온 데이터를 받은 articleEntityList를 뷰 페이지로 전달할때는 모델 사용
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity=articleRepository.findById(id).orElse(null);   //DB에서 수정할 데이터 가져오기
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }
}
