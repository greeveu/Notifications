package eu.greev.notifications.service.impl;

import eu.greev.notifications.Main;
import eu.greev.notifications.dao.NotificationDao;
import eu.greev.notifications.entity.DeliveredNotifications;
import eu.greev.notifications.entity.Notification;
import eu.greev.notifications.service.NotificationService;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class NotificationServiceImpl implements NotificationService {

    private final Main main;
    private final NotificationDao database;

    public NotificationServiceImpl(Main main, NotificationDao database) {
        this.main = main;
        this.database = database;
    }

    @Override
    public void sendNotification(ProxiedPlayer player, Notification notification) {
        if (notification.getTtl().isBefore(Instant.now())) {
            notification.getDeliveredNotifications().clear();
            main.getNotifications().remove(notification);
            return;
        }

        player.sendMessage(new TextComponent(notification.getMessage()));
        DeliveredNotifications deliveredNotifications = new DeliveredNotifications(player.getUniqueId(), notification.getId(), Instant.now());
        notification.getDeliveredNotifications().add(deliveredNotifications);

        database.saveDeliveredNotification(deliveredNotifications);
    }

    @Override
    public void scheduleNotification(Notification notification) {
        database.saveNotification(notification);
    }

    @Override
    public Set<Notification> findNotification(ProxiedPlayer player) {
        Set<Notification> notifications = new HashSet<>();
        for (Notification notification : main.getNotifications()) {
            switch (notification.getReceiverType()) {
                case ALL:
                    notifications.add(notification);
                    break;
                case PERMISSION:
                    if (player.hasPermission(notification.getReceiverIdentifier())) {
                        notifications.add(notification);
                    }
                    break;
                case UUID:
                    if (notification.getReceiverIdentifier().equals(player.getUniqueId().toString())) {
                        notifications.add(notification);
                    }
                    break;
            }
        }

        return notifications;
    }
}
