package eu.greev.notifications.dao;

import eu.greev.notifications.entity.DeliveredNotifications;
import eu.greev.notifications.entity.Notification;

import java.util.Set;
import java.util.UUID;

public interface NotificationDao {

    void saveDeliveredNotification(DeliveredNotifications deliveredNotifications);

    void saveNotification(Notification notification);

    void deleteNotification(UUID id);

    /**
     * This is only able to find notifications which are directed to the player directly via UUID
     */
    Set<Notification> findNotificationsByUser(UUID uuid);

    /**
     * Loads all notifications which have to be sent, only if the ttl is not run out yet
     */
    Set<Notification> loadAllNotifications();

}
