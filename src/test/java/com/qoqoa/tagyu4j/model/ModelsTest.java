package com.qoqoa.tagyu4j.model;

import java.util.List;

import junit.framework.TestCase;

import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.Tag;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;
import com.qoqoa.tagyu4j.util.DataFixture;

public class ModelsTest extends TestCase {

    public void testCreateModels() {

        Tag tag = DataFixture.createTag();
        assertEquals(DataFixture.TAG_VALUE, tag.getValue());
        assertEquals(DataFixture.TAG_RELATION, tag.getRelation());
        assertEquals(DataFixture.TAG_URL, tag.getHref());

        List relatedTags = DataFixture.createTags(10);
        RelatedTagsResponse relatedTagsResponse = new RelatedTagsResponse(
                relatedTags, DataFixture.REQUEST_TAG);
        assertEquals(relatedTags, relatedTagsResponse.getRelatedTags());
        assertEquals(DataFixture.REQUEST_TAG, relatedTagsResponse.getRequestTag());

        List tagSuggestions = DataFixture.createTags(20);
        TagSuggestionsResponse tagSuggestionsResponse = new TagSuggestionsResponse(
                tagSuggestions, DataFixture.CATEGORY);
        assertEquals(tagSuggestions, tagSuggestionsResponse.getSuggestedTags());
        assertEquals(DataFixture.CATEGORY, tagSuggestionsResponse.getCategory());
    }
}
