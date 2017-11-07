package com.withwiz.plankton.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {
    /**
     * write InputStream to OutputStream.<BR/>
     *
     * @param is InnputStream
     * @param os OutputStream
     * @throws IOException
     */
    public static void write(InputStream is, OutputStream os) throws IOException {
        write(is, os, 1024);
    }

    /**
     * write InputStream to OutputStream.<BR/>
     *
     * @param is         InnputStream
     * @param os         OutputStream
     * @param bufferSize buffer size
     * @throws IOException
     */
    public static void write(InputStream is, OutputStream os, int bufferSize) throws IOException {
        int defaultBufferSize = 1024;
        int len = -1;
        byte[] buffer;
        if (bufferSize < defaultBufferSize)
            buffer = new byte[defaultBufferSize];
        else
            buffer = new byte[bufferSize];
        while ((len = is.read(buffer)) > -1) {
            os.write(buffer, 0, len);
        }
    }

    /**
     * write Reader to Writer.<BR/>
     *
     * @param br Reader
     * @param bw Writer
     * @throws IOException
     */
    public static void write(Reader br, Writer bw) throws IOException {
        write(br, bw, 1024);
    }

    /**
     * write Reader to Writer.<BR/>
     *
     * @param br         Reader
     * @param bw         Writer
     * @param bufferSize buffer size
     * @throws IOException
     */
    public static void write(Reader br, Writer bw, int bufferSize) throws IOException {
        int defaultBufferSize = 1024;
        int len = -1;
        char[] buffer;
        if (bufferSize < defaultBufferSize)
            buffer = new char[defaultBufferSize];
        else
            buffer = new char[bufferSize];
        while ((len = br.read(buffer)) > -1) {
            bw.write(buffer, 0, len);
        }
    }

}
