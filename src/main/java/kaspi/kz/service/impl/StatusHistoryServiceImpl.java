package kaspi.kz.service.impl;

import kaspi.kz.model.StatusHistory;
import kaspi.kz.repository.StatusHistoryRepository;
import kaspi.kz.service.StatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusHistoryServiceImpl implements StatusHistoryService {
    private final StatusHistoryRepository statusHistoryRepository;

    @Autowired
    public StatusHistoryServiceImpl(StatusHistoryRepository statusHistoryRepository) {
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Transactional
    @Override
    public void save(StatusHistory statusHistory) {
        statusHistoryRepository.save(statusHistory);
    }
}
