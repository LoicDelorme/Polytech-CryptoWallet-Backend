package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorTokenController;
import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.TokenServices;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class AdministratorTokenControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/token";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private TokenServices tokenServices;
    @Spy
    @InjectMocks
    private AdministratorTokenController administratorTokenController;
    private MockMvc mockMvc;
    private Token firstToken;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorTokenController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorTokenController).assertUserIsAdministrator("1");
        firstToken = new Token();
        firstToken.setId(1);
        firstToken.setCreationDate(LocalDateTime.now());
        firstToken.setLastUpdate(LocalDateTime.now());
        firstToken.setEndDate(LocalDateTime.MAX);
        firstToken.setUser(new User());
        firstToken.setValue("1");
        firstToken.setPlatform("platform");
        firstToken.setBeginDate(LocalDateTime.now());
    }

    @Test
    public void testGetAllTokensWithValidToken() throws Exception {
        final List<Token> expectedTokens = new ArrayList<>();
        when(this.tokenServices.all()).thenReturn(expectedTokens);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedTokens) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllTokensWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneTokenWithValidToken() throws Exception {
        final Token expectedToken = new Token();
        when(this.tokenServices.get(1)).thenReturn(expectedToken);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedToken) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneTokenWithInvalidToken() throws Exception {
        final Token expectedToken = new Token();
        when(this.tokenServices.get(1)).thenReturn(expectedToken);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidTokenWithValidToken() throws Exception {
        when(this.tokenServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testDeleteValidToken() throws Exception {
        when(this.tokenServices.get(1)).thenReturn(this.firstToken);
        doNothing().when(this.tokenServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownToken() throws Exception {
        doThrow(new UnknownEntityException()).when(this.tokenServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
