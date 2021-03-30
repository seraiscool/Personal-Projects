var express = require('express');

var app = express();
var server = app.listen(8082);

app.use(express.static('public'));

console.log("My Socket Server is running");

var fs = require('fs');


var socket = require('socket.io');
var io = socket(server);

io.on('connection', newConnection);

function newConnection(socket) {
  console.log('New Connection:' + socket.id);
  socket.on('mouse', storeData);

	function storeData(data) {
    console.log(data);
		writeCSV(data);
  }
}

function writeCSV(data) {
	fs.appendFile('out.csv', data.title + ',' + data.v + ',' + data.a + '\n', function (err) {
 		if (err) return console.log(err);
  	console.log('error occured');
	});
}