package my.projects.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDto {

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDto errorDto)) return false;
        return Objects.equals(error, errorDto.error) && Objects.equals(errorDescription, errorDto.errorDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, errorDescription);
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "error='" + error + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
