package com.mbledug.tagyu4j.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

import com.mbledug.tagyu4j.exception.Tagyu4JException;

public class ServiceManagerTest extends TestCase {

    private ServiceManager mServiceManager;
    private DataFixture mDataFixture;
    private HttpClient mMockHttpClient;
    private HttpMethod mMockHttpMethod;

    protected void setUp() {
        mDataFixture = new DataFixture();
    }

    public void testExecuteTagSuggestionsSuccess() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, "tags+and+tagging");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteTagSuggestionsWithAuthenticationSuccess() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, true);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        mServiceManager.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, "tags+and+tagging");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteRelatedTagsSuccess() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, "css");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteRelatedTagsWithAuthenticationSuccess() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_TAG_SUGGESTIONS, true);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        mServiceManager.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
        try {
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, "css");
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testExecuteWithInvalidServiceUrlGivesTagyu4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.INVALID_SERVICE_URL, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.INVALID_SERVICE_URL, DataFixture.REQUEST_TAG);
            fail("Test with invalid service url should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testExecuteFailureWithHttpClientThrowingHttpExceptionGivesTagyu4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, new HttpException());
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, DataFixture.REQUEST_TAG);
            fail("Test with http client throwing exception should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testExecuteFailureWithHttpClientThrowingIoExceptionGivesTagyu4JException() {
        mMockHttpClient = mDataFixture.createMockHttpClient(false, new IOException());
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.execute(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, DataFixture.REQUEST_TAG);
            fail("Test with http client throwing exception should have failed at this point.");
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testExecuteSuccessWithProxy() {
        mMockHttpClient = mDataFixture.createMockHttpClient(true, null);
        mMockHttpMethod = mDataFixture.createMockHttpMethod(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, false);
        mServiceManager = new ServiceManagerImpl(mMockHttpClient, mMockHttpMethod);
        try {
            mServiceManager.setProxy(
                    DataFixture.DUMMY_PROXY_HOST,
                    DataFixture.DUMMY_PROXY_PORT,
                    DataFixture.DUMMY_PROXY_USERNAME,
                    DataFixture.DUMMY_PROXY_PASSWORD);
            String response = mServiceManager.execute(DataFixture.VALID_SERVICE_URL_RELATED_TAGS, DataFixture.REQUEST_TAG);
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }
}
