package com.upday.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jalajchawla
 * A DTO representing a article.
 */
@Data
public class ArticleDTO {
    @Size(max = 150)
    private String id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String header;
    @Size(max = 2000)
    private String text;
    @Size(max = 100)
    private String shortDesc;
    @Size(max = 150)
    private String author;
    @Size(max = 150)
    private List<String> keywords;
}
