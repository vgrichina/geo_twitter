import org.codehaus.groovy.grails.commons.ConfigurationHolder

class LocationService {
    boolean transactional = true

    // Google Maps API key
    def mapsApiKey = ConfigurationHolder.config.geo_twitter.googleMapsKey 

    def coordsCache = [:]

    /**
     * This method gets coordinates on map for given location string.
     */
    def getCoordsFromLocation(String location) {
        if (location) {
            if (location.contains("iPhone:")) {
                // There can be coords specified in location
                // like iPhone: 39.035248,-77.138687
                location = location.replace("iPhone: ", "")
                def parts = location.split(",")
                return [latitude: parts[0], longitude: parts[1]]
            } else {
                synchronized (coordsCache) {
                    // Return location coords if it already exists in cache
                    if (coordsCache[location]) {
                        return coordsCache[location]
                    }
                }

                // Encode location as URL
                def encodedLocation = URLEncoder.encode(location)
                // Call web service by retrieving URL content
                def response = 
                    "http://maps.google.com/maps/geo?q=${encodedLocation}&output=xml&key=${mapsApiKey}".toURL().getText()
                // Parse response XML
                def root = new XmlSlurper().parseText(response)
                if (root.Response.Placemark.size() == 1) {
                    def coords = root.Response.Placemark.Point.coordinates.text()
                    def parts = coords.split(",")
                    if (parts.size() > 1) {
                        def coordsPair = [latitude: parts[1] as Double, longitude: parts[0] as Double]
                        
                        // Store coords in cache
                        synchronized (coordsCache) {
                            coordsCache[location] = coordsPair
                        }
                        
                        return coords
                    }
                }
            }
        }

        // No coordinates are determined
        return null
    }

}
