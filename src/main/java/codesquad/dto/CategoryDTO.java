package codesquad.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NotBlank
    private String title;

    @Nullable
    private Long parentId;

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "title='" + title + '\'' +
                ", parentId=" + parentId +
                '}';
    }

    public boolean hasParentId() {
        return parentId != null;
    }
}
