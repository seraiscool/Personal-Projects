
function preload() {

}

let valency;
let arousal;
var socket;


function setup() {
  createCanvas(1000, 1000);
  arousal = 0;
  valency = 0;
	// creates play button
  socket = io();
	socket.on('start', start);
	console.log(socket);

  button = createButton("Play Song");
  button.position(475, 150);
	button.mousePressed(playSong);
  //Moves onto next song button
  button = createButton("Next Song");
  button.position(475, 200);
	button.mousePressed(nextSong);
  //socket.io.connect('https://localhost:8082');

}

function start() {

}

function mouseClicked() {
 
  if (mouseX < 700 && mouseX > 300 && mouseY < 700 && mouseY > 300) {
    arousal = -1 * (mouseY - 500) / 200;
    valency = (mouseX - 500) / 200;
  }
  
}


function nextSong() {
	console.log("next");	
	
	var data = {
    title: 'Sample',
    v: valency,
    a: arousal,
  };

  socket.emit('mouse', data);

	arousal = 0;
	valency = 0;
}

function playSong() {
	console.log("play");	
}

function draw() {
  background(255);
  fill(0);
  // creates text header
  strokeWeight(1);
  textFont('Georgia');
  textStyle(ITALIC);
  textSize(50);
  textAlign(CENTER);
  text("Music Classifier", 500, 45);
  textSize(20);
  text('Please rate the songs below using the graph above for sentimental analysis', 500, 115);
  textAlign(CENTER);

  

  // creates graphical interface for rating
  textSize(20);
  fill(255, 255, 255);
  stroke(0, 0, 0);
  strokeWeight(4);
  rect(300, 300, 400, 400);
  line(500, 300, 500, 700);
  line(300, 500, 700, 500);

  textAlign(CENTER);
  text('Arousal', 500, 280);
  text('Positivity', 750, 500);

  strokeWeight(1);

  fill(0);
  text('Exciting', v(0.25), a(0.75));
  text('Happy', v(0.5), a(0.5));
  text('Pleasing', v(0.75), a(0.25));
  text('Relaxing', v(0.75), a(-0.25));
  text('Peaceful', v(0.5), a(-0.5));
  text('Calm', v(0.25), a(-0.75));
  text('Sleepy', v(-0.25), a(-0.75));
  text('Bored', v(-0.5), a(-0.5));
  text('Sad', v(-0.75), a(-0.25));
  text('Nervous', v(-0.75), a(0.25));
  text('Angry', v(-0.5), a(0.5));
  text('Annoying', v(-0.25), a(0.75));

  fill(255, 0, 0);
  strokeWeight(1);
  x = valency * 200 + 500;
  y = -arousal * 200 + 500;
  ellipse(x, y, 30, 30);
  text('(' + (round(valency * 100) / 100) + ', ' + (round(arousal * 100) / 100) + ')', x + 30, y + 30);

  fill(0);
  text('Place the marker according to how you felt while listening to the piece.', 500, 725);
  text('The x-scale cooresponds to the positivity/negativity of the piece.', 500, 750);
  text('The y-scale cooresponds to the energy of the piece.', 500, 775)
  text('The words on the graph are a guideline to show where some common emotions would be placed.', 500, 800);
}

function v(num) {
  return num * 200 + 500;
}
function a(num) {
  return -num * 200 + 500;
}