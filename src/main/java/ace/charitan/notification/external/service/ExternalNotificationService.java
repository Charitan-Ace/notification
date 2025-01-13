package ace.charitan.notification.external.service;

import ace.charitan.common.dto.project.ExternalProjectDto;

public interface ExternalNotificationService {
    void testNotification();
    void sendNewProjectNotification(String userId, ExternalProjectDto dto);
    void sendCancelledProjectNotification(String userId, ExternalProjectDto dto);
}
