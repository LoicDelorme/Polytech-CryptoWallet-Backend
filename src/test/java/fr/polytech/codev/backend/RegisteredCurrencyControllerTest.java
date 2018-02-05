package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.registered.RegisteredCurrencyController;
import fr.polytech.codev.backend.entities.Currency;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.CurrencyServices;
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
public class RegisteredCurrencyControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/registered/%s/currency";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private CurrencyServices currencyServices;
    @Spy
    @InjectMocks
    private RegisteredCurrencyController registeredCurrencyController;
    private MockMvc mockMvc;
    private Currency firstCurrency;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.registeredCurrencyController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.registeredCurrencyController).assertIsUser("1");
        firstCurrency = new Currency();
        firstCurrency.setCreationDate(LocalDateTime.now());
        firstCurrency.setLastUpdate(LocalDateTime.now());
        firstCurrency.setName("test");
        firstCurrency.setId(1);
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

}
