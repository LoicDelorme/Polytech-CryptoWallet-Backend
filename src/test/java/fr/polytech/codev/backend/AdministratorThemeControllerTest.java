package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorThemeController;
import fr.polytech.codev.backend.entities.Theme;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.ThemeForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.ThemeServices;
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
public class AdministratorThemeControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/theme";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private ThemeServices themeServices;
    @Spy
    @InjectMocks
    private AdministratorThemeController administratorThemeController;
    private MockMvc mockMvc;
    private Theme firstTheme;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorThemeController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorThemeController).assertUserIsAdministrator("1");
        firstTheme = new Theme();
        firstTheme.setId(1);
        firstTheme.setCreationDate(LocalDateTime.now());
        firstTheme.setLastUpdate(LocalDateTime.now());
        firstTheme.setName("test");
    }

    @Test
    public void testGetAllThemesWithValidToken() throws Exception {
        final List<Theme> expectedThemes = new ArrayList<>();
        when(this.themeServices.all()).thenReturn(expectedThemes);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedThemes) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllThemesWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneThemeWithValidToken() throws Exception {
        final Theme expectedTheme = new Theme();
        when(this.themeServices.get(1)).thenReturn(expectedTheme);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedTheme) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneThemeWithInvalidToken() throws Exception {
        final Theme expectedTheme = new Theme();
        when(this.themeServices.get(1)).thenReturn(expectedTheme);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidThemeWithValidToken() throws Exception {
        when(this.themeServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneTheme() throws Exception {
        ThemeForm newTheme = new ThemeForm();
        newTheme.setName("new theme");
        when(this.themeServices.insert(newTheme)).thenReturn(new Theme());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newTheme))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneThemeWithCorrectId() throws Exception {
        ThemeForm newTheme = new ThemeForm();
        newTheme.setName("new theme");
        when(themeServices.get(1)).thenReturn(firstTheme);
        doReturn(new Theme()).when(this.themeServices).update(1, newTheme);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newTheme))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneThemeWithUnknownId() throws Exception {
        ThemeForm newTheme = new ThemeForm();
        newTheme.setName("new theme");
        when(this.themeServices.update(2, newTheme)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newTheme)));
    }

    @Test
    public void testDeleteValidTheme() throws Exception {
        when(this.themeServices.get(1)).thenReturn(this.firstTheme);
        doNothing().when(this.themeServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownTheme() throws Exception {
        doThrow(new UnknownEntityException()).when(this.themeServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
