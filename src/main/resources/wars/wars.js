$(document).ready(function() {
	// hideable alert thanks to Twitter Bootstrap
	//$(".alert").alert()
    console.log("asdasdsad")
	// map initialization
	// open a WebSocket
	var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
    var mapSocket = new WS("ws"+location.protocol.substring(4)+"//"+window.location.hostname+":6696/wars/ws")
	mapSocket.onmessage = function(event) {
		console.log("data")
		console.log(event.data)
    }
	// if errors on websocket
	var onalert = function(event) {
        $(".alert").removeClass("hide")
        $("#map").addClass("hide")
        log("websocket connection closed or lost")
    }
	mapSocket.onerror = onalert
	mapSocket.onclose = onalert
})
