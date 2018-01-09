package fr.polytech.codev.backend;

import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.repositories.DaoRepository;
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
    public DaoRepository<Alert> alertDaoServices() {
        return new AlertSqlDaoRepository();
    }

    @Bean
    public DaoRepository<AlertType> alertTypeDaoServices() {
        return new AlertTypeSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Asset> assetDaoServices() {
        return new AssetSqlDaoRepository();
    }

    @Bean
    public DaoRepository<ChartPeriod> chartPeriodDaoRepository() {
        return new ChartPeriodSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Cryptocurrency> cryptocurrencyDaoServices() {
        return new CryptocurrencySqlDaoRepository();
    }

    @Bean
    public DaoRepository<Currency> currencyDaoServices() {
        return new CurrencySqlDaoRepository();
    }

    @Bean
    public DaoRepository<Device> deviceDaoServices() {
        return new DeviceSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Favorite> favoriteDaoServices() {
        return new FavoriteSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Log> logDaoServices() {
        return new LogSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Setting> settingDaoServices() {
        return new SettingSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Theme> themeDaoServices() {
        return new ThemeSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Token> tokenDaoServices() {
        return new TokenSqlDaoRepository();
    }

    @Bean
    public DaoRepository<User> userDaoServices() {
        return new UserSqlDaoRepository();
    }

    @Bean
    public DaoRepository<Wallet> walletDaoServices() {
        return new WalletSqlDaoRepository();
    }

    @Bean
    public AlertServices alertServices() {
        return new AlertServices();
    }

    @Bean
    public AlertTypeServices alertTypeServices() {
        return new AlertTypeServices();
    }

    @Bean
    public AssetServices assetServices() {
        return new AssetServices();
    }

    @Bean
    public ChartPeriodServices chartPeriodServices() {
        return new ChartPeriodServices();
    }

    @Bean
    public CryptocurrencyServices cryptocurrencyServices() {
        return new CryptocurrencyServices();
    }

    @Bean
    public CurrencyServices currencyServices() {
        return new CurrencyServices();
    }

    @Bean
    public DeviceServices deviceServices() {
        return new DeviceServices();
    }

    @Bean
    public FavoriteServices favoriteServices() {
        return new FavoriteServices();
    }

    @Bean
    public LogServices logServices() {
        return new LogServices();
    }

    @Bean
    public SettingServices settingServices() {
        return new SettingServices();
    }

    @Bean
    public ThemeServices themeServices() {
        return new ThemeServices();
    }

    @Bean
    public TokenServices tokenServices() {
        return new TokenServices();
    }

    @Bean
    public UserServices userServices() {
        return new UserServices();
    }

    @Bean
    public WalletServices walletServices() {
        return new WalletServices();
    }
}