package com.qoqoa.tagyu4j;

import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;
import com.qoqoa.tagyu4j.util.DataFixture;
import com.qoqoa.tagyu4j.util.ResponseParser;
import com.qoqoa.tagyu4j.util.ServiceManager;

public class Tagyu4JTest extends BaseTest {

    private Tagyu4J mTagyu4J;
    private ServiceManager mServiceManager;
    private ResponseParser mResponseParser;
    private DataFixture mDataFixture;

    protected void setUp() {
        mDataFixture = new DataFixture();
    }

    public void testGetTagSuggestionsWithTextViaLiveService() {
        mTagyu4J = new Tagyu4J();
        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_TEXT);
        assertTagSuggestionsResponse(response);
    }

    public void testGetTagSuggestionsWithUrlViaLiveService() {
        mTagyu4J = new Tagyu4J();
        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_URL);
        assertTagSuggestionsResponse(response);
    }

    public void testGetRelatedTagsViaLiveService() {
        mTagyu4J = new Tagyu4J();
        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        RelatedTagsResponse response = mTagyu4J.getRelatedTags("music");
        assertRelatedTagsResponse(response);
    }

    public void testGetTagSuggestionsWithTextViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockTagSuggestionsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_TEXT);
        assertTagSuggestionsResponse(response);
    }

    public void testGetTagSuggestionsWithUrlViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockTagSuggestionsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_URL);
        assertTagSuggestionsResponse(response);
    }

    public void testGetRelatedTagsViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG);
        assertRelatedTagsResponse(response);
    }

    public void testGetRelatedTagsWithProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        mTagyu4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT);
        RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG_URL);
        assertRelatedTagsResponse(response);
    }

    public void testGetRelatedTagsWithAuthenticatedProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        mTagyu4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT, DataFixture.DUMMY_PROXY_USERNAME, DataFixture.DUMMY_PROXY_PASSWORD);
        RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG_URL);
        assertRelatedTagsResponse(response);
    }
}
