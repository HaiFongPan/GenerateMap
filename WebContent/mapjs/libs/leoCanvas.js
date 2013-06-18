(function() {
	window.leoCanvas = {
		
	};
	var scriptName = "leoCanvas.js";

	leoCanvas.getScriptLocation = (function() {
		var r = new RegExp("(^|(.*?\\/))(" + scriptName + ")(\\?|$)"), s = document
				.getElementsByTagName('script'), src, m, l = "";
		for ( var i = 0, len = s.length; i < len; i++) {
			src = s[i].getAttribute('src');
			if (src) {
				var m = src.match(r);
				if (m) {
					l = m[1];
					break;
				}
			}
		}
		return (function() {
			return l;
		});
	})()

	var jsFile = [ "Util/util.js", 
	               "Layer/Layer.js", 
	               "Controls/Scale.js",
	               "Controls/Pan.js",

	               "Vector/Vector.js", 
	               "Geometry/Geometry.js", 
	               "Geometry/Point.js",
	               "Geometry/Line.js", 
	               "Renderer/Canvas.js" ];

	var jsTags = "";
	var host = leoCanvas.getScriptLocation();
	for ( var i = 0, len = jsFile.length; i < len; i++) {
		jsTags += "<script src='" + host + jsFile[i] + "'></script>"
	}

	document.write(jsTags);
})()