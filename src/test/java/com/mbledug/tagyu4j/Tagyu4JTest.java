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

/**
 * Tests the available methods.
 * @author Cliffano Subagio
 */
public class Tagyu4JTest extends TestCase {

    private static final String INVALID_PROXY_HOST = "http://blahblahblah";
    private static final int DUMMY_PROXY_PORT = 8080;
    private static final String DUMMY_PROXY_USERNAME = "someusername";
    private static final String DUMMY_PROXY_PASSWORD = "somepassword";
    private static final String TAGYU_USERNAME = "tagyu4j";
    private static final String TAGYU_PASSWORD = "tagyu4j";

    private Tagyu4J mTagyu4J;

    protected void setUp() {
        // set authentication to avoid 1 request / minute restriction
        mTagyu4J = new Tagyu4J(TAGYU_USERNAME, TAGYU_PASSWORD);
    }

    public void testGetTagSuggestionsTextSuccess() {
        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions("Imagine all the people - John Lennon.");
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetTagSuggestionsUrlSuccess() {

        try {
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions("www.tagyu.com");
            assertTagSuggestionsResponse(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testGetRelatedTagsSuccess() {

        try {
            RelatedTagsResponse response = mTagyu4J.getRelatedTags("music");
            assertRelatedTagsResponse(response);
        } catch (Tagyu4JException te) {
            te.printStackTrace();
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testProxyFailure() {

        try {
            mTagyu4J.setProxy(INVALID_PROXY_HOST, DUMMY_PROXY_PORT);
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions("www.tagyu.com");
            fail("Test with invalid proxy should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testAuthenticatedProxyFailure() {

        try {
            mTagyu4J.setProxy(
                    INVALID_PROXY_HOST, DUMMY_PROXY_PORT,
                    DUMMY_PROXY_USERNAME, DUMMY_PROXY_PASSWORD);
            TagSuggestionsResponse response = mTagyu4J.getTagSuggestions("www.tagyu.com");
            fail("Test with invalid authenticated proxy should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
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
