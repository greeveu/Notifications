package eu.greev.notifications.service;

import eu.greev.notifications.entity.Notification;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Set;

public interface NotificationService {

    void sendNotification(ProxiedPlayer player, Notification notification);

    void scheduleNotification(Notification notification);

    Set<Notification> findNotification(ProxiedPlayer player);

}
