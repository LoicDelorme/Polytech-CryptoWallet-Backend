package fr.polytech.codev.backend;

import fr.polytech.codev.backend.controllers.administrators.AdministratorWalletController;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.WalletForm;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.WalletServices;
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
public class AdministratorWalletControllerTest {

    private final static String BASE_URL = "/api/cryptowallet/administrator/%s/wallet";
    private final JsonStringSerializer jsonStringSerializer = new JsonStringSerializer();
    @Mock
    private WalletServices walletServices;
    @Spy
    @InjectMocks
    private AdministratorWalletController administratorWalletController;
    private MockMvc mockMvc;
    private Wallet firstWallet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.administratorWalletController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
        doReturn(new User()).when(this.administratorWalletController).assertUserIsAdministrator("1");
        firstWallet = new Wallet();
        firstWallet.setId(1);
        firstWallet.setCreationDate(LocalDateTime.now());
        firstWallet.setLastUpdate(LocalDateTime.now());
        firstWallet.setName("test");
        firstWallet.setUser(new User());
        firstWallet.setAssets(new ArrayList<>());
    }

    @Test
    public void testGetAllWalletsWithValidToken() throws Exception {
        final List<Wallet> expectedWallets = new ArrayList<>();
        when(this.walletServices.all()).thenReturn(expectedWallets);
        mockMvc.perform(get(String.format(BASE_URL, "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedWallets) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetAllWalletsWithInvalidToken() throws Exception {
        mockMvc.perform(get(String.format(BASE_URL, "2")));
    }

    @Test
    public void testGetOneWalletWithValidToken() throws Exception {
        final Wallet expectedWallet = new Wallet();
        when(this.walletServices.get(1)).thenReturn(expectedWallet);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk()).andExpect(content().string("{\"data\":" + jsonStringSerializer.to(expectedWallet) + ",\"success\":true}"));
    }

    @Test(expected = NestedServletException.class)
    public void testGetOneWalletWithInvalidToken() throws Exception {
        final Wallet expectedWallet = new Wallet();
        when(this.walletServices.get(1)).thenReturn(expectedWallet);
        mockMvc.perform(get(String.format(BASE_URL + "/1", "2")));
    }

    @Test(expected = NestedServletException.class)
    public void testGetInvalidWalletWithValidToken() throws Exception {
        when(this.walletServices.get(2)).thenThrow(new UnknownEntityException());
        mockMvc.perform(get(String.format(BASE_URL + "/2", "1")));
    }

    @Test
    public void testPostOneWallet() throws Exception {
        WalletForm newWallet = new WalletForm();
        newWallet.setName("new wallet");
        newWallet.setUserId(1);
        when(this.walletServices.insert(newWallet)).thenReturn(new Wallet());
        mockMvc.perform(post(String.format(BASE_URL, "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newWallet))).andExpect(status().isOk());
    }

    @Test
    public void testPutOneWalletWithCorrectId() throws Exception {
        WalletForm newWallet = new WalletForm();
        newWallet.setName("new wallet");
        newWallet.setUserId(1);
        when(walletServices.get(1)).thenReturn(firstWallet);
        doReturn(new Wallet()).when(this.walletServices).update(1, newWallet);
        mockMvc.perform(put(String.format(BASE_URL + "/1", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newWallet))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testPutOneWalletWithUnknownId() throws Exception {
        WalletForm newWallet = new WalletForm();
        newWallet.setName("new wallet");
        newWallet.setUserId(1);
        when(this.walletServices.update(2, newWallet)).thenThrow(new UnknownEntityException());
        mockMvc.perform(put(String.format(BASE_URL + "/2", "1")).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringSerializer.to(newWallet)));
    }

    @Test
    public void testDeleteValidWallet() throws Exception {
        when(this.walletServices.get(1)).thenReturn(this.firstWallet);
        doNothing().when(this.walletServices).delete(1);
        mockMvc.perform(delete(String.format(BASE_URL + "/1", "1"))).andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteUnknownWallet() throws Exception {
        doThrow(new UnknownEntityException()).when(this.walletServices).delete(2);
        mockMvc.perform(delete(String.format(BASE_URL + "/2", "1")));
    }


}
