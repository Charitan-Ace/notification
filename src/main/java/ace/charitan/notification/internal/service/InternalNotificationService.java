package ace.charitan.notification.internal.service;

import ace.charitan.notification.internal.dto.InternalNotificationDto;
import ace.charitan.notification.internal.dto.SaveNotificationRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.concurrent.ExecutionException;

public interface InternalNotificationService {
    void testNotification();

    InternalNotificationDto createNotification(SaveNotificationRequestDto dto);

    void test() throws ExecutionException, InterruptedException;
    PagedModel<InternalNotificationDto> getMyNotifications(Pageable pageable);
}
