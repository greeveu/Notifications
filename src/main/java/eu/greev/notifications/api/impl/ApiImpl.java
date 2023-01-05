package eu.greev.notifications.api.impl;

import eu.greev.notifications.Main;
import eu.greev.notifications.api.Api;
import eu.greev.notifications.dao.NotificationDao;
import eu.greev.notifications.entity.Notification;
import eu.greev.notifications.service.NotificationService;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Set;
import java.util.UUID;

public class ApiImpl implements Api {

    private final Main main;
    private final NotificationService notificationService;
    private final NotificationDao notificationDao;

    public ApiImpl(Main main, NotificationService notificationService, NotificationDao notificationDao) {
        this.main = main;
        this.notificationService = notificationService;
        this.notificationDao = notificationDao;
    }

    @Override
    public void sendNotification(UUID user, Notification notification) {
        if (notification.getReceiverType() != Notification.ReceiverType.UUID) {
            throw new IllegalArgumentException("Notification has a receiver type other than UUID for sending. Use scheduleNotification instead.");
        }

        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(user);
        if (player == null || !player.isConnected()) {
            scheduleNotification(notification);
            return;
        }
        notificationService.sendNotification(player, notification);
    }

    @Override
    public void scheduleNotification(Notification notification) {
        notificationService.scheduleNotification(notification);
    }

    @Override
    public Set<Notification> getNotificationsForUser(UUID uuid) {
        return notificationDao.findNotificationsByUser(uuid);
    }
}
