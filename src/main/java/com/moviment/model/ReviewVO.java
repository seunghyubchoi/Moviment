package com.moviment.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewVO {
    private int id;
    private String userId;
    private String movieId;
    private String content;
    private String createdAt;
    private String userEmail;
    private String userName;
}
