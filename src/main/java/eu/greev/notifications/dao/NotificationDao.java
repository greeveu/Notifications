package eu.greev.notifications.dao;

import eu.greev.notifications.entity.DeliveredNotifications;
import eu.greev.notifications.entity.Notification;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Set;
import java.util.UUID;

@RegisterArgumentFactory(UUIDArgumentFactory.class)
public interface NotificationDao {

    @SqlUpdate("INSERT IGNORE INTO mc_data.notifications_send(user, notification_id) VALUES (:user, :notificationId)")
    boolean saveDeliveredNotification(@BindMethods DeliveredNotifications deliveredNotifications);

    @SqlUpdate("INSERT IGNORE INTO mc_data.notifications_messages(id, receiver_type, receiver_identifier, type, message, ttl) VALUES (:id, :receiverType, :receiverIdentifier, :type, :message, :ttl)")
    boolean saveNotification(@BindFields Notification notification);

    @SqlUpdate("DELETE FROM mc_data.notifications_messages WHERE id = ?")
    boolean deleteNotification(UUID id);

    /**
     * This is only able to find notifications which are directed to the player directly via UUID
     */
    @SqlQuery("SELECT * FROM mc_data.notifications_messages WHERE receiver_type = 'UUID' AND receiver_identifier = ? AND ttl > now()")
    @RegisterBeanMapper(Notification.class)
    Set<Notification> findNotificationsByUser(UUID uuid);

    /**
     * Loads all notifications which have to be sent, only if the ttl is not run out yet
     */
    @SqlQuery("SELECT * FROM mc_data.notifications_messages WHERE ttl > now()")
    @RegisterBeanMapper(Notification.class)
    Set<Notification> loadAllNotifications();

}
