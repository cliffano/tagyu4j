/**
 * Copyright (c) 2005, Cliffano Subagio
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of Mbledug nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mbledug.tagyu4j.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.mbledug.tagyu4j.exception.Tagyu4JException;

/**
 * Representation of a method call to the Tagyu service.
 * @author Cliffano Subagio
 */
public class ServiceManagerImpl implements ServiceManager {

    /**
     * The client used to send request and receive response.
     */
    private HttpClient mHttpClient;

    /**
     * Method used to make the connection to Tagyu service.
     */
    private HttpMethod mHttpMethod;

    /**
     * The value for authentication header.
     */
    private String mAuthenticationHeaderValue;

    /**
     * Create an instance of ServiceManager.
     */
    public ServiceManagerImpl() {
        mHttpClient = new HttpClient();
        mHttpMethod = new GetMethod();
    }

    /**
     * Create a ServiceManager instance with specified HttpClient and
     * HttpMethod.
     * @param httpClient the http client
     * @param httpMethod the http method
     */
    public ServiceManagerImpl(
            final HttpClient httpClient,
            final HttpMethod httpMethod) {
        mHttpClient = httpClient;
        mHttpMethod = httpMethod;
    }

    /**
     * Sets the Tagyu authentication details username and password.
     * @param username the Tagyu username
     * @param password the Tagyu password
     */
    public final void setAuthentication(
            final String username,
            final String password) {

        // encode authentication header value in the format of
        // username:password, using base 64 encoding.
        Base64 encoder = new Base64();
        String headerValue = username + ":" + password;
        String base64Authentication = new String(
                encoder.encode(headerValue.getBytes()));
        mAuthenticationHeaderValue = "Basic " + base64Authentication;
    }

    /**
     * Sets proxy details to HttpClient instance.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     */
    public final void setProxy(final String proxyHost, final int proxyPort) {
        mHttpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
    }

    /**
     * Sets authenticated proxy details to HttpClient instance.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     * @param proxyUsername proxy username
     * @param proxyPassword proxy password
     */
    public final void setProxy(
            final String proxyHost,
            final int proxyPort,
            final String proxyUsername,
            final String proxyPassword) {
        setProxy(proxyHost, proxyPort);
        mHttpClient.getState().setProxyCredentials(
                new AuthScope(proxyHost, proxyPort, null),
                new UsernamePasswordCredentials(
                        proxyUsername,
                        proxyPassword));
    }

    /**
     * Create a URL connection and retrieve the response String.
     * @param url the base url which the request will be added to
     * @param request the request string to be passed on the url
     * @return the response String
     * @throws Tagyu4JException when there's a problem with retrieving the
     *             response from Tagyu service or encoding the url
     */
    public final String execute(final String url, final String request)
            throws Tagyu4JException {

        String encodedUrlWithRequest = encodeUrlWithRequest(url, request);

        try {
            mHttpMethod.setURI(new URI(encodedUrlWithRequest, true));
        } catch (URIException urie) {
            throw new Tagyu4JException("Unable to get xml response string "
                    + "due to invalid url: " + url, urie);
        }
        mHttpMethod.setFollowRedirects(true);

        // authenticate by setting http basic authentication header
        if (mAuthenticationHeaderValue != null) {
            mHttpMethod.setRequestHeader(
                    "Authorization", mAuthenticationHeaderValue);
        }

        String result = null;

        try {
            mHttpClient.executeMethod(mHttpMethod);
            result = mHttpMethod.getResponseBodyAsString();
        } catch (HttpException he) {
            throw new Tagyu4JException(
                    "Unable to get xml response string",
                    he);
        } catch (IOException ioe) {
            throw new Tagyu4JException(
                    "Unable to get xml response string",
                    ioe);
        } finally {
            mHttpMethod.releaseConnection();
        }
        return result;
    }

    /**
     * Append request to url and encode it.
     * @param url the url of the service
     * @param request the request string to be passed on the url
     * @return the encoded url with the request appended to it
     * @throws Tagyu4JException when there's a problem with
     *             encoding the url with request
     */
    private String encodeUrlWithRequest(
            final String url,
            final String request)
            throws Tagyu4JException {

        String encodedurlWithRequest = url;

        // constructs url with request
        if (request != null) {
            try {
                encodedurlWithRequest += URLEncoder.encode(request, "UTF-8");
            } catch (UnsupportedEncodingException uee) {
                throw new Tagyu4JException(
                        "Unable to encode request",
                        uee);
            }
        }

        return encodedurlWithRequest;
    }
}
