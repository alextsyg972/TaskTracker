package my.projects.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {


    private Long id;

    private String name;

    @JsonProperty("created_at")
    private Instant createdAt;

}
