import grails.converters.JSON

class TwitterController {
    // TwitterService instance will be injected into this variable by Spring
    def twitterService
    // LocationService instance used to lookup location coordinates
    def locationService

    def friendsJson = {
        // Get friends of given user
        def friends = getFriends(params.name)
        // Render friends list as JSON
        render(friends as JSON)
    }

    private def getFriends(String userName) {
        def friends = twitterService.getFriends(params.name)
        
        // Return only the needed fields for each user and retrieve coordinates for location
        // Results are also grouped by the coords
        friends.collect { it ->
            [
                screenName: it.screenName,
                name: it.name,
                pictureUrl: it.profileImageUrl as String,
                bio: it.description,
                status: it.status?.text,
                coords: locationService.getCoordsFromLocation(it.location)
            ]    
        }.findAll { it.coords != null }.groupBy { it.coords }.collect { it.value }
    }
}
