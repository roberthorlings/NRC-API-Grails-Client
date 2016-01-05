package nrc.api.grails.client

import grails.converters.JSON
import groovy.json.*

class ConnectController {
    def oauthService
    def dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    def index() {

        []
    }

    def success() {

        []
    }

    def failure() {

        []
    }

    def list() {
        def token = session[oauthService.findSessionKeyForAccessToken('nrc')]

        if (!token) {
            redirect( controller: 'connect', action: 'index' )
            return
        }

        def url = 'http://localhost:8080/nrc/api/openmhealth/v1/dataPoints?' + params.collect { it }.join('&')

        def nrcResponse = oauthService.getNrcResource( token, url, null, [ "Accept": "application/json" ] )

        // set output headers
        response.status = nrcResponse.code
        response.contentType = 'application/json;charset=UTF-8'

        render nrcResponse.body
    }

    def create() {
        def token = session[oauthService.findSessionKeyForAccessToken('nrc')]

        if (!token) {
            redirect( controller: 'connect', action: 'index' )
            return
        }

        def url = 'http://localhost:8080/nrc/api/openmhealth/v1/dataPoints'

        def date = new Date().format( dateFormat, TimeZone.getTimeZone( "Europe/Amsterdam" ) )
        def source = params.source ?: "Manual"
        def value = params.float( 'value' ) ?: new java.util.Random().nextDouble() * 20 + 60
        def unit = params.unit ?: "kg"

        def data = [
                header: [
                        id: java.util.UUID.randomUUID().toString(),
                        creation_date_time: date,
                        acquisition_provenance: [
                            source_name: source,
                            source_creation_date_time:date,
                            modality:"sensed"
                        ],
                        schema_id: [
                                namespace: 'omh',
                                name: 'body-weight',
                                version: '1.0'
                        ]
                ],
                body: [
                        body_weight: [ unit: unit, value: value ]
                ]
        ]

        def nrcResponse = oauthService.postNrcResourceWithPayload( token, url, new JsonBuilder( data ).toPrettyString(), [ "Accept": "application/json", "Content-type": "application/json" ] )

        // set output headers
        response.status = nrcResponse.code
        response.contentType = 'application/json;charset=UTF-8'

        render nrcResponse.body
    }

    def show() {
        def token = session[oauthService.findSessionKeyForAccessToken('nrc')]

        if (!token) {
            redirect( controller: 'connect', action: 'index' )
            return
        }

        if( !params.id ) {
            response.contentType = 'application/json;charset=UTF-8'
            render "Specify ID in URL"
            return
        }

        def url = 'http://localhost:8080/nrc/api/openmhealth/v1/dataPoints/' + params.id

        def nrcResponse = oauthService.getNrcResource( token, url, null, [ "Accept": "application/json" ] )

        // set output headers
        response.status = nrcResponse.code
        response.contentType = 'application/json;charset=UTF-8'

        render nrcResponse.body
    }

    def delete() {
        def token = session[oauthService.findSessionKeyForAccessToken('nrc')]

        if (!token) {
            redirect( controller: 'connect', action: 'index' )
            return
        }

        if( !params.id ) {
            response.contentType = 'application/json;charset=UTF-8'
            render "Specify ID in URL"
            return
        }

        def url = 'http://localhost:8080/nrc/api/openmhealth/v1/dataPoints/' + params.id

        def nrcResponse = oauthService.deleteNrcResource( token, url, null, [ "Accept": "application/json" ] )

        // set output headers
        response.status = nrcResponse.code
        response.contentType = 'application/json;charset=UTF-8'

        render nrcResponse.body
    }
}
