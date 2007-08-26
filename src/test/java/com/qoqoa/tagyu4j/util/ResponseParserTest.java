package com.qoqoa.tagyu4j.util;

import com.qoqoa.tagyu4j.BaseTest;
import com.qoqoa.tagyu4j.exception.Tagyu4JException;
import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;

public class ResponseParserTest extends BaseTest {

    private ResponseParserImpl mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseTagSuggestionsWithSuccessResponseXmlString() {
        TagSuggestionsResponse response = mParser.parseTagSuggestions(DataFixture.createTagSuggestionsResponseXmlString(30));
        assertTagSuggestionsResponse(response);
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
        RelatedTagsResponse response = mParser.parseRelatedTags(DataFixture.createRelatedTagsResponseXmlString(20));
        assertRelatedTagsResponse(response);
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
