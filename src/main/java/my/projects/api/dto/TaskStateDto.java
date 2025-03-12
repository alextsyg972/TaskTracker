package my.projects.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStateDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @JsonProperty(value = "left_task_state_id")
    private Long leftTaskStateId;

    @JsonProperty(value = "right_task_state_id")
    private Long rightTasksStateId;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

    @NonNull
    private List<TaskDto> tasks;


}
