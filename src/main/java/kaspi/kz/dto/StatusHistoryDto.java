package kaspi.kz.dto;

import kaspi.kz.model.enums.RepairOrderStatus;

import java.util.Date;

public record StatusHistoryDto(
        RepairOrderStatus status,
        String reason,
        String changedBy,
        Date createdAt
) {}
