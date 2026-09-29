// Importamos Path y Files del paquete java.nio.file.
//
// Path representa una ruta de un fichero o directorio.
// Files contiene métodos para trabajar con esas rutas.
import java.nio.file.Path;
import java.nio.file.Files;

import java.io.IOException;
import java.util.stream.Stream;

public class MuestraInformacionDir {

    public static void main(String[] args) {

        // args contiene los argumentos que escribimos al ejecutar el programa.
        //
        // Si hemos pasado algún argumento, utilizamos args[0] como ruta.
        // Si no hemos pasado ninguno, utilizamos ".".
        //
        // "." representa el directorio actual desde el que se ejecuta
        // el programa.
        //
        // Es equivalente a:
        //
        // if (args.length > 0) {
        //     ruta = args[0];
        // } else {
        //     ruta = ".";
        // }
        String ruta = (args.length > 0) ? args[0] : ".";


        // Creamos un objeto Path que representa la ruta indicada.
        //
        // IMPORTANTE:
        // Crear un objeto Path NO significa que el fichero exista.
        // Simplemente estamos creando un objeto Java que representa esa ruta.
        Path fich = Path.of(ruta);


        // Comprobamos si la ruta existe realmente en el sistema.
        //
        // A diferencia de File, donde hacíamos:
        //
        // fich.exists()
        //
        // ahora utilizamos el método estático Files.exists().
        if (!Files.exists(fich)) {

            System.out.printf(
                "No existe el fichero o directorio (%s).",
                ruta
            );

            // Terminamos la ejecución del método main.
            // No tiene sentido continuar si la ruta no existe.
            return;
        }


        // Si hemos llegado hasta aquí sabemos que la ruta existe.
        //
        // Comprobamos si corresponde a un FICHERO.
        if (Files.isRegularFile(fich)) {

            System.out.printf(
                "%s es un fichero.\n",
                ruta
            );


        // Si no es un fichero, comprobamos si es un DIRECTORIO.
        } else if (Files.isDirectory(fich)) {

            System.out.printf(
                "%s es un directorio. Contenidos:\n",
                ruta
            );


            // Files.list() obtiene los elementos que se encuentran
            // directamente dentro del directorio.
            //
            // Devuelve un Stream<Path>.
            //
            // Cada Path representa uno de los elementos del directorio.
            //
            // Esos elementos pueden ser:
            // - ficheros
            // - directorios
            //
            // Files.list() puede producir una IOException, por eso
            // utilizamos un bloque try-catch.
            //
            // Además utilizamos try-with-resources para que el Stream
            // se cierre automáticamente cuando terminemos de utilizarlo.
            try (Stream<Path> ficheros = Files.list(fich)) {


                // Recorremos todos los elementos del Stream.
                //
                // En cada iteración, "f" representa uno de los
                // ficheros o directorios encontrados.
                ficheros.forEach(f -> {


                    // getFileName() devuelve únicamente el nombre
                    // del fichero o directorio.
                    //
                    // Por ejemplo:
                    //
                    // /usuarios/ana/documentos/apuntes.pdf
                    //
                    // getFileName() devolvería:
                    //
                    // apuntes.pdf
                    System.out.print(f.getFileName());


                    // Comprobamos si el elemento actual es un directorio.
                    if (Files.isDirectory(f)) {

                        // Si es un directorio añadimos "/"
                        // para distinguir visualmente los directorios
                        // de los ficheros.
                        System.out.print("/");
                    }


                    // Hacemos un salto de línea para que el siguiente
                    // elemento aparezca en una línea diferente.
                    System.out.println();
                });

            } catch (IOException e) {

                // Si se produce un error al intentar acceder
                // al contenido del directorio, mostramos el mensaje.
                System.out.println(
                    "Error al acceder al directorio: " + e.getMessage()
                );
            }
        }
    }
}