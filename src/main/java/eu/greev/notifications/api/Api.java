package eu.greev.notifications.api;

import eu.greev.notifications.entity.Notification;

import java.util.Set;
import java.util.UUID;

public interface Api {

    void sendNotification(UUID user, Notification notification);

    void scheduleNotification(Notification notification);

    Set<Notification> getNotificationsForUser(UUID uuid);

}
