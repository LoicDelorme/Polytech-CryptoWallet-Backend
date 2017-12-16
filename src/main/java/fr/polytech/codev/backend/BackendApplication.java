package fr.polytech.codev.backend;

import fr.polytech.codev.backend.repositories.sql.impl.*;
import fr.polytech.codev.backend.services.impl.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public AlertSqlDaoRepository alertSqlDaoServices() {
        return new AlertSqlDaoRepository();
    }

    @Bean
    public AlertTypeSqlDaoRepository alertTypeSqlDaoServices() {
        return new AlertTypeSqlDaoRepository();
    }

    @Bean
    public AssetSqlDaoRepository assetSqlDaoServices() {
        return new AssetSqlDaoRepository();
    }

    @Bean
    public CryptocurrencySqlDaoRepository cryptocurrencySqlDaoServices() {
        return new CryptocurrencySqlDaoRepository();
    }

    @Bean
    public FavoriteSqlDaoRepository favoriteSqlDaoServices() {
        return new FavoriteSqlDaoRepository();
    }

    @Bean
    public LogSqlDaoRepository logSqlDaoServices() {
        return new LogSqlDaoRepository();
    }

    @Bean
    public SettingSqlDaoRepository settingSqlDaoServices() {
        return new SettingSqlDaoRepository();
    }

    @Bean
    public TokenSqlDaoRepository tokenSqlDaoServices() {
        return new TokenSqlDaoRepository();
    }

    @Bean
    public UserSqlDaoRepository userSqlDaoServices() {
        return new UserSqlDaoRepository();
    }

    @Bean
    public WalletSqlDaoRepository walletSqlDaoServices() {
        return new WalletSqlDaoRepository();
    }

    @Bean
    public AlertServices alertControllerServices() {
        return new AlertServices();
    }

    @Bean
    public AlertTypeServices alertTypeControllerServices() {
        return new AlertTypeServices();
    }

    @Bean
    public AssetServices assetControllerServices() {
        return new AssetServices();
    }

    @Bean
    public CryptocurrencyServices cryptocurrencyControllerServices() {
        return new CryptocurrencyServices();
    }

    @Bean
    public FavoriteServices favoriteControllerServices() {
        return new FavoriteServices();
    }

    @Bean
    public LogServices logControllerServices() {
        return new LogServices();
    }

    @Bean
    public SettingServices settingControllerServices() {
        return new SettingServices();
    }

    @Bean
    public TokenServices tokenControllerServices() {
        return new TokenServices();
    }

    @Bean
    public UserServices userControllerServices() {
        return new UserServices();
    }

    @Bean
    public WalletServices walletControllerServices() {
        return new WalletServices();
    }
}