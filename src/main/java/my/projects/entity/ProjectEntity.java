package my.projects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "project_id")
    private List<TaskStateEntity> taskStates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectEntity project)) return false;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(createdAt, project.createdAt) && Objects.equals(taskStates, project.taskStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, taskStates);
    }
}
