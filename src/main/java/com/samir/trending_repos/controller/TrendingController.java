package com.samir.trending_repos.controller;

import com.samir.trending_repos.model.Language;
import com.samir.trending_repos.service.TrendingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TrendingController {

    final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("getTrendingRepos")
    public Map<String, Language> getTrendingRepos() throws IOException {
        return trendingService.getTrendingRepos();
    }

}
