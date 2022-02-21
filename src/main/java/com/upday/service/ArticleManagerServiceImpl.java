package com.upday.service;

import com.upday.dal.ArticleDAL;
import com.upday.domain.Article;
import com.upday.pagination.PageRequest;
import com.upday.repo.ArticleRepository;
import com.upday.service.dto.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * @author jalajchawla
 */
@Service
@Slf4j
public class ArticleManagerServiceImpl implements ArticleManagerService {
    private final ArticleRepository articleRepository;
    @Autowired
    private ArticleDAL articleDal;


    public ArticleManagerServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

    }

    @Override
    public List<Article> fetchArticleById(List<String> articleIds) {
        return articleDal.fetchArticleById(articleIds);
    }

    @Override
    public Optional<Article> findArticleByAuthorAndHeader(String authorId, String header) {
        return articleDal.findArticleByAuthorAndHeader(authorId,header);
    }

    @Override
    public String deleteArticleById(List<String> articleIds) {
        List<Article> articles =  fetchArticleById(articleIds);
        articles.forEach(article -> {
            article.setIsDeleted(true);
            article.setLastModifiedDate(Instant.now().toEpochMilli());
        });
        articleRepository.saveAll(articles);
        return String.format("ArticleIds : %s deleted successfully", articleIds);
    }

    @Override
    public List<Article> createArticle(List<ArticleDTO> articles) {
        List<Article> articleList = new ArrayList<>();
        articles.forEach(articleDTO -> {
            Article article = new Article();
            article.setHeader(articleDTO.getHeader());
            article.setText(articleDTO.getText());
            article.setShortDesc(articleDTO.getShortDesc());
            article.setKeyword(articleDTO.getKeyword());
            article.setCreatedBy(articleDTO.getAuthor());
            article.setLastModifiedDate(Instant.now().toEpochMilli());
            article.setCreatedDate(Instant.now().toEpochMilli());
            article.setIsDeleted(false);
            articleList.add(article);
        });
        articleRepository.saveAll(articleList);
        log.debug("Created Information for Articles: {}", articles);
        return articleList;
    }

    @Override
    public Optional<Article> updateArticle(ArticleDTO articleDTO) {
        return Optional
                .of(articleRepository.findById(articleDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(article -> {
                    article.setHeader(articleDTO.getHeader());
                    article.setText(articleDTO.getText());
                    article.setShortDesc(articleDTO.getShortDesc());
                    article.setKeyword(articleDTO.getKeyword());
                    article.setCreatedBy(articleDTO.getAuthor());
                    article.setLastModifiedDate(Instant.now().toEpochMilli());
                    articleRepository.save(article);
                    log.debug("Changed Information for Article: {}", article);
                    return article;
                });
    }

    @Override
    public List<Article> getAllArticlesByMultipleParams(List<String> authorIds, Long from, Long to, String keyword, PageRequest page) {
        return articleDal.getAllArticlesByMultipleParams(authorIds,from,to, keyword,page);
    }

}
