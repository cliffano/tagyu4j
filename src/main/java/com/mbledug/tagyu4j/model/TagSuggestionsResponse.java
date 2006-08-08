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
package com.mbledug.tagyu4j.model;

import java.util.List;

/**
 * A response which contains suggested tags and category.
 * @author Cliffano
 */
public class TagSuggestionsResponse extends AbstractResponse {

    /**
     * The category of the text provided.
     */
    private String mCategory;

    /**
     * Create an instance with suggested tags and category.
     * @param suggestedTags the suggested tags
     * @param category the category
     */
    public TagSuggestionsResponse(
            final List suggestedTags,
            final String category) {
        super(suggestedTags);
        mCategory = category;
    }

    /**
     * Get the category.
     * @return the category
     */
    public final String getCategory() {
        return mCategory;
    }

    /**
     * Get a list of suggested tags.
     * @return the list of suggested tags (Tag objects)
     */
    public final List getSuggestedTags() {
        return getTags();
    }
}
