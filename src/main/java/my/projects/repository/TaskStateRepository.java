package my.projects.repository;

import my.projects.entity.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {

    Optional<TaskStateEntity> findTaskStateEntityByProjectIdAndNameContainsIgnoreCase(Long projectId, String taskStateName);

}
