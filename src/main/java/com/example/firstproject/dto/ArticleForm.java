package com.example.firstproject.dto;

import org.springframework.web.bind.annotation.PostMapping;

public class ArticleForm {
    private String title;   //제목
    private String content;   //내용

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //데이터를 잘 받았는지 확인할 toString()메서드 추가
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){   //폼 데이터를 DTO로 받기
        System.out.println(form.toString());     //DTO에 폼 데이터가 잘 담겼는지 확인
        return "";
    }
}
