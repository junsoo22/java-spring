package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity   //이 어노테이션이 붙은 클래스를 기반으로 DB에 테이블이 생성됨.
public class Article {
    @Id   //엔티티의 대표값 지정
    @GeneratedValue   //자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column   //title 필드 선언, DB테이블의 title 열과 연결됨
    private String title;
    @Column
    private String content;


//    //기본 생성자->도 어노테이션으로 줄일 수 있음
//    public Article(){
//
//    }

//    public Article(Long id, String title, String content) {
//
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//
//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
