package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorLogController;
import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.LogServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nicolas on 04/02/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@EnableWebMvc
public class AdministratorLogControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/log";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private LogServices logServices;
    @Spy
    @InjectMocks
    private AdministratorLogController administratorLogController;
    private MockMvc mockMvc;
    private Log firstLog;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorLogController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorLogController).assertUserIsAdministrator("1");
        firstLog = new Log();
        firstLog.setId(1);
        firstLog.setCreationDate(LocalDateTime.now());
        firstLog.setLastUpdate(LocalDateTime.now());
        firstLog.setIpAddress("localhost");
        firstLog.setPlatform("platform");
        firstLog.setUser(new User());
    }

    @Test
    public void testGetAllLogsWithValidToken() throws Exception {
        final List<Log> expectedLogs = new ArrayList<>();
        when(this.logServices.all()).thenReturn(expectedLogs);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedLogs) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllLogsWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneLogWithValidToken() throws Exception {
        final Log expectedLog = new Log();
        when(this.logServices.get(1)).thenReturn(expectedLog);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedLog) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneLogWithInvalidToken() throws Exception {
        final Log expectedLog = new Log();
        when(this.logServices.get(1)).thenReturn(expectedLog);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidLogWithValidToken() throws Exception {
        when(this.logServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

}
