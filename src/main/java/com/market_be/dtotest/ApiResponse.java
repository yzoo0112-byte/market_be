package com.market_be.dtotest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
// API 응답용 DTO
public class ApiResponse {
    private List<Post> data;
    private int total;

    public ApiResponse(List<Post> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<Post> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}
