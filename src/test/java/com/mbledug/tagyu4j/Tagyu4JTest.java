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
package com.mbledug.tagyu4j;

import java.util.Iterator;

import junit.framework.TestCase;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.Tag;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;
import com.mbledug.tagyu4j.util.DataFixture;
import com.mbledug.tagyu4j.util.ResponseParser;
import com.mbledug.tagyu4j.util.ServiceManager;

/**
 * Tests the available methods.
 * @author Cliffano Subagio
 */
public class Tagyu4JTest extends TestCase {

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
    private void assertTagSuggestionsResponse(TagSuggestionsResponse response) {
        assertNotNull(response.getCategory());
        assertNotNull(response.getSuggestedTags());
        for (Iterator it = response.getSuggestedTags().iterator(); it.hasNext();) {
            Tag tag = (Tag)it.next();
            assertNotNull(tag.getValue());
            assertNotNull(tag.getRelation());
            assertNotNull(tag.getHref());
        }
    }

    private void assertRelatedTagsResponse(RelatedTagsResponse response) {
        assertNotNull(response.getRequestTag());
        assertNotNull(response.getRelatedTags());
        for (Iterator it = response.getRelatedTags().iterator(); it.hasNext();) {
            Tag tag = (Tag)it.next();
            assertNotNull(tag.getValue());
            assertNotNull(tag.getRelation());
            assertNotNull(tag.getHref());
        }
    }
}
