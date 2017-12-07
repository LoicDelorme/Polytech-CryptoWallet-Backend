var httpRequest = new XMLHttpRequest();
httpRequest.open("GET", "https://files.coinmarketcap.com/generated/search/quick_search.json", false);
httpRequest.send(null);

var data = JSON.parse(httpRequest.responseText);
var template = "INSERT INTO cryptocurrencies VALUES ({0}, '{1}', '{2}', 'https://files.coinmarketcap.com/static/img/coins/64x64/{3}.png', 'https://graphs.coinmarketcap.com/currencies/', '{4}/', now(), now());"

for (offset = 0; offset < data.length; offset++) { 
    console.log(template.format(offset + 1, data[offset]["name"], data[offset]["symbol"], data[offset]["slug"], data[offset]["slug"]));
}