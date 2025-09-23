package com.market_be.constant;

public enum HashtagType {
    ELECTRONICS("가전제품"),
    CLOTHING("의류");

    private final String hashtag;

    HashtagType(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getHashtag() {
        return hashtag;
    }
}
