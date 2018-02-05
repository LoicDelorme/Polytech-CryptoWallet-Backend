package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorChartPeriodController;
import fr.polytech.codev.backend.entities.ChartPeriod;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.ChartPeriodForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.ChartPeriodServices;
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
public class AdministratorChartPeriodControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/chart-period";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private ChartPeriodServices chartPeriodServices;
    @Spy
    @InjectMocks
    private AdministratorChartPeriodController administratorChartPeriodController;
    private MockMvc mockMvc;
    private ChartPeriod firstChartPeriod;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorChartPeriodController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorChartPeriodController).assertUserIsAdministrator("1");
        firstChartPeriod = new ChartPeriod();
        firstChartPeriod.setId(1);
        firstChartPeriod.setCreationDate(LocalDateTime.now());
        firstChartPeriod.setLastUpdate(LocalDateTime.now());
        firstChartPeriod.setName("test");
    }

    @Test
    public void testGetAllChartPeriodsWithValidToken() throws Exception {
        final List<ChartPeriod> expectedChartPeriods = new ArrayList<>();
        when(this.chartPeriodServices.all()).thenReturn(expectedChartPeriods);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedChartPeriods) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllChartPeriodsWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneChartPeriodWithValidToken() throws Exception {
        final ChartPeriod expectedChartPeriod = new ChartPeriod();
        when(this.chartPeriodServices.get(1)).thenReturn(expectedChartPeriod);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedChartPeriod) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneChartPeriodWithInvalidToken() throws Exception {
        final ChartPeriod expectedChartPeriod = new ChartPeriod();
        when(this.chartPeriodServices.get(1)).thenReturn(expectedChartPeriod);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidChartPeriodWithValidToken() throws Exception {
        when(this.chartPeriodServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneChartPeriod() throws Exception {
        ChartPeriodForm newChartPeriod = new ChartPeriodForm();
        newChartPeriod.setName("new ChartPeriod");
        when(this.chartPeriodServices.insert(newChartPeriod)).thenReturn(new ChartPeriod());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newChartPeriod))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneChartPeriodWithCorrectId() throws Exception {
        ChartPeriodForm newChartPeriod = new ChartPeriodForm();
        newChartPeriod.setName("new ChartPeriod");
        when(chartPeriodServices.get(1)).thenReturn(firstChartPeriod);
        doReturn(new ChartPeriod()).when(this.chartPeriodServices).update(1, newChartPeriod);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newChartPeriod))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneChartPeriodWithUnknownId() throws Exception {
        ChartPeriodForm newChartPeriod = new ChartPeriodForm();
        newChartPeriod.setName("new ChartPeriod");
        when(this.chartPeriodServices.update(2, newChartPeriod)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newChartPeriod)));
    }

    @Test
    public void testDeleteValidChartPeriod() throws Exception {
        when(this.chartPeriodServices.get(1)).thenReturn(this.firstChartPeriod);
        doNothing().when(this.chartPeriodServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownChartPeriod() throws Exception {
        doThrow(new UnknownEntityException()).when(this.chartPeriodServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
