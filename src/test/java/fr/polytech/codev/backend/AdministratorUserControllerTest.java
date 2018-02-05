package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorUserController;
import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.UserServices;
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
public class AdministratorUserControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/user";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private UserServices userServices;
    @Spy
    @InjectMocks
    private AdministratorUserController administratorUserController;
    private MockMvc mockMvc;
    private User firstUser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorUserController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorUserController).assertUserIsAdministrator("1");
        firstUser = new User();
        firstUser.setId(1);
        firstUser.setCreationDate(LocalDateTime.now());
        firstUser.setLastUpdate(LocalDateTime.now());
        firstUser.setAdministrator(true);
        firstUser.setEnabled(true);
        firstUser.setSetting(new Setting());
        firstUser.setLastActivity(LocalDateTime.now());
        firstUser.setPassword("password");
        firstUser.setEmail("abc@abc.fr");
        firstUser.setFirstname("John");
        firstUser.setLastname("Doe");
        firstUser.setAlerts(new ArrayList<Alert>());
        firstUser.setWallets(new ArrayList<Wallet>());
        firstUser.setLogs(new ArrayList<>());
        firstUser.setFavorites(new ArrayList<>());
        firstUser.setDevices(new ArrayList<>());
        firstUser.setTokens(new ArrayList<>());
    }

    @Test
    public void testGetAllUsersWithValidToken() throws Exception {
        final List<User> expectedUsers = new ArrayList<>();
        when(this.userServices.all()).thenReturn(expectedUsers);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedUsers) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllUsersWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneUserWithValidToken() throws Exception {
        final User expectedUser = new User();
        when(this.userServices.get(1)).thenReturn(expectedUser);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedUser) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneUserWithInvalidToken() throws Exception {
        final User expectedUser = new User();
        when(this.userServices.get(1)).thenReturn(expectedUser);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidUserWithValidToken() throws Exception {
        when(this.userServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneUser() throws Exception {
        UserForm newUser = new UserForm();
        newUser.setAdministrator(true);
        newUser.setEnabled(true);
        newUser.setPassword("new password");
        newUser.setSettingId(1);
        newUser.setEmail("abc@abc.fr");
        newUser.setFirstname("Toto");
        newUser.setLastname("Toto");
        when(this.userServices.insert(newUser)).thenReturn(new User());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newUser))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneUserWithCorrectId() throws Exception {
        UserForm newUser = new UserForm();
        newUser.setAdministrator(true);
        newUser.setEnabled(true);
        newUser.setPassword("new password");
        newUser.setSettingId(1);
        newUser.setEmail("abc@abc.fr");
        newUser.setFirstname("Toto");
        when(userServices.get(1)).thenReturn(firstUser);
        doReturn(new User()).when(this.userServices).update(1, newUser);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newUser))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneUserWithUnknownId() throws Exception {
        UserForm newUser = new UserForm();
        newUser.setAdministrator(true);
        newUser.setEnabled(true);
        newUser.setPassword("new password");
        newUser.setSettingId(1);
        newUser.setEmail("abc@abc.fr");
        newUser.setFirstname("Toto");
        when(this.userServices.update(2, newUser)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newUser)));
    }

    @Test
    public void testDeleteValidUser() throws Exception {
        when(this.userServices.get(1)).thenReturn(this.firstUser);
        doNothing().when(this.userServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownUser() throws Exception {
        doThrow(new UnknownEntityException()).when(this.userServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
