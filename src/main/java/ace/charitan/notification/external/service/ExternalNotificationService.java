package ace.charitan.notification.external.service;

import ace.charitan.common.dto.project.ExternalProjectDto;

public interface ExternalNotificationService {
    void testNotification();
    void sendProjectNotification(String userId, ExternalProjectDto dto);
}
