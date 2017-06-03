// Global variables; see the code below for their purpose.
var map;
var source;
var target;
var line;

// Main program code. The jQuery construct $(document).ready(function(){ ... }
// ensures that this is executed only when the page has been loaded.
$(document).ready(function(){

    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 48.012653, lng: 7.835194},
        zoom: 15
    });
    var source = new google.maps.Marker({map: map, draggable: true,
        position: new google.maps.LatLng(48.012653,7.835194)});
    var target = new google.maps.Marker({map : map, draggable: true,
        position: new google.maps.LatLng(48.010683,7.817760)});

    // A Polyline object drawing this path on the map.
    // See http://tinyurl.com/7mry4xl#Polyline.
    var path = [ source.getPosition(), target.getPosition() ];
    line = new google.maps.Polyline({map: map, path: path,
        strokeColor: "blue", strokeWeight: 8, strokeOpacity: 0.5});

    // Add an event listener to the source and target marker that
    // calls a function while they are being dragged.
    // See http://tinyurl.com/7mry4xl#MapsEventListener
    // and http://tinyurl.com/7mry4xl#Marker -> Subsection "Events".
    google.maps.event.addListener(source, 'dragend', redrawLine);
    google.maps.event.addListener(target, 'dragend', redrawLine);

});

function redrawLine() {
    var path = [ source.getPosition(), target.getPosition()];
    line.setPath(path)
}


// Send a query to the server with two co-ordinates
function queryServer() {
    var url = "http://localhost:8888/?"
        + source.getPosition().lat() + "," + source.getPosition().lng() + ","
        + target.getPosition().lat() + "," + target.getPosition().lng();
    $.ajax(url, { dataType: "jsonp" });
}