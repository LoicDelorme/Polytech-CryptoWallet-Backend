package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.unregistered.UnregisteredCryptocurrencyController;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.CryptocurrencyServices;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class UnregisteredCryptocurrencyControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/unregistered/cryptocurrency";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private CryptocurrencyServices cryptocurrencyServices;
    @Spy
    @InjectMocks
    private UnregisteredCryptocurrencyController unregisteredCryptocurrencyController;
    private MockMvc mockMvc;
    private Cryptocurrency firstCryptocurrency;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.unregisteredCryptocurrencyController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
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
    public void testGetAllCryptocurrencies() throws Exception {
        final List<Cryptocurrency> expectedCryptocurrencies = new ArrayList<>();
        when(this.cryptocurrencyServices.all()).thenReturn(expectedCryptocurrencies);
        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCryptocurrencies) + ",\"success\":true}"));
    }


    @Test
    public void testGetOneCryptocurrency() throws Exception {
        final Cryptocurrency expectedCryptocurrency = new Cryptocurrency();
        when(this.cryptocurrencyServices.get(1)).thenReturn(expectedCryptocurrency);
        mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedCryptocurrency) + ",\"success\":true}"));
    }


}
