package com.upday.dal;

import com.upday.domain.Article;
import com.upday.pagination.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author jalajchawla
 */
@Repository
@Slf4j
public class ArticleDaLImpl implements ArticleDAL{

    private final MongoTemplate mongoTemplate;

    public ArticleDaLImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Article> getAllArticlesByMultipleParams(List<String> authorIds, Long from, Long to, String keyword, PageRequest page) {
        Query query = PageRequest.getPaginationQueryForMongo(page);

        if (!(CollectionUtils.isEmpty(authorIds))){
            query.addCriteria(Criteria.where("id").in(authorIds));
        }

        if (from != null && to !=null) {
            query.addCriteria(new Criteria().andOperator(Criteria.where("created_date").gte(from),
                    Criteria.where("created_date").lte(to)));
        }

        if (Objects.nonNull(keyword)) {
            query.addCriteria(Criteria.where("keyword").regex( keyword, "i"));
        }
        log.info("Final query is : {}", query);
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> fetchArticleById(List<String> articleIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(articleIds));
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public Optional<Article> findArticleByAuthorAndHeader(String authorId, String header) {
        Query query = new Query();
        Article article = null;
        query.addCriteria(new Criteria().andOperator(Criteria.where("created_by").is(authorId),
                Criteria.where("header").is(header)));
        List<Article> articleList = mongoTemplate.find(query, Article.class);
        if(!(articleList.isEmpty())){
            return Optional.ofNullable(articleList.get(0));
        }
        return Optional.empty();
    }
}
