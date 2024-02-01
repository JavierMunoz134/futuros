package org.example;

import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
/**
 * Esta clase representa un programa de ejemplo que descarga el contenido de una página web
 * especificada por el usuario y lo muestra en la consola.
 */
public class Ejercicio1 {
        /**
         * El método principal del programa.
         * Solicita al usuario que ingrese una URL, descarga el contenido de la página web de forma asíncrona
         * y luego muestra el contenido en la consola.
         *
         * @param args Los argumentos de línea de comandos.
         */
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Ingrese la URL: ");
                String url = scanner.nextLine();

                CompletableFuture<String> downloadFuture = CompletableFuture.supplyAsync(() -> downloadWebPage(url));
                CompletableFuture<Void> showFuture = downloadFuture.thenAccept(content -> System.out.println(content));

                showFuture.join();
        }

        /**
         * Descarga el contenido de una página web especificada por la URL dada.
         *
         * @param url La URL de la página web a descargar.
         * @return El contenido de la página web como una cadena de texto.
         */
        private static String downloadWebPage(String url) {
                StringBuilder content = new StringBuilder();
                try {
                        URL urlObj = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                        connection.setRequestMethod("GET");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                                content.append(line);
                        }
                        reader.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return content.toString();
        }
}
