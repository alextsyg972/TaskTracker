package my.projects.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    @JsonProperty("created_at")
    Instant createdAt;
}
