package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor  //클래스 안쪽의 모든 필드, title과 content를 매개변수로 하는 생성자 자동으로 만들어짐
@ToString
public class ArticleForm {     //폼 데이터를 받아올 그릇, DTO가 된다.
    private Long id;
    private String title;   //제목
    private String content;   //내용


    public Article toEntity() {

        return new Article(id, title, content);   //폼 데이터를 담은 DTO 객체를 엔티티로 반환함
    }
}
