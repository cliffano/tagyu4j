package com.cliffano.tagyu4j.exception;

import com.cliffano.tagyu4j.exception.Tagyu4JException;

import junit.framework.TestCase;

public class Tagyu4JExceptionTest extends TestCase {

        private Tagyu4JException exception;

        public void testExceptionWithErrorMessage() {

                exception = new Tagyu4JException("some error message");
                assertEquals("some error message", exception.getMessage());
        }

        public void testExceptionWithErrorMessageAndThrowableCause() {

                Throwable cause = new Throwable();
                exception = new Tagyu4JException("some error message", cause);
                assertEquals("some error message", exception.getMessage());
                assertSame(cause, exception.getCause());
                assertEquals(cause, exception.getCause());
        }
}
