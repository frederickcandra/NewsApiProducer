package com.fetch.ProducerNewsAPI.controller;

import com.fetch.ProducerNewsAPI.model.NewsModel;
import com.fetch.ProducerNewsAPI.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/fetch")
    public List<NewsModel> getAndSaveNews() {
        return newsService.fetchNewsAndSave();
    }
}
