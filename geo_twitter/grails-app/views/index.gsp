<html>
    <head>
        <title>Welcome to GeoTwitter!</title>
        <meta name="layout" content="main" />

        <script src="http://www.google.com/jsapi?key=${grailsApplication.config.geo_twitter.googleMapsKey}" type="text/javascript"></script>
        <g:javascript src="geo_twitter.js" />
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
