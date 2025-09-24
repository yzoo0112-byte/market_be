package com.market_be.dtotest;

import java.util.List;

public class ApiResponse {
    private List<ViewPost> data;
    private int total;

    public ApiResponse(List<ViewPost> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<ViewPost> getData() {
        return data;
    }

    public void setData(List<ViewPost> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
