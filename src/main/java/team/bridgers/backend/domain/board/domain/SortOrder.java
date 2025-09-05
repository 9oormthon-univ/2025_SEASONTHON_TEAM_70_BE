package team.bridgers.backend.domain.board.domain;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortOrder {
    POPULARITY("popularityCount", Sort.Direction.DESC),
    NEWEST("createdDate", Sort.Direction.DESC),
    OLDEST("createdDate", Sort.Direction.ASC);

    private final String fieldName;
    private final Sort.Direction direction;

    SortOrder(String fieldName, Sort.Direction direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }
}
