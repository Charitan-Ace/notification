package ace.charitan.notification.internal.dto;

import ace.charitan.notification.internal.service.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveNotificationRequestDto {

    private String userId;

    @NonNull
    private String message;

    @NonNull
    private NotificationType notificationType;

    private Long projectId;

    private Long donationId;
}
