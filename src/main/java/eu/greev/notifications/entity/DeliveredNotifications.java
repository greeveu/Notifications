package eu.greev.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DeliveredNotifications {

    private UUID user;
    private UUID notificationId;
    private Instant timestamp;

}
