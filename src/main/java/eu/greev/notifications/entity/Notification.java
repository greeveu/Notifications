package eu.greev.notifications.entity;

import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Notification {

    @ColumnName("id")
    private UUID id;

    @ColumnName("receiverType")
    private ReceiverType receiverType;

    @ColumnName("receiverIdentifier")
    private String receiverIdentifier;

    @ColumnName("type")
    private String type;

    @ColumnName("message")
    private String message;

    @ColumnName("ttl")
    private Instant ttl;

    private Set<DeliveredNotifications> deliveredNotifications = new HashSet<>();

    public enum ReceiverType {
        ALL, PERMISSION, UUID
    }
}
