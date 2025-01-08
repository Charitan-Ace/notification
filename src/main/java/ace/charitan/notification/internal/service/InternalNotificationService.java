package ace.charitan.notification.internal.service;

import ace.charitan.notification.internal.dto.InternalNotificationDto;
import ace.charitan.notification.internal.dto.SaveNotificationRequestDto;

public interface InternalNotificationService {
    void testNotification();

    InternalNotificationDto createNotification(SaveNotificationRequestDto dto);
}
