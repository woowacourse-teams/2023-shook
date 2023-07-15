package shook.shook.part.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class PartRegisterRequest {

    @NotNull
    @Range(min = 0)
    private Integer startSecond;

    @NotNull
    @Range(min = 0)
    private Integer length;

    @NotNull
    private Long songId;
}
