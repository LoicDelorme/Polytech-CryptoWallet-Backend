package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorAlertTypeController;
import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertTypeForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.AlertTypeServices;
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
public class AdministratorAlertTypeControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/alert-type";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private AlertTypeServices alertTypeServices;
    @Spy
    @InjectMocks
    private AdministratorAlertTypeController administratorAlertTypeController;
    private MockMvc mockMvc;
    private AlertType firstAlertType;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorAlertTypeController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorAlertTypeController).assertUserIsAdministrator("1");
        firstAlertType = new AlertType();
        firstAlertType.setCreationDate(LocalDateTime.now());
        firstAlertType.setLastUpdate(LocalDateTime.now());
        firstAlertType.setName("test");
        firstAlertType.setId(1);
    }

    @Test
    public void testGetAllAlertTypesWithValidToken() throws Exception {
        final List<AlertType> expectedAlertTypes = new ArrayList<>();
        when(this.alertTypeServices.all()).thenReturn(expectedAlertTypes);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedAlertTypes) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllAlertTypesWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneAlertTypeWithValidToken() throws Exception {
        final AlertType expectedAlertType = new AlertType();
        when(this.alertTypeServices.get(1)).thenReturn(expectedAlertType);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedAlertType) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneAlertTypeWithInvalidToken() throws Exception {
        final AlertType expectedAlertType = new AlertType();
        when(this.alertTypeServices.get(1)).thenReturn(expectedAlertType);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidAlertTypeWithValidToken() throws Exception {
        when(this.alertTypeServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneAlertType() throws Exception {
        AlertTypeForm newAlertType = new AlertTypeForm();
        newAlertType.setName("new alert type");
        when(this.alertTypeServices.insert(newAlertType)).thenReturn(new AlertType());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlertType))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneAlertTypeWithCorrectId() throws Exception {
        AlertTypeForm newAlertType = new AlertTypeForm();
        newAlertType.setName("new alert type");
        when(alertTypeServices.get(1)).thenReturn(firstAlertType);
        doReturn(new AlertType()).when(this.alertTypeServices).update(1, newAlertType);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlertType))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneAlertTypeWithUnknownId() throws Exception {
        AlertTypeForm newAlertType = new AlertTypeForm();
        newAlertType.setName("new alert type");
        when(this.alertTypeServices.update(2, newAlertType)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newAlertType)));
    }

    @Test
    public void testDeleteValidAlertType() throws Exception {
        when(this.alertTypeServices.get(1)).thenReturn(this.firstAlertType);
        doNothing().when(this.alertTypeServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownAlertType() throws Exception {
        doThrow(new UnknownEntityException()).when(this.alertTypeServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
