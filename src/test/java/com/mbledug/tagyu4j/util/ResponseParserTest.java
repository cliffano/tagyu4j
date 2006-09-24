package com.mbledug.tagyu4j.util;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;

import junit.framework.TestCase;

public class ResponseParserTest extends TestCase {

    private ResponseParserImpl mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseTagSuggestionsWithSuccessResponseXmlString() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(DataFixture.createTagSuggestionsResponseXmlString(30));
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testParseTagSuggestionsWithInvalidResponseXmlStringGivesTagyu4JException() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(DataFixture.INVALID_RESPONSE_XML_STRING);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testParseTagSuggestionsWithErrorResponseXmlStringGivesTagyu4JException() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(DataFixture.ERROR_RESPONSE_XML_STRING);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testParseRelatedTagsWithSuccessResponseXmlString() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags(DataFixture.createRelatedTagsResponseXmlString(20));
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testParseRelatedTagsWithInvalidResponseXmlStringGivesTagyu4JException() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags(DataFixture.INVALID_RESPONSE_XML_STRING);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testParseRelatedTagsWithErrorResponseXmlStringGivesTagyu4JException() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags(DataFixture.ERROR_RESPONSE_XML_STRING);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }
}
