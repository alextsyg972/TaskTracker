package my.projects.repository;

import my.projects.entity.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {


}
