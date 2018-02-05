package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorSettingController;
import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.SettingServices;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nicolas on 04/02/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@EnableWebMvc
public class AdministratorSettingControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/setting";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private SettingServices settingServices;
    @Spy
    @InjectMocks
    private AdministratorSettingController administratorSettingController;
    private MockMvc mockMvc;
    private Setting firstSetting;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorSettingController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorSettingController).assertUserIsAdministrator("1");
        firstSetting = new Setting();
        firstSetting.setId(1);
        firstSetting.setCreationDate(LocalDateTime.now());
        firstSetting.setLastUpdate(LocalDateTime.now());
        firstSetting.setChartPeriod(new ChartPeriod());
        firstSetting.setTheme(new Theme());
        firstSetting.setCurrency(new Currency());
    }

    @Test
    public void testGetAllSettingsWithValidToken() throws Exception {
        final List<Setting> expectedSettings = new ArrayList<>();
        when(this.settingServices.all()).thenReturn(expectedSettings);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedSettings) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllSettingsWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneSettingWithValidToken() throws Exception {
        final Setting expectedSetting = new Setting();
        when(this.settingServices.get(1)).thenReturn(expectedSetting);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedSetting) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneSettingWithInvalidToken() throws Exception {
        final Setting expectedSetting = new Setting();
        when(this.settingServices.get(1)).thenReturn(expectedSetting);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidSettingWithValidToken() throws Exception {
        when(this.settingServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPutOneSettingWithCorrectId() throws Exception {
        SettingForm newSetting = new SettingForm();
        newSetting.setChartPeriodId(1);
        newSetting.setCurrencyId(1);
        newSetting.setThemeId(1);
        when(settingServices.get(1)).thenReturn(firstSetting);
        doReturn(new Setting()).when(this.settingServices).update(1, newSetting);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newSetting))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneSettingWithUnknownId() throws Exception {
        SettingForm newSetting = new SettingForm();
        newSetting.setChartPeriodId(1);
        newSetting.setCurrencyId(1);
        newSetting.setThemeId(1);
        when(this.settingServices.update(2, newSetting)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newSetting)));
    }

}
