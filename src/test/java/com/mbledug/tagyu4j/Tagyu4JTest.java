package com.mbledug.tagyu4j;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;
import com.mbledug.tagyu4j.util.DataFixture;
import com.mbledug.tagyu4j.util.ResponseParser;
import com.mbledug.tagyu4j.util.ServiceManager;

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
        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_TEXT);
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetTagSuggestionsWithUrlViaLiveService() {
        mTagyu4J = new Tagyu4J();
        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_URL);
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetRelatedTagsViaLiveService() {
        mTagyu4J = new Tagyu4J();
        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        try {
            RelatedTagsResponse response = mTagyu4J.getRelatedTags("music");
            assertRelatedTagsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetTagSuggestionsWithTextViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockTagSuggestionsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_TEXT);
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetTagSuggestionsWithUrlViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockTagSuggestionsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_URL);
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetRelatedTagsViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(false);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        try {
            RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG);
            assertRelatedTagsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetRelatedTagsWithProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        try {
            mTagyu4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT);
            RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG_URL);
            assertRelatedTagsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetRelatedTagsWithAuthenticatedProxyViaMockService() {
        mServiceManager = mDataFixture.createMockServiceManager(true);
        mResponseParser = mDataFixture.createMockRelatedTagsResponseParser();
        mTagyu4J = new Tagyu4J(mServiceManager, mResponseParser);
        try {
            mTagyu4J.setProxy(DataFixture.DUMMY_PROXY_HOST, DataFixture.DUMMY_PROXY_PORT, DataFixture.DUMMY_PROXY_USERNAME, DataFixture.DUMMY_PROXY_PASSWORD);
            RelatedTagsResponse response = mTagyu4J.getRelatedTags(DataFixture.REQUEST_TAG_URL);
            assertRelatedTagsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }
}
