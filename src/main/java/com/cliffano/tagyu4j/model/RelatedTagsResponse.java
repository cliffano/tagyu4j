/**
 * Copyright (c) 2005-2007, Cliffano Subagio
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
 *   * Neither the name of Studio Cliffano nor the names of its contributors
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
package com.cliffano.tagyu4j.model;

import java.util.List;

/**
 * A response which contains related tags and request tag.
 * @author Cliffano
 */
public class RelatedTagsResponse extends AbstractResponse {

    /**
     * The request tag.
     */
    private String mRequestTag;

    /**
     * Creates an instance with related tags and request tag.
     * @param relatedTags a list of tags related to the request tag
     * @param requestTag the request tag
     */
    public RelatedTagsResponse(
            final List relatedTags,
            final String requestTag) {
        super(relatedTags);
        mRequestTag = requestTag;
    }

    /**
     * Gets the related {@link Tag}s.
     * @return a list of {@link Tag}s related to the request tag
     */
    public final List getRelatedTags() {
        return getTags();
    }

    /**
     * Get the request tag.
     * @return the request tag
     */
    public final String getRequestTag() {
        return mRequestTag;
    }
}
