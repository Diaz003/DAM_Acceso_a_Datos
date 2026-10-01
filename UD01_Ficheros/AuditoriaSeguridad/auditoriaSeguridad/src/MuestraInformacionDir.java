import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.stream.Stream;

public class MuestraInformacionDir {

    public static void main(String[] args) {

        String ruta = (args.length > 0) ? args[0] : ".";

        // Path representa la ruta que queremos consultar.
        Path fich = Path.of(ruta);

        // Comprobamos si existe.
        if (!Files.exists(fich)) {
            System.out.printf(
                "No existe el fichero o directorio (%s).",
                ruta
            );
            return;
        }

        // Si la ruta corresponde a un fichero,
        // mostramos directamente su información.
        if (Files.isRegularFile(fich)) {

            mostrarInformacion(fich);

        // Si corresponde a un directorio,
        // recorremos su contenido.
        } else if (Files.isDirectory(fich)) {

            System.out.printf(
                "%s es un directorio. Contenidos:\n",
                ruta
            );

            try (Stream<Path> ficheros = Files.list(fich)) {

                ficheros.forEach(f -> {
                    mostrarInformacion(f);
                });

            } catch (IOException e) {

                System.out.println(
                    "Error al acceder al directorio: "
                    + e.getMessage()
                );
            }
        }
    }


    public static void mostrarInformacion(Path f) {

        try {

            // Nombre
            System.out.print(f.getFileName());


            // Si es un directorio añadimos "/"
            if (Files.isDirectory(f)) {
                System.out.print("/");
            }


            // Tamaño
            // Solo lo mostramos si se trata de un fichero.
            if (Files.isRegularFile(f)) {
                System.out.print(
                    "\t" + Files.size(f) + " bytes"
                );
            }


            // Permisos
            System.out.print("\t");

            System.out.print(
                Files.isReadable(f) ? "r" : "-"
            );

            System.out.print(
                Files.isWritable(f) ? "w" : "-"
            );

            System.out.print(
                Files.isExecutable(f) ? "x" : "-"
            );


            // Fecha de última modificación
            FileTime tiempo =
                Files.getLastModifiedTime(f);

            LocalDateTime fecha =
                LocalDateTime.ofInstant(
                    tiempo.toInstant(),
                    ZoneId.systemDefault()
                );

            DateTimeFormatter formato =
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
                );

            System.out.print(
                "\t" + fecha.format(formato)
            );


            System.out.println();


        } catch (IOException e) {

            System.out.println(
                "Error al obtener información de "
                + f.getFileName()
            );
        }
    }
}