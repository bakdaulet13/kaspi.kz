package kaspi.kz.dto;

import jakarta.validation.constraints.NotBlank;

public record RepairOrderCreateDto (
        @NotBlank(message = "clientName doesn't be null") String clientName,
        @NotBlank(message = "description doesn't be null") String description,
        @NotBlank(message = "createdBy doesn't be null") String createdBy
){
}
