package my.projects.api.controller;

import lombok.RequiredArgsConstructor;
import my.projects.api.controller.helpers.ControllerHelper;
import my.projects.api.dto.AskDto;
import my.projects.api.dto.ProjectDto;
import my.projects.api.exception.BadRequestException;
import my.projects.api.factory.ProjectDtoFactory;
import my.projects.entity.ProjectEntity;
import my.projects.repository.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Transactional
public class ProjectController {

    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
//    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";
//    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";

    private final ProjectRepository projectRepository;

    private final ProjectDtoFactory projectDtoFactory;

    private final ControllerHelper controllerHelper;

    @PutMapping(CREATE_PROJECT)
    public ProjectDto createOrUpdateProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName
            //another params
    ) {

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        boolean isCreate = optionalProjectId.isEmpty();

        if (isCreate && optionalProjectName.isEmpty()) {
            throw new BadRequestException("Project name can't be empty");
        }

        final ProjectEntity project = optionalProjectId
                .map(controllerHelper::getProjectOrThrewException)
                .orElseGet(() -> ProjectEntity.builder().build());



        optionalProjectName
                .ifPresent(projectName -> {
                    projectRepository
                            .findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(String.format("Project \"%s\" already exists.", projectName));
                            });
                    project.setName(projectName);
                });
        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @GetMapping(FETCH_PROJECTS)

    public List<ProjectDto> fetchProjects(@RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());


    }

//    @PostMapping(CREATE_PROJECT)
//    public ProjectDto createProject(@RequestParam(value = "project_name") String projectName) {
//
//        if (projectName.trim().isEmpty()) {
//            throw new BadRequestException("Name can't be empty");
//        }
//
//        projectRepository
//                .findByName(projectName)
//                .ifPresent(project -> {
//                    throw new BadRequestException(String.format("Project \"%s\" already exists.", projectName));
//                });
//
//        ProjectEntity project = projectRepository.saveAndFlush(
//                ProjectEntity.builder()
//                        .name(projectName)
//                        .build()
//        );
//
//        return projectDtoFactory.makeProjectDto(project);
//    }

//    @PatchMapping(EDIT_PROJECT)
//    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
//                                  @RequestParam(value = "project_name") String projectName) {
//
//        if (projectName.trim().isEmpty()) {
//            throw new BadRequestException("Name can't be empty");
//        }
//
//        ProjectEntity project = getProjectOrThrewException(projectId);
//
//        projectRepository
//                .findByName(projectName)
//                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
//                .ifPresent(anotherProject -> {
//                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
//                });
//
//        project.setName(projectName);
//
//        project = projectRepository.saveAndFlush(project);
//
//        return projectDtoFactory.makeProjectDto(project);
//    }

    @DeleteMapping(DELETE_PROJECT)
    public AskDto deleteProject(@PathVariable("project_id") Long projectId) {
        controllerHelper.getProjectOrThrewException(projectId);
        projectRepository.deleteById(projectId);

        return AskDto.makeDefault(true);
    }


}
