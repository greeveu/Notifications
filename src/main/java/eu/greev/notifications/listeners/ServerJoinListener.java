package eu.greev.notifications.listeners;

import eu.greev.notifications.Main;
import eu.greev.notifications.entity.Notification;
import eu.greev.notifications.service.NotificationService;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Set;

public class ServerJoinListener implements Listener {

    private final Main main;
    private final NotificationService notificationService;

    public ServerJoinListener(Main main, NotificationService notificationService) {
        this.main = main;
        this.notificationService = notificationService;
    }

    @EventHandler
    public void onJoin(ServerSwitchEvent event) {
        //If from is not null it means the player just switched servers
        if (event.getFrom() != null) {
            return;
        }

        ProxiedPlayer player = event.getPlayer();

        ProxyServer.getInstance().getScheduler().runAsync(main, () -> {
            Set<Notification> notifications = notificationService.findNotification(player);
            notifications.forEach(notification -> notificationService.sendNotification(player, notification));
        });
    }

}
