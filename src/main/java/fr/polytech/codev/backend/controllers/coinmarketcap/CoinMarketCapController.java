package fr.polytech.codev.backend.controllers.coinmarketcap;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.impl.CoinMarketCapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/coinmarketcap")
public class CoinMarketCapController extends AbstractController {

    @Autowired
    private CoinMarketCapServices coinMarketCapServices;

    @RequestMapping(value = "/graphs/{cryptocurrencyResourceUrl}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity allPrices(@PathVariable("cryptocurrencyResourceUrl") String cryptocurrencyResourceUrl) {
        return serializeSuccessResponse(this.coinMarketCapServices.allPrices(cryptocurrencyResourceUrl));
    }

    @RequestMapping(value = "/graphs/{cryptocurrencyResourceUrl}/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity allPricesBetween(@PathVariable("cryptocurrencyResourceUrl") String cryptocurrencyResourceUrl, @PathVariable String startDate, @PathVariable String endDate) {
        return serializeSuccessResponse(this.coinMarketCapServices.allPricesBetween(cryptocurrencyResourceUrl, startDate, endDate));
    }

    @RequestMapping(value = "/ticker/{cryptocurrencyResourceUrl}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCurrentPrice(@PathVariable("cryptocurrencyResourceUrl") String cryptocurrencyResourceUrl) {
        return serializeSuccessResponse(this.coinMarketCapServices.getCurrentPrice(cryptocurrencyResourceUrl));
    }
}