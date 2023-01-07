package eu.greev.notifications;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.greev.notifications.api.Api;
import eu.greev.notifications.api.impl.ApiImpl;
import eu.greev.notifications.dao.NotificationDao;
import eu.greev.notifications.listeners.ServerJoinListener;
import eu.greev.notifications.service.NotificationService;
import eu.greev.notifications.service.impl.NotificationServiceImpl;
import eu.greev.notifications.utils.ConfigUtils;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.IOException;
import java.util.logging.Level;

public final class Main extends Plugin {
    @Getter
    public Api api;

    private NotificationDao notificationDao;
    private NotificationService notificationService;
    private Configuration config;

    @Override
    public void onEnable() {
        try {
            config = new ConfigUtils(this).getCustomConfig("config.yml");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Unable to load config.");
            throw new RuntimeException(e);
        }

        loadDatabase();
        notificationService = new NotificationServiceImpl(this, notificationDao);
        getProxy().getPluginManager().registerListener(this, new ServerJoinListener(this, notificationService));

        api = new ApiImpl(this, notificationService, notificationDao);
    }

    private void loadDatabase() {
        HikariConfig newDbHikariConfig = new HikariConfig();
        newDbHikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", config.getString("mysql.host"), config.getInt("mysql.port"), config.getString("mysql.database")));
        newDbHikariConfig.setUsername(config.getString("mysql.username"));
        newDbHikariConfig.setPassword(config.getString("mysql.password"));
        newDbHikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        newDbHikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        newDbHikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        Jdbi jdbi = Jdbi.create(new HikariDataSource(newDbHikariConfig));
        jdbi.installPlugin(new SqlObjectPlugin());

        notificationDao = jdbi.onDemand(NotificationDao.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
