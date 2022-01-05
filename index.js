const express = require('express');
let app = express();
const request = require('request');
const PORT = 8080;
const DEFAULT_STOCK = 'IBM';
APIKEY = 'BYHKP7FYWBWRVJN3';


function fetchPriceForStock(stock, cb){

	let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stock}&apikey=${APIKEY}`

	request.get({
    url: url,
    json: true,
    headers: {'User-Agent': 'request'}
  	}, (err, res, data) => {
	    if (err) {
	      console.log('Error:', err);
	    } else if (res.statusCode !== 200) {
	      console.log('Status:', res.statusCode);
	    } else {
	      // data is successfully parsed as a JSON object:
	      console.log(data);
	      Price = data['Global Quote']['05. price']
	      console.log(`The Price is ${Price}`);

	      return cb(null, {
	      	symbol: stock,
	      	price: Price
	      });

	    }
	});
}

app.get('/stock', (req, res, next) => {
	/*
	var text = '<html><body>hey there<br>it was a good lecture</body><html>'
	res.send(text);
	*/
	console.log("inside the server");
 	let stockSymbol = req.query.symbol || DEFAULT_STOCK;
 	console.log(stockSymbol);
 	
 	
 	fetchPriceForStock(stockSymbol, (err, price) =>{
 		if (err) return res.status(500).json({err: err.message});
 		return res.json(price);
 	});

});

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
