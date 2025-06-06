package kaspi.kz.dto;

import kaspi.kz.model.enums.RepairOrderStatus;

import java.util.List;

public record RepairOrderDto(
        Long id,
        String username,
        String description,
        RepairOrderStatus status,
        List<StatusHistoryDto> historyList
) {}

