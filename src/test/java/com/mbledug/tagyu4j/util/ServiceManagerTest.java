package com.mbledug.tagyu4j.util;

import junit.framework.TestCase;

import com.mbledug.tagyu4j.exception.Tagyu4JException;

public class ServiceManagerTest extends TestCase {

    private static final String INVALID_SERVICE_URL = "http://blahblahblah";
    private static final String VALID_SERVICE_URL_TAG_SUGGESTIONS = "http://tagyu.com/api/suggest/";
    private static final String VALID_SERVICE_URL_RELATED_TAGS = "http://tagyu.com/api/tag/css";
    private static final String INVALID_PROXY_HOST = "http://blahblahblah";
    private static final int DUMMY_PROXY_PORT = 8080;
    private static final String DUMMY_PROXY_USERNAME = "someusername";
    private static final String DUMMY_PROXY_PASSWORD = "somepassword";
    private static final String TAGYU_USERNAME = "tagyu4j";
    private static final String TAGYU_PASSWORD = "tagyu4j";

    private ServiceManager mServiceManager;

    protected void setUp() {
        mServiceManager = new ServiceManagerImpl();
    }

    public void testExecuteTagSuggestionsSuccess() {
        try {
            String response = mServiceManager.execute(VALID_SERVICE_URL_TAG_SUGGESTIONS, "tags+and+tagging");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteTagSuggestionsWithAuthenticationSuccess() {
        try {
            mServiceManager.setAuthentication(TAGYU_USERNAME, TAGYU_PASSWORD);
            String response = mServiceManager.execute(VALID_SERVICE_URL_TAG_SUGGESTIONS, "tags+and+tagging");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteRelatedTagsSuccess() {
        try {
            String response = mServiceManager.execute(VALID_SERVICE_URL_RELATED_TAGS, "css");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteFailureInvalidServiceUrl() {
        try {
            mServiceManager.execute(INVALID_SERVICE_URL, "css");
            fail("Test with invalid service url should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testExecuteFailureInvalidProxy() {
        try {
            mServiceManager.setProxy(INVALID_PROXY_HOST, DUMMY_PROXY_PORT);
            mServiceManager.execute(VALID_SERVICE_URL_RELATED_TAGS, "css");
            fail("Test with invalid proxy should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testExecuteFailureInvalidAuthenticatedProxy() {
        try {
            mServiceManager.setProxy(
                    INVALID_PROXY_HOST,
                    DUMMY_PROXY_PORT,
                    DUMMY_PROXY_USERNAME,
                    DUMMY_PROXY_PASSWORD);
            mServiceManager.execute(VALID_SERVICE_URL_RELATED_TAGS, "css");
            fail("Test with invalid authenticated proxy should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }
}
