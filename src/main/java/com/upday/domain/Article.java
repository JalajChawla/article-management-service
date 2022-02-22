package com.upday.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author jalajchawla
 */
@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class Article extends AbstractAuditingEntity implements Serializable {
    @Id
    private String id;
    @NotNull
    @Indexed
    @Size(min = 1, max = 50)
    private String header;
    @Size(max = 2000)
    @Indexed
    private String text;
    @Size(max = 100)
    private String shortDesc;
    @Indexed
    @Size(max = 150)
    private List<String> keywords;
}
