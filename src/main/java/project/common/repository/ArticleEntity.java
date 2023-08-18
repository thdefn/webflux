package project.common.repository;

import lombok.Data;

/**
 * Entity 는 일종의 DB 개념
 */
@Data
public class ArticleEntity {
    private final String id;
    private final String title;
    private final String content;
    private final String userId;
}
