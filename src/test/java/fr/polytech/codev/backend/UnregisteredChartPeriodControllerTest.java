package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.unregistered.UnregisteredChartPeriodController;
import fr.polytech.codev.backend.entities.ChartPeriod;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.ChartPeriodServices;
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
public class UnregisteredChartPeriodControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/unregistered/chart-period";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private ChartPeriodServices chartPeriodServices;
    @Spy
    @InjectMocks
    private UnregisteredChartPeriodController unregisteredChartPeriodController;
    private MockMvc mockMvc;
    private ChartPeriod firstChartPeriod;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.unregisteredChartPeriodController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        firstChartPeriod = new ChartPeriod();
        firstChartPeriod.setId(1);
        firstChartPeriod.setCreationDate(LocalDateTime.now());
        firstChartPeriod.setLastUpdate(LocalDateTime.now());
        firstChartPeriod.setName("test");
    }

    @Test
    public void testGetAllChartPeriods() throws Exception {
        final List<ChartPeriod> expectedChartPeriods = new ArrayList<>();
        when(this.chartPeriodServices.all()).thenReturn(expectedChartPeriods);
        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedChartPeriods) + ",\"success\":true}"));
    }

    @Test
    public void testGetOneChartPeriod() throws Exception {
        final ChartPeriod expectedChartPeriod = new ChartPeriod();
        when(this.chartPeriodServices.get(1)).thenReturn(expectedChartPeriod);
        mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedChartPeriod) + ",\"success\":true}"));
    }

}
