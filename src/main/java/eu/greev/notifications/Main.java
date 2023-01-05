package eu.greev.notifications;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.greev.notifications.api.Api;
import eu.greev.notifications.dao.NotificationDao;
import eu.greev.notifications.dao.impl.NotificationDaoImpl;
import eu.greev.notifications.entity.Notification;
import eu.greev.notifications.listeners.ServerJoinListener;
import eu.greev.notifications.service.NotificationService;
import eu.greev.notifications.service.impl.NotificationServiceImpl;
import eu.greev.notifications.utils.ConfigUtils;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class Main extends Plugin {
    @Getter
    public Api api;

    @Getter
    private NotificationDao notificationDao;

    @Getter
    private NotificationService notificationService;

    @Getter
    private Configuration config;

    @Getter
    private final Set<Notification> notifications = new HashSet<>();

    @Override
    public void onEnable() {
        try {
            config = new ConfigUtils(this).getCustomConfig("main.yml");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Unable to load config.");
            throw new RuntimeException(e);
        }

        loadDatabase();
        notificationService = new NotificationServiceImpl(this, notificationDao);
        getProxy().getPluginManager().registerListener(this, new ServerJoinListener(this, notificationService));
    }

    private void loadDatabase() {
        HikariConfig newDbHikariConfig = new HikariConfig();
        newDbHikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", getConfig().getString("mysql.host"), getConfig().getInt("mysql.port"), getConfig().getString("mysql.database")));
        newDbHikariConfig.setUsername(getConfig().getString("mysql.username"));
        newDbHikariConfig.setPassword(getConfig().getString("mysql.password"));
        newDbHikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        newDbHikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        newDbHikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        notificationDao = new NotificationDaoImpl(new HikariDataSource(newDbHikariConfig));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
