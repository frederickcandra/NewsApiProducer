package com.fetch.ProducerNewsAPI.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsModel {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
    private String sourceName;

    public NewsModel(String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String sourceName) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.sourceName = sourceName;
    }
}
