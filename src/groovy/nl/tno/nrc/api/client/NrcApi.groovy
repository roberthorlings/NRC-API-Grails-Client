package nl.tno.nrc.api.client

import nl.tno.nrc.oauth.*

import org.scribe.extractors.AccessTokenExtractor
import org.scribe.model.*
import org.scribe.oauth.OAuthService
import org.scribe.utils.*

public class NrcApi extends org.scribe.builder.api.DefaultApi20
{
  private static final String AUTHORIZATION_URL = "http://localhost:8080/nrc/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s"
//  private static final String AUTHORIZATION_URL = "http://localhost:8080/oauth2-test/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=read"
  private static final String TOKEN_URL = "http://localhost:8080/nrc/oauth/token?grant_type=authorization_code"
//  private static final String TOKEN_URL = "http://localhost:8080/oauth2-test/oauth/token?grant_type=authorization_code"

  /**
   * Returns the URL that receives the access token requests.
   *
   * @return access token URL
   */
  @Override
  public String getAccessTokenEndpoint() {
    return TOKEN_URL
  }

  @Override
  public Verb getAccessTokenVerb()
  {
	return Verb.POST;
  }
  
  @Override
  public String getAuthorizationUrl(OAuthConfig config)
  {
    return String.format(AUTHORIZATION_URL, config.getApiKey(), config.getCallback(), config.getScope() );
  }
  
  /**
   * Returns the access token extractor.
   *
   * @return access token extractor
   */
  public AccessTokenExtractor getAccessTokenExtractor()
  {
	return new NrcApiAccessTokenExtractorImpl();
  }
  
  /**
   * {@inheritDoc}
   */
  public OAuthService createService(OAuthConfig config)
  {
	return new NrcApiOAuthServiceImpl(this, config);
  }
}