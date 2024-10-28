package com.fetch.ProducerNewsAPI.service;

import com.fetch.ProducerNewsAPI.model.NewsModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private static final String API_URL = "https://newsapi.org/v2/everything?q=tesla&from=2024-09-25&sortBy=publishedAt&apiKey=a5b8a8781be1486c9cc3828b32c97d95";

    private final KafkaTemplate<String, NewsModel> kafkaTemplate;

    public NewsService(KafkaTemplate<String, NewsModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private String truncateString(String str, int maxLength) {
        return (str != null && str.length() > maxLength) ? str.substring(0, maxLength) : str;
    }

    public List<NewsModel> fetchNewsAndSave() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        List<NewsModel> newsList = new ArrayList<>();

        if (response != null && response.containsKey("articles")) {
            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");

            newsList = articles.stream().map(article -> {
                Map<String, String> source = (Map<String, String>) article.get("source");
                String sourceName = source != null ? source.get("name") : null;

                String author = truncateString((String) article.get("author"), 255);
                String title = truncateString((String) article.get("title"), 255);
                String description = truncateString((String) article.get("description"), 255);
                String url = (String) article.get("url");
                String urlToImage = (String) article.get("urlToImage");
                String publishedAt = (String) article.get("publishedAt");
                String content = truncateString((String) article.get("content"), 255);

                NewsModel news = new NewsModel(author, title, description, url, urlToImage, publishedAt, content, sourceName);

                // Kirim berita ke Kafka
                kafkaTemplate.send("news-topic", news);
                return news;
            }).collect(Collectors.toList());
        }
        return newsList;
    }
}
