<%-- 
    Document   : newjsp
    Created on : 19/05/2020, 07:19:13 PM
    Author     : helme
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head> 
        <meta charset="UTF-8">
        <meta name="viewport" content="initial-scale=1.0">
        <title>Maps JavaScript API</title>
        <style> 
            #map {
                height: 100%;
            }
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
            }
        </style> 
    </head>  
    <body>
        <div id ="map"> </div> 
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCKiIqCdZGrVxx06LSbe7uG3zXOq1Cz5k&callback=initMap" async defer></script>
        <script>
            var map;
            function initMap() {
                map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: -12.153667, lng: -77.013981},
                    zoom: 10
                });
                var marker = new google.maps.Marker({
                    position: {lat: -12.153667, lng: -77.013981},
                    map: map,
                    title: 'Casa'
                });
            }
        </script>
    </body> 
</html>
