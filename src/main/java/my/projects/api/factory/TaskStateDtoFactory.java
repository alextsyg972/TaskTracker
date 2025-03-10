package my.projects.api.factory;

import my.projects.api.dto.TaskStateDto;
import my.projects.entity.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {

        return TaskStateDto.builder()
                .id(entity.getId())
                .ordinal(entity.getOrdinal())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();

    }

}
