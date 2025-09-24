package com.market_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
@Builder
public class CommentsDto {
    private Long id;

    private Long userId;

    private String comment;

    private Date createdAt;

    private Date updatedAt;
}
