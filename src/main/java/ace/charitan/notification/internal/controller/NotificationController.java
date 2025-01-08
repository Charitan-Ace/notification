package ace.charitan.notification.internal.controller;

import ace.charitan.notification.internal.auth.AuthUtils;
import ace.charitan.notification.internal.service.InternalNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
}
