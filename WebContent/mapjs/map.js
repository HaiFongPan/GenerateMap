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

function mapinit() {
	var mapoption = new MMapOptions();
	mapoption.zoom = 13; // 设置地图zoom级别
	mapoption.center = new MLngLat(120.156033, 30.319833);
	mapoption.toolbar = MConstants.ROUND; // 设置工具条
	mapoption.toolbarPos = new MPoint(20, 20); // 设置工具条在地图上的显示位置
	mapoption.overviewMap = MConstants.SHOW; // 设置鹰眼
	mapoption.dragPowerUp = true;
	mapObj = new MMap("mapObj", mapoption);
}

function addline(arr) {
	var linetype = new MLineStyle(); // 设置线样式
	linetype.alpha = 1;
	linetype.color = 0xFF3300;
	linetype.thickness = 3;
	linetype.lineType = MConstants.LINE_SOLID;

	// 设置标注参数选项
	var tipOption = new MTipOptions();
	tipOption.title = "标题！！！！！！";
	tipOption.content = "信息窗口内容！！！！"; // 信息窗口内容
	// 设置线的参数选项
	var lineopt = new MLineOptions();
	lineopt.lineStyle = linetype;
	lineopt.tipOption = tipOption;
	lineopt.canShowTip = true;
	// 在地图上添加多折线覆盖物
	for ( var i = 0; i < arr.length; i++) {
		var sub_arr = new Array();
		for ( var j = 0; j < arr[i].length; j++) {
			sub_arr.push(new MLngLat(arr[i][j].longi - 0.0103,
					arr[i][j].lati + 0.0044))
		}
		var line = new MPolyline(sub_arr, lineopt);
		mapObj.addOverlay(line, true); // 向地图添加线覆盖物。
	}

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
	
	for (var i = 392300; i < 398000; i += 600) {
	//	 for (var i = 790000; i < 796000; i += 600){
		callServer(i);
	}
}

function callServer(param) {

	var url = "getGps?param=" + param;
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
	// for(var i=0; i<5; i++) {
	// addPoint();
	// }
	// for (var i = 0; i < 5; i++) {
	// addCircle();
	// }
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
	layer.center.x = 54;
	layer.center.y = 400;
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
				// alert("moveStart");
				layer.center.x = 600;
				layer.center.y = 900;
				// alert(layer.center.x + "," + layer.center.y);
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

function addCircle() {
	var vectors = [];
	var circle = new Circle((Math.random() * 660 - 330),
			(Math.random() * 600 - 300), Math.random() * 10 + 5);
	var vector = new Vector(circle);
	vector.style = circleStyle;
	vectors.push(vector);
	layer.addVectors(vectors);
}

function zoomin() {
	var zoom = layer.zoom + 5;
	layer.moveTo(zoom, layer.center);
}

function zoomout() {
	var zoom = layer.zoom - 5;
	layer.moveTo(zoom, layer.center);
}

function addMarker(arr) {
	var tipOption = new MTipOptions(); // 添加信息窗口
	tipOption.title = "信息窗口标题"; // 信息窗口标题
	tipOption.content = "信息窗口内容"; // 信息窗口内容
	var labelOption = new MLabelOptions(); // 添加标注
	labelOption.content = "点"; // 标注的内容
	labelOption.hasBorder = true; // 设置标注背景是否有边框，默认为false，即没有边框
	labelOption.hasBackground = true; // 设置标注是否有背景，默认为false，即没有背景
	// 标注左上角相对于图片中下部的锚点。Label左上角与图片中下部重合时，记为像素坐标原点(0,0)。
	labelOption.labelPosition = new MPoint(10, 10);
	// 构建一个名为markerOption的点选项对象。
	var markerOption = new MMarkerOptions();
	// 标注图片或SWF的url，默认为蓝色气球图片
	// markerOption.imageUrl="http://code.mapabc.com/images/lan_1.png";
	// 是否使用图片代理形式
	// 如果imageUrl属性的图片资源所在域名下没有crossdomain.xml，则需要用代理形式添加该图片资源
	markerOption.picAgent = false;
	// 设置图片相对于加点经纬度坐标的位置。九宫格位置。默认BOTTOM_CENTER代表正下方
	markerOption.imageAlign = MConstants.BOTTOM_CENTER;
	// 设置点的标注参数选项
	markerOption.labelOption = labelOption;
	// 拖动结束后是否有弹跳效果,ture，有弹跳效果；false，没有弹跳效果（默认）
	// 当有弹跳效果的时候，marker的imageAlign属性必须为BOTTOM_CENTER，否则弹跳效果显示不正确
	markerOption.isBounce = false;
	// 设置点是否为可编辑状态,rue，可以编辑； false，不可编辑（默认）
	markerOption.isEditable = false;
	// 设置点的信息窗口参数选项
	markerOption.tipOption = tipOption;
	// 是否在地图中显示信息窗口，true，可以显示（默认）；false，不显示
	markerOption.canShowTip = true;
	// 设置图标旋转的角度
	markerOption.rotation = "40";
	// 是否显示阴影，默认为true，即有阴影
	markerOption.hasShadow = true;
	// 设置点是否高亮显示
	// 设置高亮显示与设置可编辑有冲突，只能设置一个，不能同时设置。
	markerOption.isDimorphic = true;
	// 设置第二种状态的颜色，默认为0xFF0000，即红色
	markerOption.dimorphicColor = "0x00A0FF";
	// 通过经纬度坐标及参数选项确定标注信息

	for ( var i = 0; i < arr.length; i++) {

		for ( var j = 0; j < arr[i].length; j++) {
			Mmarker = new MMarker(new MLngLat(arr[i][j].longi - 0.0103,
					arr[i][j].lati + 0.0044), markerOption);
			// 对象编号，也是对象的唯一标识
			Mmarker.id = "mark" + i + j;
			// 向地图添加覆盖物
			mapObj.addOverlay(Mmarker, true);
		}

	}
}
