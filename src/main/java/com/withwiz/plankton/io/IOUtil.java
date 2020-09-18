package com.withwiz.plankton.io;

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
     * default buffer size
     */
    private static final int	DEFAULT_BUFFER_SIZE	= 1024;

    /**
     * write InputStream to OutputStream.<BR>
     *
     * @param is InnputStream
     * @param os OutputStream
     * @throws IOException
     */
    public static void write(InputStream is, OutputStream os) throws IOException {
        write(is, os, 1024);
    }

    /**
     * write InputStream to OutputStream.<BR>
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
     * write Reader to Writer.<BR>
     *
     * @param br Reader
     * @param bw Writer
     * @throws IOException
     */
    public static void write(Reader br, Writer bw) throws IOException {
        write(br, bw, 1024);
    }

    /**
     * write Reader to Writer.<BR>
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

    /**
     * InputStream to byte[]
     *
     * @param is
     *            InputStream
     * @param bufferSize
     *            read buffer size
     * @return byte[]
     */
    public static byte[] toByteArray(InputStream is, int bufferSize)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(is, output, bufferSize);
        return output.toByteArray();
    }

    /**
     * InputStream to byte[]
     *
     * @param is
     *            InputStream
     * @return byte[]
     */
    public static byte[] toByteArray(InputStream is) throws IOException
    {
        return toByteArray(is, DEFAULT_BUFFER_SIZE);
    }

    /**
     * copy inputStream to outputStream
     *
     * @param inputStream
     *            InputStream
     * @param outputStream
     *            OutputStream
     * @param bufferSize
     *            read buffer size
     * @return 복사한 크기
     */
    public static long copy(InputStream inputStream, OutputStream outputStream,
                            int bufferSize) throws IOException
    {
        byte[] buffer = new byte[bufferSize];
        return copy(inputStream, outputStream, buffer);
    }

    /**
     * copy inputStream to outputStream
     *
     * @param inputStream
     *            InputStream
     * @param outputStream
     *            OutputStream
     * @param buffer
     *            buffer
     * @return read length
     * @throws java.io.IOException
     */
    public static long copy(InputStream inputStream, OutputStream outputStream,
                            byte[] buffer) throws IOException
    {
        long totalLength = 0L;
        int readLength = 0;

        while ((readLength = inputStream.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, readLength);
            totalLength += readLength;
        }
        return totalLength;
    }

    /**
     * copy inputStream to outputStream
     *
     * @param inputStream
     *            InputStream
     * @param outputStream
     *            OutputStream
     * @return read length
     * @throws java.io.IOException
     */
    public static long copy(InputStream inputStream, OutputStream outputStream)
            throws IOException
    {
        return copy(inputStream, outputStream, new byte['က']);
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args)
    {
        String str = "o12heo1ihdo12lhdn;l1irh   2o;gfib.wkv na;wlbgnf;h3gf' o;2fjn2[";
        StringInputStream is = new StringInputStream(str);
        System.out.println("source string:\n" + str);
        try
        {
            System.out.println("target byte[]:\n"
                    + new String(IOUtil.toByteArray(is)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
