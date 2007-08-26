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
 *   * Neither the name of Qoqoa nor the names of its contributors
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
package com.qoqoa.tagyu4j.model;

/**
 * A representation of a tag information from Tagyu service.
 * @author Cliffano
 */
public class Tag {

    /**
     * The value of the tag.
     */
    private String mValue;

    /**
     * The tag relation.
     */
    private String mRelation;

    /**
     * The Tagyu url of the tag.
     */
    private String mHref;

    /**
     * Creates an instance of the tag.
     * @param value the tag value
     * @param relation the tag relation
     * @param href the Tagyu url of the tag
     */
    public Tag(final String value, final String relation, final String href) {
        mValue = value;
        mRelation = relation;
        mHref = href;
    }

    /**
     * Gets the Tagyu url of the tag.
     * @return the Tagyu url of the tag
     */
    public final String getHref() {
        return mHref;
    }

    /**
     * Gets the tag relation.
     * @return the tag relation
     */
    public final String getRelation() {
        return mRelation;
    }

    /**
     * Gets the tag value.
     * @return the tag value
     */
    public final String getValue() {
        return mValue;
    }
}
