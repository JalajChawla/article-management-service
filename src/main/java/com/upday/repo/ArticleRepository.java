package com.upday.repo;

import com.upday.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author jalajchawla
 */
public interface ArticleRepository extends MongoRepository<Article,String> {
}
