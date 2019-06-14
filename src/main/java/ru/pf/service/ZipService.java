package ru.pf.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author a.kakushin
 */
@Service
public class ZipService {

    public ByteArrayOutputStream createWithSubdir(Path dir) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ZipOutputStream zipOut = new ZipOutputStream(baos);
        File fileToZip = dir.toFile();

        addDir(fileToZip, fileToZip.getName(), zipOut);

        zipOut.close();

        return baos;
    }

    private void addDir(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                addDir(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

}
