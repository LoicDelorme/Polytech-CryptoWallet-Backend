package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.impl.AssetServices;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AssetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/asset")
public class AdministratorAssetController extends AbstractController {

    @Autowired
    private AssetServices assetServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.all());
    }

    @RequestMapping(value = "wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.get(walletId, cryptocurrencyId));
    }

    @RequestMapping(value = "wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByWallet(@PathVariable String tokenValue, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.getByWallet(walletId));
    }

    @RequestMapping(value = "cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable String tokenValue, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.getByCryptocurrency(cryptocurrencyId));
    }

    @RequestMapping(value = "wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.insert(walletId, cryptocurrencyId, deserialize(data, AssetForm.class)));
    }

    @RequestMapping(value = "wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.assetServices.update(walletId, cryptocurrencyId, deserialize(data, AssetForm.class)));
    }

    @RequestMapping(value = "wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        this.assetServices.delete(walletId, cryptocurrencyId);
        return serializeSuccessResponse();
    }
}