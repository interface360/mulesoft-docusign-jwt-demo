package io.api360.docu;

import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.auth.OAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Raymond M Arnado - Senior Solution Architect - MuleSoft a Salesforce
 *         Company
 *
 */

public class DocuSignTokenXjja {

	private String accessToken;

	public DocuSignTokenXjja(String _authUri, String _clientId, String _userId, String _rsaKey, long _expiration) {
		String accessToken = "";
		List<String> SCOPES = new ArrayList<String>(Arrays.asList(OAuth.Scope_SIGNATURE, OAuth.Scope_IMPERSONATION));
		 this.accessToken = accessToken;
	        try {
		        ApiClient api = new ApiClient();
		        api.setOAuthBasePath(_authUri);
		        OAuth.OAuthToken oAuthToken = api.requestJWTUserToken(_clientId, _userId, SCOPES, ("-----BEGIN PRIVATE KEY-----\r\n" + _rsaKey + "\r\n-----END PRIVATE KEY-----").getBytes(), _expiration);
		        if (oAuthToken != null) {
		        	accessToken = oAuthToken.getAccessToken();
		        }
	        }
			catch (Exception e) {
				if (e instanceof ApiException) {
					ApiException api = (ApiException) e;
					String body = api.getResponseBody();
					if (body != null && body.contains("consent_required")) {
						accessToken = "CONSENT_REQUIRED";
					}
				}else
					accessToken = "INVALID_REQUEST";
			}        

			this.accessToken =accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
