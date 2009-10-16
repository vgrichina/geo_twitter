import grails.converters.*

class TwitterController {
    // Google Maps API key
    static def API_KEY = "ABQIAAAAbsJwNuAmOaJE18sONZ3oXxS6PDgnYWzBubbuuLvpkz8oZ7SkXhS0ia-cXa80LYOn9MIu-oBLdGR2AQ"

    // TwitterService instance will be injected into this variable by Spring
    def twitterService

    def friendsJson = {
        // Get friends of given user
        def friends = getFriends(params.name)
        // Render friends list as JSON
        render(friends as JSON)
    }

    private def getFriends(String userName) {
        def friends = twitterService.getFriends(params.name)
        
        // Return only the needed fields for each user and retrieve coordinates for location
        friends.collect { it ->
            [
                screenName: it.screenName,
                name: it.name,
                pictureUrl: it.profileImageUrl as String,
                bio: it.description,
                status: it.status?.text,
                coords: getCoordsFromLocation(it.location)
            ]    
        }
    }
    
    /**
     * This method gets coordinates on map for given location string.
     */
    private def getCoordsFromLocation(String location) {
        if (location) {
            if (location.contains("iPhone:")) {
                // There can be coords specified in location
                // like iPhone: 39.035248,-77.138687
                location = location.replace("iPhone: ", "")
                def parts = location.split(",")
                return [latitude: parts[0], longitude: parts[1]]
            } else {
                // Encode location as URL
                def encodedLocation = URLEncoder.encode(location)
                // Call web service by retrieving URL content
                def response = 
                    "http://maps.google.com/maps/geo?q=${encodedLocation}&output=xml&key=${API_KEY}".toURL().getText()
                // Parse response XML
                def root = new XmlSlurper().parseText(response)
                if (root.Response.Placemark.size() == 1) {
                    def coords = root.Response.Placemark.Point.coordinates.text()
                    def parts = coords.split(",")
                    if (parts.size() > 1) {
                        return [latitude: parts[1] as Double, longitude: parts[0] as Double]
                    }
                }
            }
        }

        // No coordinates are determined
        return null
    }
}
