package com.quyenlx.learningjapanese.Util.sharedpreferences;

import java.io.IOException;

/**
 * Created by quyenlx on 8/20/2016.
 */
public class WrappedIOException {
    public static IOException wrap(final Throwable e) {
        return wrap(e.getMessage(), e);
    }

    public static IOException wrap(final String message, final Throwable e) {
        final IOException wrappedException = new IOException(message + " [" +
                e.getMessage() + "]");
        wrappedException.setStackTrace(e.getStackTrace());
        wrappedException.initCause(e);
        return wrappedException;
    }
}
