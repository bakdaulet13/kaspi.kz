package kaspi.kz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kaspi.kz.model.enums.RepairOrderStatus;

public record RepairOrderUpdateDto (
        @NotNull(message = "id doesn't be null") Long id,
        @NotNull(message = "status doesn't be null") RepairOrderStatus status,
        @NotBlank(message = "updatedBy doesn't be null") String updatedBy,
        @NotBlank(message = "reason doesn't be null") String reason
){
}
