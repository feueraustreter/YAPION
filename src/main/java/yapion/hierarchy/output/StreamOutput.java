// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020,2021 yoyosource

package yapion.hierarchy.output;

import yapion.exceptions.YAPIONException;

import java.io.IOException;
import java.io.OutputStream;

public class StreamOutput extends AbstractOutput implements AutoCloseable {

    private final OutputStream outputStream;

    public StreamOutput(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    protected void internalConsume(String s) {
        try {
            outputStream.write(bytes(s));
        } catch (IOException e) {
            throw new YAPIONException("Exception while writing data");
        }
    }

    public void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new YAPIONException("Exception while stream flushing");
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new YAPIONException("Exception while stream closing");
        }
    }

}