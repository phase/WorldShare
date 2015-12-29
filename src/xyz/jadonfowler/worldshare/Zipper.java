package xyz.jadonfowler.worldshare;

import java.io.*;
import java.util.zip.*;

public class Zipper {
    public static void zip(String inputFolder, String targetZippedFolder) throws IOException {
        FileOutputStream fileOutputStream = null;
        fileOutputStream = new FileOutputStream(targetZippedFolder);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        File inputFile = new File(inputFolder);
        if (inputFile.isFile()) zipFile(inputFile, "", zipOutputStream);
        else if (inputFile.isDirectory()) zipFolder(zipOutputStream, inputFile, "");
        zipOutputStream.close();
    }

    public static void zipFolder(ZipOutputStream zipOutputStream, File inputFolder, String parentName)
            throws IOException {
        String myname = parentName + inputFolder.getName() + "\\";
        ZipEntry folderZipEntry = new ZipEntry(myname);
        zipOutputStream.putNextEntry(folderZipEntry);
        File[] contents = inputFolder.listFiles();
        for (File f : contents) {
            if (f.isFile()) zipFile(f, myname, zipOutputStream);
            else if (f.isDirectory()) zipFolder(zipOutputStream, f, myname);
        }
        zipOutputStream.closeEntry();
    }

    public static void zipFile(File inputFile, String parentName, ZipOutputStream zipOutputStream) throws IOException {
        ZipEntry zipEntry = new ZipEntry(parentName + inputFile.getName());
        zipOutputStream.putNextEntry(zipEntry);
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buf)) > 0)
            zipOutputStream.write(buf, 0, bytesRead);
        zipOutputStream.closeEntry();
        fileInputStream.close();
    }
}