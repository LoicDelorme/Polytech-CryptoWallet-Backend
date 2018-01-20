package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Device;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.*;
import fr.polytech.codev.backend.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/user")
public class UnregisteredUserController extends AbstractController {

    private static final String passwordSalt = "G2*K0nMKETcvZLyG%$bUjD!Pi1$$SlN!GzaAvlXuOZRJN8agFZ6@)S%k(UJWsB8-";

    @Autowired
    private SettingServices settingServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private DeviceServices deviceServices;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private LogServices logServices;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity subscribe(@RequestBody String data) throws InvalidEntityException {
        final SettingForm settingForm = new SettingForm();
        settingForm.setThemeId(DEFAULT_THEME_ID_VALUE);
        settingForm.setCurrencyId(DEFAULT_CURRENCY_ID_VALUE);
        settingForm.setChartPeriodId(DEFAULT_CHART_PERIOD_ID_VALUE);

        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setPassword(encryptPassword(userForm.getPassword()));
        userForm.setAdministrator(DEFAULT_IS_ADMINISTRATOR_VALUE);
        userForm.setEnabled(DEFAULT_IS_ENABLED_VALUE);
        userForm.setSettingId(this.settingServices.insert(settingForm).getId());

        return serializeSuccessResponse(this.userServices.insert(userForm));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity authenticate(HttpServletRequest request, @RequestBody String data) throws InvalidEntityException, UnknownEntityException {
        final AuthenticationForm authenticationForm = deserialize(data, AuthenticationForm.class);
        authenticationForm.setPassword(encryptPassword(authenticationForm.getPassword()));

        final User user = this.userServices.getByCredentials(authenticationForm);
        final List<Device> devices = user.getDevices().stream().filter(device -> device.getUuid().equals(authenticationForm.getDeviceUuid())).collect(Collectors.toList());
        if (devices.isEmpty()) {
            final DeviceForm deviceForm = new DeviceForm();
            deviceForm.setPlatform(authenticationForm.getDevicePlatform());
            deviceForm.setUuid(authenticationForm.getDeviceUuid());
            deviceForm.setUserId(user.getId());

            this.deviceServices.insert(deviceForm);
        }

        final LogForm logForm = new LogForm();
        logForm.setIpAddress(request.getRemoteAddr());
        logForm.setPlatform(authenticationForm.getDevicePlatform());
        logForm.setUserId(user.getId());

        this.logServices.insert(logForm);

        final TokenForm tokenForm = new TokenForm();
        tokenForm.setEndDate(LocalDateTime.now().plusDays(DEFAULT_TOKEN_END_DATE_PLUS_DAY_VALUE));
        tokenForm.setPlatform(authenticationForm.getDevicePlatform());
        tokenForm.setUserId(user.getId());

        return serializeSuccessResponse(this.tokenServices.insert(tokenForm));
    }

    private String encryptPassword(String plainTextPassword) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update((plainTextPassword + passwordSalt).getBytes());

            final byte[] bytes = messageDigest.digest();
            final StringBuilder encryptedPassword = new StringBuilder();
            for (int offset = 0; offset < bytes.length; offset++) {
                encryptedPassword.append(Integer.toString((bytes[offset] & 0xff) + 0x100, 16).substring(1));
            }

            return encryptedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}