package nl.tno.nrc.api.client

import org.scribe.builder.api.DefaultApi20
import org.scribe.model.OAuthConfig
import org.scribe.model.OAuthConstants
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuth20ServiceImpl

class NrcApiOAuthServiceImpl extends OAuth20ServiceImpl {
	
	protected final DefaultApi20 api;
	protected final OAuthConfig config;
	
	/**
	 * Default constructor
	 *
	 * @param api OAuth2.0 api information
	 * @param config OAuth 2.0 configuration param object
	 */
	public NrcApiOAuthServiceImpl(DefaultApi20 api, OAuthConfig config)
	{
		super( api, config )
		
	    this.api = api;
	    this.config = config;
	}
	
	/**
	 * Retrieves the access token from the oAuth provider.
	 * This method is overwritten, in order to send the parameters properly
	 * when using a POST request
	 */
	@Override
	public Token getAccessToken(Token requestToken, Verifier verifier)
	{
		OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());

		def parameters = [
			(OAuthConstants.CLIENT_ID): 		config.getApiKey(),
			(OAuthConstants.CLIENT_SECRET): 	config.getApiSecret(),
			(OAuthConstants.CODE): 				verifier.getValue(),
			(OAuthConstants.REDIRECT_URI): 		config.getCallback()
		]

//		if(config.hasScope())
//			parameters[ OAuthConstants.SCOPE ] = config.getScope()

		parameters.each { paramName, paramValue ->
			if( api.getAccessTokenVerb() == Verb.GET )
				request.addQuerystringParameter( paramName, paramValue )
			else
				request.addBodyParameter( paramName, paramValue )
		}

        println parameters

		Response response = request.send();

        println response.getBody()

		return api.getAccessTokenExtractor().extract(response.getBody());
	}
  
	/**
	 * {@inheritDoc}
	 */
	public String getAuthorizationUrl(Token requestToken)
	{
	  return api.getAuthorizationUrl(config);
	}
  
	

}
