package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorAlertController;
import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.AlertServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.util.NestedServletException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nicolas on 04/02/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@EnableWebMvc
public class AdministratorAlertControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/alert";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private AlertServices alertServices;
    @Spy
    @InjectMocks
    private AdministratorAlertController administratorAlertController;
    private MockMvc mockMvc;
    private Alert firstAlert;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorAlertController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorAlertController).assertUserIsAdministrator("1");
        firstAlert = new Alert();
        firstAlert.setId(1);
        firstAlert.setActive(true);
        firstAlert.setCreationDate(LocalDateTime.now());
        firstAlert.setLastUpdate(LocalDateTime.now());
        firstAlert.setName("test");
        firstAlert.setOneShot(false);
        firstAlert.setThreshold(BigDecimal.ZERO);
        firstAlert.setType(new AlertType());
        firstAlert.setCryptocurrency(new Cryptocurrency());
    }

    @Test
    public void testGetAllAlertsWithValidToken() throws Exception {
        final List<Alert> expectedAlerts = new ArrayList<>();
        when(this.alertServices.all()).thenReturn(expectedAlerts);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedAlerts) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllAlertsWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneAlertWithValidToken() throws Exception {
        final Alert expectedAlert = new Alert();
        when(this.alertServices.get(1)).thenReturn(expectedAlert);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedAlert) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneAlertWithInvalidToken() throws Exception {
        final Alert expectedAlert = new Alert();
        when(this.alertServices.get(1)).thenReturn(expectedAlert);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidAlertWithValidToken() throws Exception {
        when(this.alertServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneAlert() throws Exception {
        AlertForm newAlert = new AlertForm();
        newAlert.setName("new alert");
        newAlert.setActive(true);
        newAlert.setOneShot(false);
        newAlert.setThreshold(BigDecimal.ZERO);
        newAlert.setUserId(1);
        newAlert.setCryptocurrencyId(1);
        newAlert.setTypeId(1);
        when(this.alertServices.insert(newAlert)).thenReturn(new Alert());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlert))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneAlertWithCorrectId() throws Exception {
        AlertForm newAlert = new AlertForm();
        newAlert.setName("new alert");
        newAlert.setActive(true);
        newAlert.setOneShot(false);
        newAlert.setThreshold(BigDecimal.ZERO);
        newAlert.setUserId(1);
        newAlert.setCryptocurrencyId(1);
        newAlert.setTypeId(1);
        when(alertServices.get(1)).thenReturn(firstAlert);
        doReturn(new Alert()).when(this.alertServices).update(1, newAlert);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlert))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneAlertWithUnknownId() throws Exception {
        AlertForm newAlert = new AlertForm();
        newAlert.setName("new alert");
        newAlert.setActive(true);
        newAlert.setOneShot(false);
        newAlert.setThreshold(BigDecimal.ZERO);
        newAlert.setUserId(1);
        newAlert.setCryptocurrencyId(1);
        newAlert.setTypeId(1);
        when(this.alertServices.update(2, newAlert)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlert)));
    }

    @Test
    public void testDeleteValidAlert() throws Exception {
        when(this.alertServices.get(1)).thenReturn(this.firstAlert);
        doNothing().when(this.alertServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownAlert() throws Exception {
        doThrow(new UnknownEntityException()).when(this.alertServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
