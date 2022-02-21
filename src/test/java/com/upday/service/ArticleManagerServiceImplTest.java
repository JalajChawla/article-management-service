package com.upday.service;

import com.upday.dal.ArticleDAL;
import com.upday.domain.Article;
import com.upday.pagination.PageRequest;
import com.upday.repo.ArticleRepository;
import com.upday.service.dto.ArticleDTO;
import com.upday.service.dto.SortOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ArticleManagerServiceImplTest {
    @Mock
    ArticleRepository articleRepository;
    @Mock
    Query query;
    @Mock
    ArticleDAL articleDal;
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    Logger log;
    @InjectMocks
    ArticleManagerServiceImpl articleManagerServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFetchArticleById() {
        when(articleDal.fetchArticleById(any())).thenReturn(Arrays.<Article>asList(new Article()));

        List<Article> result = articleManagerServiceImpl.fetchArticleById(Arrays.<String>asList("String"));
        Assert.assertEquals(Arrays.<Article>asList(new Article()), result);
    }

    @Test
    public void testFindArticleByAuthorAndHeader() {
        when(articleDal.findArticleByAuthorAndHeader(anyString(), anyString())).thenReturn(null);

        Optional<Article> result = articleManagerServiceImpl.findArticleByAuthorAndHeader("authorId", "header");
        Assert.assertNull(result);
    }

    @Test
    public void testDeleteArticleById() {
        when(articleDal.fetchArticleById(any())).thenReturn(Arrays.<Article>asList(new Article()));
        String result = articleManagerServiceImpl.deleteArticleById(Arrays.<String>asList("ids"));
        Assert.assertEquals("ArticleIds : [ids] deleted successfully", result);
    }

    @Test
    public void testCreateArticle() {
        List<Article> result = articleManagerServiceImpl.createArticle(Arrays.<ArticleDTO>asList(new ArticleDTO()));
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateArticle()  {
        Optional<Article> result = articleManagerServiceImpl.updateArticle(new ArticleDTO());
        Assert.assertEquals(Optional.empty(), result);
    }

    @Test
    public void testGetAllArticlesByMultipleParams() throws Exception {
        when(articleDal.getAllArticlesByMultipleParams(any(), anyLong(), anyLong(), anyString(), any())).thenReturn(Arrays.<Article>asList(new Article()));

        List<Article> result = articleManagerServiceImpl.getAllArticlesByMultipleParams(Arrays.<String>asList("String"), 1L, 1L, "keyword", new PageRequest(0, 0, SortOrder.ASC, "sortBy"));
        Assert.assertEquals(Arrays.<Article>asList(new Article()), result);
    }
}