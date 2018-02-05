package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorCurrencyController;
import fr.polytech.codev.backend.entities.Currency;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.CurrencyForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.CurrencyServices;
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
public class AdministratorCurrencyControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/currency";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private CurrencyServices currencyServices;
    @Spy
    @InjectMocks
    private AdministratorCurrencyController administratorCurrencyController;
    private MockMvc mockMvc;
    private Currency firstCurrency;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorCurrencyController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorCurrencyController).assertUserIsAdministrator("1");
        firstCurrency = new Currency();
        firstCurrency.setId(1);
        firstCurrency.setCreationDate(LocalDateTime.now());
        firstCurrency.setLastUpdate(LocalDateTime.now());
        firstCurrency.setName("test");
        firstCurrency.setSymbol("symbol");
    }

    @Test
    public void testGetAllCurrenciesWithValidToken() throws Exception {
        final List<Currency> expectedCurrencies = new ArrayList<>();
        when(this.currencyServices.all()).thenReturn(expectedCurrencies);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCurrencies) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllCurrenciesWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneCurrencyWithValidToken() throws Exception {
        final Currency expectedCurrency = new Currency();
        when(this.currencyServices.get(1)).thenReturn(expectedCurrency);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCurrency) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneCurrencyWithInvalidToken() throws Exception {
        final Currency expectedCurrency = new Currency();
        when(this.currencyServices.get(1)).thenReturn(expectedCurrency);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidCurrencyWithValidToken() throws Exception {
        when(this.currencyServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneCurrency() throws Exception {
        CurrencyForm newCurrency = new CurrencyForm();
        newCurrency.setName("new currency");
        newCurrency.setSymbol("new symbol");
        when(this.currencyServices.insert(newCurrency)).thenReturn(new Currency());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCurrency))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneCurrencyWithCorrectId() throws Exception {
        CurrencyForm newCurrency = new CurrencyForm();
        newCurrency.setName("new currency");
        newCurrency.setSymbol("new symbol");
        when(currencyServices.get(1)).thenReturn(firstCurrency);
        doReturn(new Currency()).when(this.currencyServices).update(1, newCurrency);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCurrency))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneCurrencyWithUnknownId() throws Exception {
        CurrencyForm newCurrency = new CurrencyForm();
        newCurrency.setName("new currency");
        newCurrency.setSymbol("new symbol");
        when(this.currencyServices.update(2, newCurrency)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newCurrency)));
    }

    @Test
    public void testDeleteValidCurrency() throws Exception {
        when(this.currencyServices.get(1)).thenReturn(this.firstCurrency);
        doNothing().when(this.currencyServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownCurrency() throws Exception {
        doThrow(new UnknownEntityException()).when(this.currencyServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
