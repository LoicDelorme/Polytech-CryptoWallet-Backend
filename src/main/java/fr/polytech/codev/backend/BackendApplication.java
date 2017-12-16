package fr.polytech.codev.backend;

import fr.polytech.codev.backend.services.controllers.implementations.*;
import fr.polytech.codev.backend.services.dao.sql.implementations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public AlertSqlDaoServices alertSqlDaoServices() {
        return new AlertSqlDaoServices();
    }

    @Bean
    public AlertTypeSqlDaoServices alertTypeSqlDaoServices() {
        return new AlertTypeSqlDaoServices();
    }

    @Bean
    public AssetSqlDaoServices assetSqlDaoServices() {
        return new AssetSqlDaoServices();
    }

    @Bean
    public CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices() {
        return new CryptocurrencySqlDaoServices();
    }

    @Bean
    public FavoriteSqlDaoServices favoriteSqlDaoServices() {
        return new FavoriteSqlDaoServices();
    }

    @Bean
    public LogSqlDaoServices logSqlDaoServices() {
        return new LogSqlDaoServices();
    }

    @Bean
    public SettingSqlDaoServices settingSqlDaoServices() {
        return new SettingSqlDaoServices();
    }

    @Bean
    public TokenSqlDaoServices tokenSqlDaoServices() {
        return new TokenSqlDaoServices();
    }

    @Bean
    public UserSqlDaoServices userSqlDaoServices() {
        return new UserSqlDaoServices();
    }

    @Bean
    public WalletSqlDaoServices walletSqlDaoServices() {
        return new WalletSqlDaoServices();
    }

    @Bean
    public AlertControllerServices alertControllerServices() {
        return new AlertControllerServices();
    }

    @Bean
    public AlertTypeControllerServices alertTypeControllerServices() {
        return new AlertTypeControllerServices();
    }

    @Bean
    public AssetControllerServices assetControllerServices() {
        return new AssetControllerServices();
    }

    @Bean
    public CryptocurrencyControllerServices cryptocurrencyControllerServices() {
        return new CryptocurrencyControllerServices();
    }

    @Bean
    public FavoriteControllerServices favoriteControllerServices() {
        return new FavoriteControllerServices();
    }

    @Bean
    public LogControllerServices logControllerServices() {
        return new LogControllerServices();
    }

    @Bean
    public SettingControllerServices settingControllerServices() {
        return new SettingControllerServices();
    }

    @Bean
    public TokenControllerServices tokenControllerServices() {
        return new TokenControllerServices();
    }

    @Bean
    public UserControllerServices userControllerServices() {
        return new UserControllerServices();
    }

    @Bean
    public WalletControllerServices walletControllerServices() {
        return new WalletControllerServices();
    }
}