package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor   //클래스 안쪽의 모든 필드, title과 content를 매개변수로 하는 생성자 자동으로 만들어짐
@ToString
public class ArticleForm {
    private String title;   //제목
    private String content;   //내용

//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }


    //데이터를 잘 받았는지 확인할 toString()메서드 추가
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        return new Article(null,title,content);   //폼 데이터를 담은 DTO 객체를 엔티티로 반환함

    }
}
