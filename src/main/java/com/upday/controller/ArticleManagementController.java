package com.upday.controller;

import com.upday.domain.Article;
import com.upday.service.dto.ArticleDTO;
import com.upday.service.dto.SortOrder;
import com.upday.service.dto.UpdayResponse;
import com.upday.exception.ArticleAlreadyExistException;
import com.upday.exception.BadFormatException;
import com.upday.pagination.PageRequest;
import com.upday.service.ArticleManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author jalajchawla
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleManagementController {
    private final ArticleManagerService articleManagerService;

    public ArticleManagementController(ArticleManagerService service) {
        this.articleManagerService = service;
    }



    /**
     * {@code POST  /article}  : Creates a new Article.
     * <p>
     * Creates a new article if the header is not already used by the author
     *
     * @param dtoList  the List of Article to create.
     * @return the {@link UpdayResponse} with status {@code 200} and with body of the updated user, or with status {@code 400 (Bad Request)} if the Article is already in use.
     * @throws ArticleAlreadyExistException if the Article is already in use.
     * @throws BadFormatException {@code 400 (Bad Request)} if the Article contains an Id.
     */

    @PostMapping("")
    public UpdayResponse createArticle(@Valid @RequestBody List<ArticleDTO> dtoList){
        log.debug("REST request to save Articles : {}", dtoList);
        UpdayResponse response= new UpdayResponse();
        dtoList.forEach(article -> {
            if (article.getId() != null) {
                throw new BadFormatException("A new article cannot already have an ID");
            } else if (articleManagerService.findArticleByAuthorAndHeader(article.getAuthor(), article.getHeader()).isPresent()) {
                throw new ArticleAlreadyExistException("Article already exist");
            }else{
                response.setData(articleManagerService.createArticle(dtoList));
            }
        });
        return response;
    }

    /**
     * {@code PUT /article} : Updates an existing Article.
     *
     * @param articleDTO the Article to update.
     * @return the {@link UpdayResponse} with status {@code 200} and with body of the updated Article.
     * @throws ArticleAlreadyExistException if the Article is already in use.
     */

    @PutMapping("")
    public UpdayResponse updateArticle(@RequestBody ArticleDTO articleDTO){
        log.debug("REST request to update Articles : {}", articleDTO);
        UpdayResponse response= new UpdayResponse();
        Optional<Article> existingArticle= articleManagerService.findArticleByAuthorAndHeader(articleDTO.getAuthor(), articleDTO.getHeader());
        if (existingArticle.isPresent() && (!existingArticle.get().getId().equals(articleDTO.getId()))) {
            throw new ArticleAlreadyExistException();
        }
        response.setData(articleManagerService.updateArticle(articleDTO));
        return response;
    }

    /**
     * {@code DELETE /articles/} : delete the Article.
     *
     * @param ids the ids of the article to delete.
     *
     * @return the {@link UpdayResponse} with status {@code 200} and with body of the updated Article.
     */
    @DeleteMapping("{ids}")
    public UpdayResponse deleteArticleByIds(@PathVariable("ids") List<String> ids){
        log.debug("REST request to delete ArticleIds: {}  ", ids);
        UpdayResponse response= new UpdayResponse();
        articleManagerService.deleteArticleById(ids);
        return response;
    }

    /**
     * {@code GET /admin/users/:login} : get the Article .
     *
     * @param ids the ids of the Article to get.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Article of the user}.
     */
    @GetMapping("{ids}")
    public UpdayResponse fetchArticleById(@PathVariable("ids") List<String> ids){
        log.debug("REST request to get ArticleIds : {}", ids);
        UpdayResponse response= new UpdayResponse();
        response.setData(articleManagerService.fetchArticleById(ids));
        return response;
    }

    /**
     * {@code GET /admin/users} : get all Articles with filter criteria .
     *
     * @param pageNumber,pageSize,sortOrder,sortBy the pagination information.
     * @throws BadFormatException for the Invalid Paginated Params.
     * @return the {@link UpdayResponse} with status {@code 200} and with body of all filtered Articles.
     */

    @GetMapping("")
    public UpdayResponse getAllArticlesByMultipleParams(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false, defaultValue = "DESC") SortOrder sortOrder,
                                                        @RequestParam(required = false, defaultValue = "createdOn") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "false") boolean all,
                                                        @RequestParam(required = false) List<String> authorIds,
                                                        @RequestParam(required = false) Long from,
                                                        @RequestParam(required = false) Long to,
                                                        @RequestParam(required = false) String keyword) {
        if (all) {
            pageNumber = 0;
            pageSize = 2000;
            log.info("Since all=TRUE, pageNumber :{} and pageSize = {}", pageNumber, pageSize);
        } else {
            log.info("Since all=FALSE, relying of values provided by client, "
                    + "pageNumber :{} and pageSize = {}", pageNumber, pageSize);
        }
        PageRequest page = new PageRequest(pageNumber, pageSize, sortOrder, sortBy);
        UpdayResponse response= new UpdayResponse();
        String pageValidationError = PageRequest.validatePaginationParams(page, 3000);
        if (StringUtils.isNotEmpty(pageValidationError)) {
            log.error("Validation error in pagination params - {}", pageValidationError);
            throw new BadFormatException(pageValidationError);
        }

        try {
            response.setData(articleManagerService.getAllArticlesByMultipleParams(authorIds,from,to,keyword,page));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }



}
