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
                // Loop through friends list grouped by locations and add markers to map
                $.each(data, function (i, friendsList) {
                    // Add marker to map
                    var marker = new GMarker(new GLatLng(friendsList[0].coords.latitude, friendsList[0].coords.longitude));
                    map.addOverlay(marker);
                    // Construct popup HTML
                    var popup = '<ul class="friendsList">'
                    $.each(friendsList, function (i, item) {
                        popup += 
                        '<li>' + 
                            '<div class="friendInfo">' + 
                                '<img src="' + item.pictureUrl + '">' + 
                                '<div class="name">' + item.name + ' (' + item.screenName + ') </div>' + 
                                '<div class="bio">' + item.bio + '</div>' +
                            '</div>' +
                            '<div class="message">' + item.status + '</div>' +
                        '</li>';
                    });
                    popup += "</ul>";
                    // Add popup handler
                    GEvent.addListener(marker, "click", function() {
                        marker.openInfoWindowHtml(popup);
                    });
                });
            });
            // Indicate that form should not actually be submitted
            return false;
        });
    });
});
