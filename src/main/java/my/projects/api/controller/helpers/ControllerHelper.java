package my.projects.api.controller.helpers;

import lombok.RequiredArgsConstructor;
import my.projects.api.exception.NotFoundException;
import my.projects.entity.ProjectEntity;
import my.projects.repository.ProjectRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ControllerHelper {

    private final ProjectRepository projectRepository;


    public ProjectEntity getProjectOrThrewException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException("Project with " + projectId + " doesn't exist")
                );
    }

}
