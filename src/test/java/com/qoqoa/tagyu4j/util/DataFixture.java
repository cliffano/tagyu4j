package com.qoqoa.tagyu4j.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URIException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.Tag;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;
import com.qoqoa.tagyu4j.util.ResponseParser;
import com.qoqoa.tagyu4j.util.ServiceManager;

public class DataFixture extends MockObjectTestCase {

    public static final String VALID_SERVICE_URL_TAG_SUGGESTIONS = "http://tagyu.com/api/suggest/";
    public static final String VALID_SERVICE_URL_RELATED_TAGS = "http://tagyu.com/api/tag/css";
    public static final String INVALID_SERVICE_URL = "http://#@(*$&(*@&$&@#$#@(&";
    public static final String TAGYU_USERNAME = "tagyu4j";
    public static final String TAGYU_PASSWORD = "tagyu4j";
    public static final String DUMMY_PROXY_HOST = "http://@#&$(@$&@$&#@&";
    public static final int DUMMY_PROXY_PORT = 8080;
    public static final String DUMMY_PROXY_USERNAME = "Some Username";
    public static final String DUMMY_PROXY_PASSWORD = "Some Password";
    public static final String TAG_VALUE = "tagging";
    public static final String TAG_RELATION = "related";
    public static final String TAG_URL = "http://tagyu.com/api/tag/tagging";
    public static final String REQUEST_TAG = "css";
    public static final String REQUEST_TAG_TEXT = "Imagine all the people - John Lennon.";
    public static final String REQUEST_TAG_URL = "www.tagyu.com";
    public static final String CATEGORY = "Technology";
    public static final String INVALID_RESPONSE_XML_STRING = "<foobar>(&#($&(*&#@($&@#</foobar>";
    public static final String ERROR_RESPONSE_XML_STRING = "<error>You are requesting too often</error>";

    public static Tag createTag() {
        return new Tag(TAG_VALUE, TAG_RELATION, TAG_URL);
    }

    public static List createTags(final int numOfTags) {
        List tags = new ArrayList();
        for (int i = 0; i < numOfTags; i++) {
            tags.add(createTag());
        }
        return tags;
    }

    public static RelatedTagsResponse createRelatedTagsResponse() {
        return new RelatedTagsResponse(createTags(20), REQUEST_TAG);
    }

    public static TagSuggestionsResponse createTagSuggestionsResponse() {
        return new TagSuggestionsResponse(createTags(30), CATEGORY);
    }

    public static String createTagXmlString(final String tag) {
        return "<tag rel=\"related\" href=\"http://tagyu.com/api/tag/" + tag + "\">" + tag + "</tag>";
    }

    public static String createTagSuggestionsResponseXmlString(final int numOfTags) {
        StringBuffer tagSuggestionsResponseXmlString = new StringBuffer().append("<suggestions>");
        for (int i = 0; i < numOfTags; i++) {
            tagSuggestionsResponseXmlString.append(createTagXmlString("sometagsuggestion" + i));
        }
        tagSuggestionsResponseXmlString.append("<category>Technology</category></suggestions>");
        return tagSuggestionsResponseXmlString.toString();
    }

    public static String createRelatedTagsResponseXmlString(final int numOfTags) {
        StringBuffer relatedTagsResponseXmlString = new StringBuffer().append("<related for=\"css\">");
        for (int i = 0; i < numOfTags; i++) {
            relatedTagsResponseXmlString.append(createTagXmlString("somerelatedtag" + i));
        }
        relatedTagsResponseXmlString.append("</related>");
        return relatedTagsResponseXmlString.toString();
    }

    public final HttpClient createMockHttpClient(final boolean withProxy, final Exception executeMethodException) {
        Mock mockHttpClient = mock(HttpClient.class);
        if (executeMethodException == null) {
            mockHttpClient.expects(once()).method("executeMethod").will(returnValue(1));
        } else {
            mockHttpClient.expects(once()).method("executeMethod").will(throwException(executeMethodException));
        }
        if (withProxy) {
            mockHttpClient.expects(once()).method("getHostConfiguration").will(returnValue(new HostConfiguration()));
            mockHttpClient.expects(once()).method("getState").will(returnValue(new HttpState()));
        }
        return (HttpClient) mockHttpClient.proxy();
    }

    public final HttpMethod createMockHttpMethod(final String url, final boolean withAuthentication) {
        Mock mockHttpMethod = mock(HttpMethod.class);
        if (VALID_SERVICE_URL_TAG_SUGGESTIONS.equals(url)) {
            mockHttpMethod.expects(once()).method("getResponseBodyAsString").will(returnValue(createTagSuggestionsResponseXmlString(10)));
            mockHttpMethod.expects(once()).method("setURI");
        } else if (VALID_SERVICE_URL_RELATED_TAGS.equals(url)) {
            mockHttpMethod.expects(once()).method("getResponseBodyAsString").will(returnValue(createRelatedTagsResponseXmlString(10)));
            mockHttpMethod.expects(once()).method("setURI");
        } else {
            mockHttpMethod.expects(once()).method("getResponseBodyAsString").will(throwException(new IOException("Some Dummy Error")));
            mockHttpMethod.expects(once()).method("setURI").will(throwException(new URIException()));
        }
        mockHttpMethod.expects(once()).method("getURI");
        mockHttpMethod.expects(once()).method("releaseConnection");
        mockHttpMethod.expects(once()).method("setFollowRedirects");
        mockHttpMethod.expects(once()).method("setQueryString");
        if (withAuthentication) {
            mockHttpMethod.expects(once()).method("setRequestHeader");
        }
        return (HttpMethod) mockHttpMethod.proxy();
    }

    public final ServiceManager createMockServiceManager(final boolean withProxy) {
        Mock mockServiceManager = mock(ServiceManager.class);
        mockServiceManager.expects(once()).method("execute");
        if (withProxy) {
            mockServiceManager.expects(once()).method("setProxy");
        }
        return (ServiceManager) mockServiceManager.proxy();
    }

    public final ResponseParser createMockTagSuggestionsResponseParser() {
        Mock mockResponseParser = mock(ResponseParser.class);
        mockResponseParser.expects(once()).method("parseTagSuggestions").will(returnValue(createTagSuggestionsResponse()));
        return (ResponseParser) mockResponseParser.proxy();
    }

    public final ResponseParser createMockRelatedTagsResponseParser() {
        Mock mockResponseParser = mock(ResponseParser.class);
        mockResponseParser.expects(once()).method("parseRelatedTags").will(returnValue(createRelatedTagsResponse()));
        return (ResponseParser) mockResponseParser.proxy();
    }
}
