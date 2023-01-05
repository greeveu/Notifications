package eu.greev.notifications.entity;

import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Notification {

    private UUID id;
    private ReceiverType receiverType;
    private String receiverIdentifier;
    private String type;
    private String message;
    private Instant ttl;
    private Set<DeliveredNotifications> deliveredNotifications = new HashSet<>();

    public enum ReceiverType {
        ALL, PERMISSION, UUID
    }
}
