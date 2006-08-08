package com.mbledug.tagyu4j.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ModelsTest extends TestCase {

    private static final String TAG_VALUE = "tagging";
    private static final String TAG_RELATION = "related";
    private static final String TAG_URL = "http://tagyu.com/api/tag/tagging";
    private static final String REQUEST_TAG = "css";
    private static final String CATEGORY = "Technology";

    private List mTags;
    private Tag mTag;

    protected void setUp() {
        mTag = new Tag(TAG_VALUE, TAG_RELATION, TAG_URL);

        mTags = new ArrayList();
        mTags.add(mTag);
        mTags.add(mTag);
    }

    public void testTagImmutability() {
        assertEquals(TAG_VALUE, mTag.getValue());
        assertEquals(TAG_RELATION, mTag.getRelation());
        assertEquals(TAG_URL, mTag.getHref());
    }

    public void testRelatedTagResponseImmutability() {
        RelatedTagsResponse response = new RelatedTagsResponse(
                mTags, REQUEST_TAG);
        assertEquals(mTags, response.getRelatedTags());
        assertEquals(REQUEST_TAG, response.getRequestTag());
    }

    public void testTagSuggestionsResponseImmutability() {
        TagSuggestionsResponse response = new TagSuggestionsResponse(
                mTags, CATEGORY);
        assertEquals(mTags, response.getSuggestedTags());
        assertEquals(CATEGORY, response.getCategory());
    }
}
