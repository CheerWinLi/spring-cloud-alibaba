package com.alibaba.dubbo.provider.server;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author :Lictory
 * @date : 2024/08/23
 */


public class CaptureHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream capture;
    private final PrintWriter writer;
    private int httpStatus;

    public CaptureHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        this.capture = new ByteArrayOutputStream();
        this.writer = new PrintWriter(capture);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void setStatus(int sc) {
        super.setStatus(sc);
        this.httpStatus = sc;
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        writer.flush();
    }

    public byte[] getCaptureAsBytes() {
        return capture.toByteArray();
    }

    public String getCaptureAsString() {
        return capture.toString();
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }
}

