package com.moviment.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int viewCount;
    private String createdAt;
}
