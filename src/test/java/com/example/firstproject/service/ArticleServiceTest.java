package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest       //해당 클래스를 스프링 부트와 연동해 통합 테스트를 수행하겠다고 선언하는 것
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;         //articleService 객체 주입

    @Test           //해당 메서드가 테스트 코드임을 선언
    void index() {
        //1. 예상 데이터
        Article a =new Article(1L,"가가가가","1111");
        Article b =new Article(2L,"나나나나","2222");
        Article c =new Article(3L,"다다다다","3333");
        List<Article> expected=new ArrayList<Article>(Arrays.asList(a,b,c));       //a,b,c 합치기. asList(): 입력된 배열 또는 2개 이상의 동일한 타입 데이터를 정적 리스트로 만들어 반환함.
        System.out.println(expected);
        //2. 실제 데이터
        List<Article> articles = articleService.index();      //모든 게시글을 조회 요청하고 그 결과로 반환되는 게시글의 묶음을 받아오는 것.
        //3. 비교 및 검증
        assertEquals(expected.toString(),articles.toString());     //예상 데이터(X) 실제데이터(Y)를 비교해 일치하면 테스트 통과시킴.
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1. 예상 데이터
        Long id=1L;
        Article expected=new Article(id,"가가가가","1111");
        //2. 실제 데이터
        Article article=articleService.show(id);
        //3. 비교 및 검증
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void show_실패_존재하지_않는_id_입력(){
        //1. 예상 데이터
        Long id=-1L;
        Article expected=null;     //예상 데이터 null 저장. DB에 조회되는 내용이 없어 null 반환할 것이기 때문에.
        //2. 실제 데이터
        Article article=articleService.show(id);
        //3. 비교 및 검증
        assertEquals(expected,article);     //null은 toString()메서드를 호출할 수 없으므로.

    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        //1.예상 데이터
        String title="라라라라";      //새 게시물에 들어갈 내용
        String content="4444";
        ArticleForm dto=new ArticleForm(null,title,content);     //dto 생성
        Article expected=new Article(4L,title,content);      //자동 생성될 id 4.
        //2.실제 데이터
        Article article=articleService.create(dto);
        //3. 비교 및 검증
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력(){
        //1. 예상 데이터
        Long id=4L;     //id를 써 줄 필요가 없는데도 id를 입력한 상황이라고 가정. id를 넣으면 오류가 나고 null이 반환됨. expected객체에 null을 저장
        String title="라라라라";
        String content="4444";
        ArticleForm dto=new ArticleForm(id,title,content);
        Article expected=null;
        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 비교 및 검증
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력(){
        //1. 예상 데이터
        Long id=1L;
        String title="가나다라";
        String content="1234";
        ArticleForm dto=new ArticleForm(id,title,content);
        Article exptected=new Article(id,title,content);
        //2. 실제 데이터
        Article article=articleService.update(id, dto);
        //3. 비교 및 검증
        assertEquals(exptected.toString(),article.toString());

    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력(){
        // 예상 데이터
        Long id = 1L;
        String title = "AAAA";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(1L, "AAAA" , "1111");
        // 실제 데이터
        Article article = articleService.update(id, dto);
        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력(){
        //1. 예상 데이터
        Long id=-1L;
        String title="ㅂㅈㄷㄱ";
        String content="4321";
        ArticleForm dto=new ArticleForm(id,title,content);
        Article expected=null;
        //2. 실제 데이터
        Article article=articleService.update(id,dto);
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력(){
        Long id=1L;

        Article expected=new Article(id,"가가가가","1111");
        //2. 실제 데이터
        Article article=articleService.delete(id);
        //3. 비교 및 검증
        assertEquals(expected.toString(),article.toString());

    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력(){
        Long id=-1L;
        Article expected=null;
        Article article=articleService.delete(id);
        assertEquals(expected,article);

    }
}