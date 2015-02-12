package nl.tno.nrc.api.client

import java.util.regex.*;

import org.scribe.exceptions.*;
import org.scribe.model.*;
import org.scribe.utils.*;
import org.scribe.extractors.AccessTokenExtractor;
import grails.converters.JSON

/**
 * Default implementation of {@AccessTokenExtractor}. Conforms to OAuth 2.0
 *
 */
public class NrcApiAccessTokenExtractorImpl implements AccessTokenExtractor
{
  private static final String EMPTY_SECRET = "";

  /**
   * {@inheritDoc}
   */
  public Token extract(String response)
  {
	Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
	
	try {
		def json = JSON.parse( response )
		String token = json.access_token
		
		if( !token )
			throw new Exception( "No token found in JSON body" )
		
		return new Token(token, EMPTY_SECRET, response);
	} catch( Exception e ) {
		throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", e);
	}
  }
}