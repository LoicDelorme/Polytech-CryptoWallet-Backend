package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorDeviceController;
import fr.polytech.codev.backend.entities.Device;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.DeviceForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.DeviceServices;
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
import java.util.UUID;

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
public class AdministratorDeviceControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/device";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private DeviceServices deviceServices;
    @Spy
    @InjectMocks
    private AdministratorDeviceController administratorDeviceController;
    private MockMvc mockMvc;
    private Device firstDevice;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorDeviceController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorDeviceController).assertUserIsAdministrator("1");
        firstDevice = new Device();
        firstDevice.setId(1);
        firstDevice.setCreationDate(LocalDateTime.now());
        firstDevice.setLastUpdate(LocalDateTime.now());
        firstDevice.setPlatform("platform");
        firstDevice.setUuid(UUID.randomUUID().toString());
        firstDevice.setUser(new User());
    }

    @Test
    public void testGetAllDevicesWithValidToken() throws Exception {
        final List<Device> expectedDevices = new ArrayList<>();
        when(this.deviceServices.all()).thenReturn(expectedDevices);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedDevices) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllDevicesWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneDeviceWithValidToken() throws Exception {
        final Device expectedDevice = new Device();
        when(this.deviceServices.get(1)).thenReturn(expectedDevice);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedDevice) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneDeviceWithInvalidToken() throws Exception {
        final Device expectedDevice = new Device();
        when(this.deviceServices.get(1)).thenReturn(expectedDevice);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidDeviceWithValidToken() throws Exception {
        when(this.deviceServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneDevice() throws Exception {
        DeviceForm newDevice = new DeviceForm();
        newDevice.setUserId(1);
        newDevice.setPlatform("new platform");
        when(this.deviceServices.insert(newDevice)).thenReturn(new Device());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newDevice))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneDeviceWithCorrectId() throws Exception {
        DeviceForm newDevice = new DeviceForm();
        newDevice.setUserId(1);
        newDevice.setPlatform("new platform");
        when(deviceServices.get(1)).thenReturn(firstDevice);
        doReturn(new Device()).when(this.deviceServices).update(1, newDevice);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newDevice))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneDeviceWithUnknownId() throws Exception {
        DeviceForm newDevice = new DeviceForm();
        newDevice.setUserId(1);
        newDevice.setPlatform("new platform");
        when(this.deviceServices.update(2, newDevice)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newDevice)));
    }

    @Test
    public void testDeleteValidDevice() throws Exception {
        when(this.deviceServices.get(1)).thenReturn(this.firstDevice);
        doNothing().when(this.deviceServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownDevice() throws Exception {
        doThrow(new UnknownEntityException()).when(this.deviceServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
