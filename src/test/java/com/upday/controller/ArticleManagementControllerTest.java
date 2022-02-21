package com.upday.controller;

import com.upday.domain.Article;
import com.upday.service.ArticleManagerService;
import com.upday.service.dto.ArticleDTO;
import com.upday.service.dto.SortOrder;
import com.upday.service.dto.UpdayResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class ArticleManagementControllerTest {
    @Mock
    ArticleManagerService articleManagerService;
    @Mock
    Logger log;
    @InjectMocks
    ArticleManagementController articleManagementController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateArticle() {
        when(articleManagerService.findArticleByAuthorAndHeader(anyString(), anyString())).thenReturn(null);
        when(articleManagerService.createArticle(any())).thenReturn(Arrays.<Article>asList(new Article()));

        UpdayResponse result = articleManagementController.createArticle(Arrays.<ArticleDTO>asList(new ArticleDTO()));
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateArticle() {
        when(articleManagerService.findArticleByAuthorAndHeader(anyString(), anyString())).thenReturn(null);
        when(articleManagerService.updateArticle(any())).thenReturn(null);

        UpdayResponse result = articleManagementController.updateArticle(new ArticleDTO());
        Assert.assertEquals(new UpdayResponse(), result);
    }

    @Test
    public void testDeleteArticleByIds() {
        when(articleManagerService.deleteArticleById(any())).thenReturn("deleteArticleByIdResponse");

        UpdayResponse result = articleManagementController.deleteArticleByIds(Arrays.<String>asList("String"));
        Assert.assertNotNull(result);
    }

    @Test
    public void testFetchArticleById() {
        when(articleManagerService.fetchArticleById(any())).thenReturn(Arrays.<Article>asList(new Article()));

        UpdayResponse result = articleManagementController.fetchArticleById(Arrays.<String>asList("String"));
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetAllArticlesByMultipleParams() {
        when(articleManagerService.getAllArticlesByMultipleParams(any(), anyLong(), anyLong(), anyString(), any())).thenReturn(Arrays.<Article>asList(new Article()));

        UpdayResponse result = articleManagementController.getAllArticlesByMultipleParams(0, 0, SortOrder.ASC, "sortBy", true, Arrays.<String>asList("String"), 1L, 1L, "keyword");
        Assert.assertNotNull(result);
    }
}