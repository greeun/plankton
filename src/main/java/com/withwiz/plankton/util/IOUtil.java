package com.withwiz.plankton.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Input/Output utility
 */
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

    /**
     * get Byte[] inputStream that read from file
     *
     * @param file File
     * @return ByteArrayInputStream
     */
    public static ByteArrayInputStream getByteArrayInputStream(File file) throws IOException {
        return getByteArrayInputStream(file.getAbsolutePath());
    }

    /**
     * get Byte[] inputStream that read from file
     *
     * @param filepath file path
     * @return ByteArrayInputStream
     */
    public static ByteArrayInputStream getByteArrayInputStream(String filepath) throws IOException {
        ByteArrayInputStream inputStream = null;
        Path path = Paths.get(filepath);
        FileChannel channel = null;
        ByteBuffer byteBuffer = null;
        try {
            channel = FileChannel.open(path, StandardOpenOption.READ);
            byteBuffer = ByteBuffer.allocate((int) Files.size(path));
            channel.read(byteBuffer);
            byteBuffer.flip();
            byte[] buffer = byteBuffer.array();
            inputStream = new ByteArrayInputStream(buffer);
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return inputStream;
    }

    /**
     * get inputStream from filepath
     *
     * @param filepath file path
     * @return DataInputStream
     */
    public static DataInputStream getDataInputStream(String filepath) throws FileNotFoundException {
        return getDataInputStream(new File(filepath));
    }

    /**
     * get inputStream from filepath
     *
     * @param file File instance
     * @return DataInputStream
     */
    public static DataInputStream getDataInputStream(File file) throws FileNotFoundException {
        return new DataInputStream(new FileInputStream(file));
    }

}
