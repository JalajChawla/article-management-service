package com.upday.service;

import com.upday.domain.Article;
import com.upday.pagination.PageRequest;
import com.upday.service.dto.ArticleDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author jalajchawla
 */
public interface ArticleManagerService {
    List<Article> fetchArticleById(List<String> id) ;

    Optional<Article> findArticleByAuthorAndHeader(String authorId, String header) ;

    String deleteArticleById(List<String> articleIds);

    List<Article> createArticle(List<ArticleDTO> articles);

    Optional<Article> updateArticle(ArticleDTO articleDTO);

    List<Article> getAllArticlesByMultipleParams(List<String> authorIds, Long from, Long to, String keyword, PageRequest page);
}
