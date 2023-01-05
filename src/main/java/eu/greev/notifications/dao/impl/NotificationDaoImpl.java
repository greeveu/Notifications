package eu.greev.notifications.dao.impl;

import com.zaxxer.hikari.HikariDataSource;
import eu.greev.notifications.dao.NotificationDao;
import eu.greev.notifications.entity.DeliveredNotifications;
import eu.greev.notifications.entity.Notification;

import java.util.Set;
import java.util.UUID;

public class NotificationDaoImpl implements NotificationDao {

    private final HikariDataSource dataSource;

    public NotificationDaoImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveDeliveredNotification(DeliveredNotifications deliveredNotifications) {

    }

    @Override
    public void saveNotification(Notification notification) {

    }

    @Override
    public void deleteNotification(UUID id) {

    }

    @Override
    public Set<Notification> findNotificationsByUser(UUID uuid) {
        return null;
    }

    @Override
    public Set<Notification> loadAllNotifications() {
        return null;
    }
}
