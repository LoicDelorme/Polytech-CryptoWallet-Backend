package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorCryptocurrencyController;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.CryptocurrencyForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.CryptocurrencyServices;
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
public class AdministratorCryptocurrencyControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/cryptocurrency";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private CryptocurrencyServices cryptocurrencyServices;
    @Spy
    @InjectMocks
    private AdministratorCryptocurrencyController administratorCryptocurrencyController;
    private MockMvc mockMvc;
    private Cryptocurrency firstCryptocurrency;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorCryptocurrencyController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorCryptocurrencyController).assertUserIsAdministrator("1");
        firstCryptocurrency = new Cryptocurrency();
        firstCryptocurrency.setId(1);
        firstCryptocurrency.setCreationDate(LocalDateTime.now());
        firstCryptocurrency.setLastUpdate(LocalDateTime.now());
        firstCryptocurrency.setName("test");
        firstCryptocurrency.setImageUrl("image url");
        firstCryptocurrency.setResourceUrl("resource url");
        firstCryptocurrency.setSymbol("symbol");
    }

    @Test
    public void testGetAllCryptocurrenciesWithValidToken() throws Exception {
        final List<Cryptocurrency> expectedCryptocurrencies = new ArrayList<>();
        when(this.cryptocurrencyServices.all()).thenReturn(expectedCryptocurrencies);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCryptocurrencies) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllCryptocurrenciesWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneCryptocurrencyWithValidToken() throws Exception {
        final Cryptocurrency expectedCryptocurrency = new Cryptocurrency();
        when(this.cryptocurrencyServices.get(1)).thenReturn(expectedCryptocurrency);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCryptocurrency) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneCryptocurrencyWithInvalidToken() throws Exception {
        final Cryptocurrency expectedCryptocurrency = new Cryptocurrency();
        when(this.cryptocurrencyServices.get(1)).thenReturn(expectedCryptocurrency);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidCryptocurrencyWithValidToken() throws Exception {
        when(this.cryptocurrencyServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneCryptocurrency() throws Exception {
        CryptocurrencyForm newCryptocurrency = new CryptocurrencyForm();
        newCryptocurrency.setName("new cryptocurrency");
        newCryptocurrency.setImageUrl("new image url");
        newCryptocurrency.setResourceUrl("new res url");
        newCryptocurrency.setSymbol("new symbol");
        when(this.cryptocurrencyServices.insert(newCryptocurrency)).thenReturn(new Cryptocurrency());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCryptocurrency))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneCryptocurrencyWithCorrectId() throws Exception {
        CryptocurrencyForm newCryptocurrency = new CryptocurrencyForm();
        newCryptocurrency.setName("new cryptocurrency");
        newCryptocurrency.setImageUrl("new image url");
        newCryptocurrency.setResourceUrl("new res url");
        newCryptocurrency.setSymbol("new symbol");
        when(cryptocurrencyServices.get(1)).thenReturn(firstCryptocurrency);
        doReturn(new Cryptocurrency()).when(this.cryptocurrencyServices).update(1, newCryptocurrency);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCryptocurrency))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneCryptocurrencyWithUnknownId() throws Exception {
        CryptocurrencyForm newCryptocurrency = new CryptocurrencyForm();
        newCryptocurrency.setName("new cryptocurrency");
        newCryptocurrency.setImageUrl("new image url");
        newCryptocurrency.setResourceUrl("new res url");
        newCryptocurrency.setSymbol("new symbol");
        when(this.cryptocurrencyServices.update(2, newCryptocurrency)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCryptocurrency)));
    }

    @Test
    public void testDeleteValidCryptocurrency() throws Exception {
        when(this.cryptocurrencyServices.get(1)).thenReturn(this.firstCryptocurrency);
        doNothing().when(this.cryptocurrencyServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownCryptocurrency() throws Exception {
        doThrow(new UnknownEntityException()).when(this.cryptocurrencyServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
