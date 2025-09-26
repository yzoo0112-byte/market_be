package com.market_be.dtotest;

import com.market_be.dtotest.PostDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse {
    private List<PostDTO> data;
    private long total;

    public ApiResponse(List<PostDTO> data, long total) {
        this.data = data;
        this.total = total;
    }

    // Getters and Setters
}
