package ace.charitan.notification.internal.service;

import ace.charitan.notification.internal.dto.InternalNotificationDto;
import ace.charitan.notification.internal.dto.SaveNotificationRequestDto;

import java.util.concurrent.ExecutionException;

public interface InternalNotificationService {
    void testNotification();

    InternalNotificationDto createNotification(SaveNotificationRequestDto dto);

    void test() throws ExecutionException, InterruptedException;
}
