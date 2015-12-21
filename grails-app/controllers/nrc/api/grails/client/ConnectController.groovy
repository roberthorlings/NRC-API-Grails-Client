package nrc.api.grails.client

import grails.converters.JSON

class ConnectController {
    def oauthService

    def index() {

        []
    }

    def success() {

        []
    }

    def failure() {

        []
    }

    def test() {
        def token = session.getAttribute('nrc:oasAccessToken')

        if (!token) {
            redirect( controller: 'connect', action: 'index' )
            return
        }

        def url = 'http://localhost:8080/nrc/api/openmhealth/testCall'

        def nrcResponse = oauthService.getNrcResource( token, url )

        def jsonResponse = JSON.parse( nrcResponse.body )


        // set output headers
        response.status = 200
        response.contentType = 'application/json;charset=UTF-8'

        render jsonResponse
    }
}
