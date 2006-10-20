/**
 * Copyright (c) 2005-2006, Cliffano Subagio
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
package com.mbledug.tagyu4j;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;
import com.mbledug.tagyu4j.util.ResponseParser;
import com.mbledug.tagyu4j.util.ResponseParserImpl;
import com.mbledug.tagyu4j.util.ServiceManager;
import com.mbledug.tagyu4j.util.ServiceManagerImpl;

/**
 * Java API for interacting with Tagyu Service APIs.
 * More information at http://www.tagyu.com/tools/rest .
 * <p>
 * Usage: simply create an instance of Tagyu4J. For example:
 *   <pre>
 *     Tagyu4J tagyu4j = new Tagyu4J();
 *     TagSuggestionsResponse tagSuggestionsResponse =
 *             tagyu4j.getTagSuggestions("tags and tagging");
 *     RelatedTagsResponse relatedTagsResponse =
 *             tagyu4j.getRelatedTags("css");
 *   </pre>
 * </p>
 * @author Cliffano Subagio
 */
public class Tagyu4J {

    /**
     * The REST API service end point.
     */
    private static final String SERVICE_END_POINT =
            "http://www.tagyu.com/api/";
    /**
     * The method to get tag suggestions.
     */
    private static final String METHOD_GET_TAG_SUGGESTIONS = "suggest/";

    /**
     * The method to get related tags.
     */
    private static final String METHOD_GET_RELATED_TAGS = "tag/";

    /**
     * The service call used to execute the methods.
     */
    private ServiceManager mServiceManager;
    /**
     * The parser used to parse the XML response.
     */
    private ResponseParser mResponseParser;

    /**
     * Create an instance of Tagyu4J.
     */
    public Tagyu4J() {
        mServiceManager = new ServiceManagerImpl();
        mResponseParser = new ResponseParserImpl();
    }

    /**
     * Create an instance of Tagyu4J with specified ServiceManager and
     * ResponseParser.
     * @param serviceManager the service manager
     * @param responseParser the response parser
     */
    public Tagyu4J(
            final ServiceManager serviceManager,
            final ResponseParser responseParser) {
        mServiceManager = serviceManager;
        mResponseParser = responseParser;
    }

    /**
     * Sets authentication using Tagyu username and password.
     * @param username Tagyu username
     * @param password Tagyu password
     */
    public final void setAuthentication(
            final String username,
            final String password) {
        mServiceManager.setAuthentication(username, password);
    }

    /**
     * Sets proxy details.
     * @param proxyHost proxy host name
     * @param proxyPort proxy port number
     */
    public final void setProxy(final String proxyHost, final int proxyPort) {
        mServiceManager.setProxy(proxyHost, proxyPort);
    }

    /**
     * Sets authenticated proxy details.
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
        mServiceManager.setProxy(
                proxyHost, proxyPort, proxyUsername, proxyPassword);
    }

    /**
     * Retrieve tag suggestions.
     * @param request text or a URL for which you would like tag suggestions
     * @return response for tag suggestions
     * @throws Tagyu4JException when there's a problem with
     *             retrieving the response.
     */
    public final TagSuggestionsResponse getTagSuggestions(
            final String request)
            throws Tagyu4JException {

        String url = SERVICE_END_POINT + METHOD_GET_TAG_SUGGESTIONS;
        String responseString = mServiceManager.execute(url, request);
        return mResponseParser.parseTagSuggestions(responseString);
    }

    /**
     * Retrieve related tags.
     * @param request a tag for which you want to find related tags of
     * @return response for related tags
     * @throws Tagyu4JException when there's a problem with
     *             retrieving the response.
     */
    public final RelatedTagsResponse getRelatedTags(
            final String request)
            throws Tagyu4JException {

        String url = SERVICE_END_POINT + METHOD_GET_RELATED_TAGS;
        String responseString = mServiceManager.execute(url, request);
        return mResponseParser.parseRelatedTags(responseString);
    }
}
