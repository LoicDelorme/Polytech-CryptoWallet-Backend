package fr.polytech.codev.backend.tasks;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.services.impl.AlertServices;
import fr.polytech.codev.backend.services.impl.CoinMarketCapServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Component
public class AlertTasks {

    public static final String DEFAULT_NOTIFICATION_EMAIL = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" align=\"center\" style=\"border-radius:4px;border:1px solid rgb=(221,221,221);background:rgb(255,255,255)\" bgcolor=\"#ffffff\"> <tbody> <tr> <td style=\"color:rgb(102,102,102);font-size:16px;font=-family:'Open Sans','Helvetica Neue',Arial,sans-serif;line-height:1.5\"> <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"500\" align=\"center\" style=\"margin:42px\"> <tbody> <tr> <td width=\"500\" cellpadding=\"0\" align=\"center\" style=\"font-family:helvetica;font-size:24px;font-weight:300;color:rgb(85,85,85);line-height:1.5\"> <p style=\"color:rgb(102,102,102);font=-size:16px;font-family:'Open Sans','Helvetica Neue',Arial,sans-serif;line-height:1.5;margin:0px\">Hello</p><p style=\"font-family:helvetica;font-=size:34px;font-weight:600;color:rgb(85,85,85);line-height:1.5;margin:0px 0px 42px\">Alert on %s (%s)!</p></td></tr><tr> <td width=\"500\" cellpadding=\"0\" align=\"center\" style=\"font-family:helvetica;font-size:16px;font-weight:300;color:rgb(102,102,102);line-height:1.5\"> <p style=\"color:rgb(102,102,102);font=-size:16px;font-family:'Open Sans','Helvetica Neue',Arial,sans-serif;line-height:1.5;margin:0px 0px 21px\">We send you this alert because the price of %s (%s) matches your defined constraints.</p><p style=\"color:rgb(102,102,102);font=-size:16px;font-family:'Open Sans','Helvetica Neue',Arial,sans-serif;line-height:1.5;margin:0px 0px 21px\"><strong>Current value is %s BTC.</strong></p></td></tr></tbody> </table> </td></tr></tbody></table>";

    private static Logger logger = LoggerFactory.getLogger(AlertTasks.class);

    @Autowired
    private CoinMarketCapServices coinMarketCapServices;

    @Autowired
    private AlertServices alertServices;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedDelay = 60 * 60000)
    public void processAlerts() throws Exception {
        this.alertServices.all().parallelStream().filter(alert -> alert.isActive()).forEach(alert -> processAlert(alert));
    }

    private void processAlert(Alert alert) {
        final BigDecimal currentPrice = this.coinMarketCapServices.getCurrentPrice(alert.getCryptocurrency().getResourceUrl()).getPriceBtc();
        if (equalityMatches(currentPrice, alert.getType().getName(), alert.getThreshold())) {
            try {
                sendNotification(alert, currentPrice);

                if (alert.isOneShot()) {
                    final AlertForm alertForm = new AlertForm();
                    alertForm.setName(alert.getName());
                    alertForm.setThreshold(alert.getThreshold());
                    alertForm.setOneShot(true);
                    alertForm.setActive(false);
                    alertForm.setUserId(alert.getUser().getId());
                    alertForm.setCryptocurrencyId(alert.getCryptocurrency().getId());
                    alertForm.setTypeId(alert.getType().getId());

                    this.alertServices.update(alert.getId(), alertForm);
                }
            } catch (Exception e) {
                logger.error("AlertTasks", e);
            }
        }
    }

    private boolean equalityMatches(BigDecimal priceBtc, String alertType, BigDecimal threshold) {
        switch (alertType) {
            case "=":
                return priceBtc.compareTo(threshold) == 0;
            case ">":
                return priceBtc.compareTo(threshold) > 0;
            case "<":
                return priceBtc.compareTo(threshold) < 0;
            case ">=":
                return priceBtc.compareTo(threshold) >= 0;
            case "<=":
                return priceBtc.compareTo(threshold) <= 0;
            case "!=":
                return priceBtc.compareTo(threshold) != 0;
            default:
                return false;
        }
    }

    private void sendNotification(Alert alert, BigDecimal currentPrice) throws MessagingException {
        final String cryptocurrencyName = alert.getCryptocurrency().getName();
        final String cryptocurrencySymbol = alert.getCryptocurrency().getSymbol();

        final MimeMessage email = this.mailSender.createMimeMessage();
        final MimeMessageHelper emailHelper = new MimeMessageHelper(email);

        emailHelper.setTo(alert.getUser().getEmail());
        emailHelper.setSubject("CryptoWallet (" + alert.getName() + ")");
        emailHelper.setText(String.format(DEFAULT_NOTIFICATION_EMAIL, cryptocurrencyName, cryptocurrencySymbol, cryptocurrencyName, cryptocurrencySymbol, currentPrice), true);

        this.mailSender.send(email);
    }
}