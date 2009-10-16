<html>
    <head>
        <title>Welcome to GeoTwitter!</title>
        <meta name="layout" content="main" />

        <script src="http://www.google.com/jsapi?key=${grailsApplication.config.geo_twitter.googleMapsKey}" type="text/javascript"></script>

        <script type="text/javascript">
            google.load("maps", "2.x");
            google.load("jquery", "1.3.1");
        
            google.setOnLoadCallback(function() {
                $(document).ready(function() {
                    // Create and configure Google Map control
                    var map = new GMap2(document.getElementById("map"));
                    var vinnitsa = new GLatLng(49.2325477, 28.4744695);
                    map.setCenter(vinnitsa, 4);
                    map.addControl(new GLargeMapControl());
                    // Add form submit handler
                    var form = $("#twitter");
                    form.submit(function() {
                        $.getJSON(form.attr("action") + "?" + form.serialize(), function (data) {
                            // Clear all markers
                            map.clearOverlays();
                            // Loop through friends list and add markers to map
                            $.each(data, function (i, item) {
                                if (item.coords) {
                                    var marker = new GMarker(new GLatLng(item.coords.latitude, item.coords.longitude));
                                    map.addOverlay(marker);
                                    var popup = '<img style="width: 48px; height:48px;" src="' + item.pictureUrl + '">' + 
                                        item.name + ' (' + item.screenName + ') <br>' + 
                                        item.bio + '<br>' + item.status;
                                    GEvent.addListener(marker, "click", function() {
                                        marker.openInfoWindowHtml(popup);
                                    });
                                }
                            });
                        });
                        // Indicate that form should not actually be submitted
                        return false;
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="form">
            <form action="${createLink(controller: 'twitter', action: 'friendsJson')}" id="twitter">
                <p>
                    <label>twitter id:</label>
                    <input type="text" id="name" name="name" value=""/>
                </p>
                <p class="submit">
                    <input type="submit" value="Map my friends!">
                </p>
            </form>
        </div>
        <div id="map">
        </div>
    </body>
</html>
