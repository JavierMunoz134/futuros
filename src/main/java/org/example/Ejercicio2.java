package org.example;


import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.*;
import java.nio.file.*;
import java.io.*;
import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la ruta del archivo o directorio que quieras comprimir:");
        String sourcePath = scanner.nextLine();
        System.out.println("¿Donde quieres poner el .zip?");
        String destinationPath = scanner.nextLine();

        String zipFileName = destinationPath.concat(".zip");

        try (
                FileOutputStream fos = new FileOutputStream(zipFileName);
                ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            Path filePath = Paths.get(sourcePath);
            if (Files.isDirectory(filePath)) {
                Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        zos.putNextEntry(new ZipEntry(filePath.relativize(file).toString()));
                        Files.copy(file, zos);
                        zos.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                zos.putNextEntry(new ZipEntry(filePath.getFileName().toString()));
                Files.copy(filePath, zos);
                zos.closeEntry();
            }
        }

        Path zipPath = Paths.get(zipFileName);
        Files.move(zipPath, zipPath.resolveSibling(destinationPath));
    }
}
