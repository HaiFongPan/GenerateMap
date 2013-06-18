//工具类型文件
leoCanvas.lastId = 0;
//取得id。
leoCanvas.getId = function (preString) {
	leoCanvas.lastId += 1;
	return preString + leoCanvas.lastId;
}

//图形的范围
leoCanvas.Bounds = function (x1, y1, x2, y2) {
	this.leftBottom = new leoCanvas.Position(x1, y1);
	this.rigthTop = new leoCanvas.Position(x2, y2);
	this.leftTop = new leoCanvas.Position(x1, y2);
	this.rightBottom = new leoCanvas.Position(x2, y1);
	this.left = x1;
	this.right = x2;
	this.bottom = y1;
	this.top = y2;
}

leoCanvas.Bounds.prototype.getCenter = function (){
	var w = this.right - this.left;
	var h = this.top - this.bottom;
	return new leoCanvas.Position(this.left + w/2, this.bottom + h/2);
}

//位置信息类
leoCanvas.Position = function (x, y) {
	this.x = x;
	this.y = y;
}

//大小类
leoCanvas.Size = function (w, h) {
	this.w = w;
	this.h = h;
}

//矢量图形的默认样式
leoCanvas.defaultStyle = function () {
	this.fill = true;
	this.stroke = true;
	this.pointRadius = 0.2;
	this.fillOpacity = 0.6;
	this.strokeOpacity = 1;
	this.fillColor = "red";
	this.strokeColor = "black";
}

//保存时间的this。
leoCanvas.bindAsEventListener = function(func, object) {
	return function(event) {
		return func.call(object, event || window.event);
	};
}

//阻止事件冒泡
leoCanvas.stopEventBubble = function(e) {
    if (e.preventDefault) {
        e.preventDefault();
    } else {
        e.returnValue = false;
    }

    if (e && e.stopPropagation)
        e.stopPropagation();
    else
        window.event.cancelBubble=true;
}