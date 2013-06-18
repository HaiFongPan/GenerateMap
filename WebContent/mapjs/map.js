var mapObj;
var xmlHttp = false;
var layer;
var circleStyle;

function canvasInit() {
	circleStyle = {
		fillColor : "blue",
		fill : true,
		stroke : true,
		fillOpacity : 1,
		strokeOpacity : 1
	};
	window.addEventListener("load", init2, true);
}

function xmlhttpinit() {
	// var http_request = new ActiveXObject("Msxml2.XMLHTTP");

	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlHttp = false;
		}
	}
	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
		xmlHttp = new XMLHttpRequest();
	}
}

function test() {
//	for ( var i =  789300; i <=  792645 ; i += 1000) {
//		for ( var j = 73203; j < 73800; j+= 70) {

		callServer( 791300,146407);
//		}
//	}
}
function test_1() {
	for ( var i = 789300; i <= 792645; i += 1000) {
//		for ( var j = 146500; j < 147600; j += 140) {

			callServer( i,146500);
		}
	//}
}

function callServer(px, py) {

	var url = "getGps?px=" + px + "&py=" + py;
	if (!xmlHttp) {
		alert("xmlHttp == false");
	} else {
		xmlHttp.open("Get", url, false);
		xmlHttp.onreadystatechange = showline;
		xmlHttp.send(null);
	}
}

function showline() {
	if (xmlHttp.readyState == 4) {
		var response = xmlHttp.responseText;
		var responseToJson = eval('(' + response + ')');
		var LngLatArr = new Array();
		// alert(responseToJson);
		for ( var i = 0; i < responseToJson.length; i++) {
			LngLatArr.push(responseToJson[i].list);
			// alert(responseToJson[i].list);
			// LngLatArr.push(new
			// MLngLat(responseToJson[i].px,responseToJson[i].py));
		}
		// alert(responseToJson);
		showPoints2(responseToJson);
		// addLine(LngLatArr);
		// addMarker(LngLatArr);
		// drawXY(responseToJson);
	}
}

function init2() {
	var div = document.getElementById("renderer2");
	div.innerHTML = "";
	layer = new Layer(div);

}

function addPoint() {
	var vectors = [];
	var point = new Point((Math.random() * 660 - 330),
			(Math.random() * 600 - 300));
	vectors.push(new Vector(point));
	layer.addVectors(vectors);
}
function showPoints2(arr) {
	var vectors = [];
	layer.center.x = 1000;
	layer.center.y = 300;
	layer.zoom = 10;
	layer.moveTo(layer.zoom, layer.center);
	for ( var i = 0; i < arr.length; i++) {
		var point = new Point(arr[i].px, arr[i].py);
		vectors.push(new Vector(point));
	}
	layer.addVectors(vectors);
}
function showPoints(arr) {
	var vectors = [];
	for ( var i = 0; i < arr.length; i++) {
		for ( var j = 0; j < arr[i].length; j++) {
			if (i == 0 && j == 0) {
				layer.center.x = 1300;
				layer.center.y = 100;
				layer.moveTo(layer.zoom, layer.center);
			}
			var point = new Point(arr[i][j].px - 1584000, arr[i][j].py - 297680);
			vectors.push(new Vector(point));
		}
	}
	layer.addVectors(vectors);
}

function addLine(arr) {

	alert("alert start addline");
	for ( var i = 0; i < arr.length; i++) {
		var vectors = [];
		var points = [];

		for ( var j = 0; j < arr[i].length; j++) {
			var point = new Point(arr[i][j].px, arr[i][j].py);
			points.push(point);
		}
		var line = new Vector(new Line(points));
		alert("pushing");
		vectors.push(line);
		alert("addVector");
		layer.addVectors(vectors);
	}
	alert("end addline");
	/*
	 * for(var i = 0; i < 3; i++) { var point = new
	 * Point((Math.random()*400-200), (Math.random()*300-150));
	 * points.push(point); } var line = new Vector(new Line(points));
	 * vectors.push(line); layer.addVectors(vectors);
	 */

}


function zoomin() {
	var zoom = layer.zoom + 2;
	layer.moveTo(zoom, layer.center);
}

function zoomout() {
	var zoom = layer.zoom - 2;
	layer.moveTo(zoom, layer.center);
}

