package com.upday.dal;

import com.upday.domain.Article;
import com.upday.pagination.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author jalajchawla
 */
public interface ArticleDAL {
    List<Article> getAllArticlesByMultipleParams(List<String> authorIds, Long from, Long to, String keyword, PageRequest page);

    List<Article> fetchArticleById(List<String> articleIds);

    Optional<Article> findArticleByAuthorAndHeader(String authorId, String header);
}
