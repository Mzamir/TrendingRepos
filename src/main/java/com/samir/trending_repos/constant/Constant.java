package com.samir.trending_repos.constant;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Constant {

    public static final String LANGUAGE_PARAMETER = "language";
    public static final String REPO_FULL_NAME_PARAMETER = "full_name";
    public static final String REPO_URL_PARAMETER = "html_url";

    public static String getUrl() {
        return "https://api.github.com/search/repositories?q=created:"
                + LocalDate.now()
                + "&sort=stars&order=desc&per_page=100";
    }
}
