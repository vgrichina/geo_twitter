<html>
    <head>
        <title>Welcome to GeoTwitter!</title>
        <meta name="layout" content="main" />

        <script src="http://www.google.com/jsapi" type="text/javascript"></script>

        <script type="text/javascript">
            google.load("maps", "2.x");
            google.load("jquery", "1.3.1");
        
            google.setOnLoadCallback(function() {
                $(document).ready(function(){
                    var map = new GMap2(document.getElementById('map'));
                    var vinnitsa = new GLatLng(49.2325477, 28.4744695);
                    map.setCenter(vinnitsa, 8);
                    map.addControl(new GLargeMapControl());
                });
            });
        </script>
    </head>
    <body>
        <div class="form">
            <form action="" name="twitter">
                <p>
                    <label>twitter id:</label>
                    <input type="text" id="name" name="name" value=""/>
                </p>
                <p>
                    <label>password:</label>
                    <input type="password" id="pword" name="pword" value=""/>
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
