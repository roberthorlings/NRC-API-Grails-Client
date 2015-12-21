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

        def url = 'http://localhost:8080/nrc/api/openmhealth/v1/dataPoints/4'

        def nrcResponse = oauthService.putNrcResource( token, url, null, [ "Accept": "application/json" ] )

        // set output headers
        response.status = nrcResponse.code
        response.contentType = 'application/json;charset=UTF-8'

        render nrcResponse.body
    }
}
