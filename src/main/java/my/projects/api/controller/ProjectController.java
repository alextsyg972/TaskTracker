package my.projects.api.controller;

import lombok.RequiredArgsConstructor;
import my.projects.api.dto.ProjectDto;
import my.projects.api.exception.BadRequestException;
import my.projects.api.factory.ProjectDtoFactory;
import my.projects.entity.ProjectEntity;
import my.projects.repository.ProjectRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    private final ProjectRepository projectRepository;

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
            ProjectEntity.builder()
                    .name(name)
                    .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

}
