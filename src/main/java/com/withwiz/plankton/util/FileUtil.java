package com.withwiz.plankton.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Calendar;

/**
 * file utility class.<BR>
 */
public class FileUtil {

    /**
     * return file directory.<BR>
     *
     * @param filepath file path
     * @return directory path
     */
    public static String getFileDirectory(String filepath) {
        return new File(filepath).getParent();
    }

    /**
     * return Only file name.<BR>
     *
     * @param filepath file path
     * @return file name
     */
    public static String getOnlyFileName(String filepath) {
        String sRet = null;
        int separatorIndex = filepath.lastIndexOf(File.separator);
        String filename;
        if (-1 < separatorIndex) {
            filename = filepath.substring(separatorIndex);
        } else {
            filename = filepath;
        }
        sRet = filename.substring(0, filename.lastIndexOf("."));
        return sRet;
    }

    /**
     * return file extension.<BR>
     *
     * @param filepath file path
     * @return file extension
     */
    public static String getFileExtension(String filepath) {
        return filepath.substring(filepath.lastIndexOf(File.separator));
    }

    /**
     * remove file with recursive path.<BR>
     *
     * @param filepath file path
     * @return result
     */
    public static boolean deleteWithSub(String filepath) {
        boolean bRet = false;
        File file = new File(filepath);
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                bRet = deleteWithSub(fileList[i].getAbsolutePath());
            }
        }

        if (file.exists()) {
            bRet = file.delete();
        }

        return bRet;
    }

    /**
     * write InputStream to destination path.<BR>
     *
     * @param inputStream  source InputStream
     * @param destFilePath destination path
     * @throws IOException
     */
    public static void writeFile(InputStream inputStream, String destFilePath) throws IOException {
        checkParentDir(destFilePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(destFilePath);
            bos = new BufferedOutputStream(fos);
            IOUtil.write(inputStream, bos);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bos != null) {
                bos.close();
                bos = null;
            }
            if (fos != null) {
                fos.close();
                fos = null;
            }
        }
    }


    /**
     * write Reader to Writer with encoding.<BR>
     *
     * @param reader       reader
     * @param destFilePath destination path
     * @param enconding    encoding
     * @throws IOException
     */
    public static void writeFile(Reader reader, String destFilePath, String enconding)
            throws IOException {
        checkParentDir(destFilePath);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(destFilePath);
            osw = new OutputStreamWriter(fos, enconding);
            bw = new BufferedWriter(osw);
            IOUtil.write(reader, bw);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
                bw = null;
            }
            if (osw != null) {
                osw.close();
                osw = null;
            }
            if (fos != null) {
                fos.close();
                fos = null;
            }
        }
    }

    /**
     * if parent directory NOT exist, mkdir the parent.<BR>
     *
     * @param destFilePath destination path
     */
    public static void checkParentDir(String destFilePath) {
        int lastIndex = destFilePath.lastIndexOf(File.separator);
        File dirParent = new File(destFilePath.substring(0, lastIndex));
        if (!dirParent.isDirectory()) {
            dirParent.mkdirs();
        }
    }

    /**
     * return user work directory.<BR>
     *
     * @return directory
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String convertToFileURL(String filename) {
        return convertToFileURL(new File(filename));
    }

    public static String convertToFileURL(File file) {
        // On JDK 1.2 and later, simplify this to:
        // "path = file.toURL().toString()".
        String path = file.getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    /**
     * copy a file.<BR>
     *
     * @param source source file
     * @param target destination file
     */
    public static void fileCopy(File source, File target) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            if (source.isDirectory()) {
                if (!target.exists()) {
                    target.mkdir();
                }
                String[] children = source.list();
                for (int i = 0; i < children.length; i++) {
                    fileCopy(new File(source, children[i]), new File(target, children[i]));
                }
            } else {
                inputStream = new FileInputStream(source);
                outputStream = new FileOutputStream(target);
                copyWithChannel(inputStream, outputStream);
            }
        } finally {
            if (bout != null) {
                bout.close();
                bout = null;
            }
            if (bin != null) {
                bin.close();
                bin = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                outputStream = null;
            }
        }
    }

    /**
     * copy a file with stream.<BR>
     *
     * @param source source file path
     * @param target destination path
     */
    public static void copyWithStream(String source, String target) throws IOException {
        File sourceFile = new File(source);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * copy a file with buffer.<BR>
     *
     * @param source source file path
     * @param target destinatio path
     */
    public static void copyWithBuffer(String source, String target) throws IOException {
        File sourceFile = new File(source);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);
            bin = new BufferedInputStream(inputStream);
            bout = new BufferedOutputStream(outputStream);

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = bin.read(buffer, 0, 1024)) != -1) {
                bout.write(buffer, 0, bytesRead);
            }

        } finally {
            if (bout != null) {
                bout.close();
                bout = null;
            }
            if (bin != null) {
                bin.close();
                bin = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        }
    }

    /**
     * copy a file with FileInputStream,FileOutputStream.<BR>
     *
     * @param fis FileInputStream
     * @param fos FileOutputStream
     */
    public static void copyWithChannel(FileInputStream fis, FileOutputStream fos) throws IOException {
        FileChannel fcin = null;
        FileChannel fcout = null;

        try {
            fcin = fis.getChannel();
            fcout = fos.getChannel();

            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } finally {
            if (fcout != null) {
                fcout.close();
                fcout = null;
            }
            if (fcin != null) {
                fcin.close();
                fcin = null;
            }
            if (fos != null) {
                fos.close();
                fos = null;
            }
            if (fis != null) {
                fis.close();
                fis = null;
            }
        }
    }

    /**
     * copy a file with channel.<BR>
     *
     * @param source source file path
     * @param target destination file path
     */
    public static void copyWithChannel(String source, String target) throws IOException {
        File sourceFile = new File(source);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);
            // channel copy
            copyWithChannel(inputStream, outputStream);
        } finally {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        }
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        String source = "c:/test.mp4";
        String target = "c:/test_copied.mp4";

        File file = new File(source);

        String filename = file.getName();
        String d2 = file.getPath();
        String d3 = file.getParent();

        Calendar startCal = null;
        Calendar endCal = null;
        // default stream
        startCal = Calendar.getInstance();
        try {
            FileUtil.copyWithStream(source, target + "1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        endCal = Calendar.getInstance();
        System.out.println("stream elapsed time: "
                + (endCal.getTimeInMillis() - startCal.getTimeInMillis()));

        // buffer
        startCal = Calendar.getInstance();
        try {
            FileUtil.copyWithBuffer(source, target + "2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        endCal = Calendar.getInstance();
        System.out.println("buffer elapsed time: "
                + (endCal.getTimeInMillis() - startCal.getTimeInMillis()));

        // channel
        startCal = Calendar.getInstance();
        try {
            FileUtil.copyWithChannel(source, target + "3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        endCal = Calendar.getInstance();
        System.out.println("channel elapsed time: "
                + (endCal.getTimeInMillis() - startCal.getTimeInMillis()));

    }
}
