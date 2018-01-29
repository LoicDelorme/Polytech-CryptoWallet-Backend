package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.requesters.JsonStringRestfulRequester;
import fr.polytech.codev.backend.requesters.RestfulRequester;

import java.math.BigDecimal;
import java.util.List;

public class CoinMarketCapServices {

    public static final String DEFAULT_COIN_MARKET_CAP_TICKER_BASE_URL = "https://api.coinmarketcap.com/v1/ticker/";

    public static final String DEFAULT_COIN_MARKET_CAP_TICKER_RESOURCE_URL = "%s/";

    public static final String DEFAULT_COIN_MARKET_CAP_GRAPHS_BASE_URL = "https://graphs2.coinmarketcap.com/currencies/";

    public static final String DEFAULT_COIN_MARKET_CAP_GRAPHS_ALL_RESOURCE_URL = "%s/";

    public static final String DEFAULT_COIN_MARKET_CAP_GRAPHS_ALL_BETWEEN_RESOURCE_URL = "%s/%s/%s/";

    private final RestfulRequester coinMarketCapTickerRequester;

    private final RestfulRequester coinMarketCapGraphsRequester;

    public CoinMarketCapServices() {
        this.coinMarketCapTickerRequester = new JsonStringRestfulRequester(DEFAULT_COIN_MARKET_CAP_TICKER_BASE_URL);
        this.coinMarketCapGraphsRequester = new JsonStringRestfulRequester(DEFAULT_COIN_MARKET_CAP_GRAPHS_BASE_URL);
    }

    public GraphsResponse allPrices(String cryptocurrencyResourceUrl) {
        return this.coinMarketCapGraphsRequester.get(String.format(DEFAULT_COIN_MARKET_CAP_GRAPHS_ALL_RESOURCE_URL, cryptocurrencyResourceUrl), GraphsResponse.class);
    }

    public GraphsResponse allPricesBetween(String cryptocurrencyResourceUrl, String startDate, String endDate) {
        return this.coinMarketCapGraphsRequester.get(String.format(DEFAULT_COIN_MARKET_CAP_GRAPHS_ALL_BETWEEN_RESOURCE_URL, cryptocurrencyResourceUrl, startDate, endDate), GraphsResponse.class);
    }

    public TickerResponse getCurrentPrice(String cryptocurrencyResourceUrl) {
        return this.coinMarketCapTickerRequester.get(String.format(DEFAULT_COIN_MARKET_CAP_TICKER_RESOURCE_URL, cryptocurrencyResourceUrl), TickerResponse[].class)[0];
    }

    public static class TickerResponse {

        private BigDecimal priceUsd;

        private BigDecimal priceBtc;

        public BigDecimal getPriceUsd() {
            return this.priceUsd;
        }

        public void setPrice_usd(BigDecimal priceUsd) {
            this.priceUsd = priceUsd;
        }

        public BigDecimal getPriceBtc() {
            return this.priceBtc;
        }

        public void setPrice_btc(BigDecimal priceBtc) {
            this.priceBtc = priceBtc;
        }
    }

    public static class GraphsResponse {

        private List<List<BigDecimal>> marketCapByAvailableSupply;

        private List<List<BigDecimal>> priceBtc;

        private List<List<BigDecimal>> priceUsd;

        private List<List<BigDecimal>> volumeUsd;

        public List<List<BigDecimal>> getMarketCapByAvailableSupply() {
            return this.marketCapByAvailableSupply;
        }

        public void setMarket_cap_by_available_supply(List<List<BigDecimal>> marketCapByAvailableSupply) {
            this.marketCapByAvailableSupply = marketCapByAvailableSupply;
        }

        public List<List<BigDecimal>> getPriceBtc() {
            return this.priceBtc;
        }

        public void setPrice_btc(List<List<BigDecimal>> priceBtc) {
            this.priceBtc = priceBtc;
        }

        public List<List<BigDecimal>> getPriceUsd() {
            return this.priceUsd;
        }

        public void setPrice_usd(List<List<BigDecimal>> priceUsd) {
            this.priceUsd = priceUsd;
        }

        public List<List<BigDecimal>> getVolumeUsd() {
            return this.volumeUsd;
        }

        public void setVolume_usd(List<List<BigDecimal>> volumeUsd) {
            this.volumeUsd = volumeUsd;
        }
    }
}