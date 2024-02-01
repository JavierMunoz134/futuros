package org.example;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ejercicio3 {
    public static void main(String[] args) throws IOException {
        List<String> webPages = Arrays.asList(
                "http://example.com",
                "http://example.org",
                "https://www.google.com",
                "https://www.github.com",
                "https://www.stackoverflow.com",
                "https://www.openai.com",
                "https://www.oracle.com",
                "https://www.wikipedia.org",
                "https://www.linkedin.com",
                "https://www.youtube.com"
        );

        for (int i = 0; i < webPages.size(); i++) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(webPages.get(i)).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("file" + i + ".html")) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                // Handle exception
            }
        }

        try (FileOutputStream fos = new FileOutputStream("webpages.zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (int i = 0; i < webPages.size(); i++) {
                File fileToZip = new File("file" + i + ".html");

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }
                }
            }
        }
    }
}