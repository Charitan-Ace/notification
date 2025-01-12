package ace.charitan.notification.internal.controller;

import ace.charitan.notification.internal.auth.AuthUtils;
import ace.charitan.notification.internal.dto.InternalNotificationDto;
import ace.charitan.notification.internal.service.InternalNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class NotificationController {

    @Autowired
    private InternalNotificationService service;

    @PostMapping
    public ResponseEntity<String> testStreamMessage(HttpServletRequest request) {
        UserDetails userDetails = AuthUtils.getUserDetails();

        System.out.println(userDetails);

        service.testNotification();
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails.getUsername());
        } else {
            return ResponseEntity.ok("cringe");
        }
    }

<<<<<<< HEAD
    @GetMapping("/test")
    public ResponseEntity<Void> test() throws ExecutionException, InterruptedException {
        service.test();
        return ResponseEntity.noContent().build();
    }

=======
>>>>>>> 606c0bc (Added get my notifications)
    @GetMapping("/my-notifications")
    public ResponseEntity<PagedModel<InternalNotificationDto>> getMyNotifications(Pageable pageable) {
        return ResponseEntity.ok(service.getMyNotifications(pageable));
    }
}
