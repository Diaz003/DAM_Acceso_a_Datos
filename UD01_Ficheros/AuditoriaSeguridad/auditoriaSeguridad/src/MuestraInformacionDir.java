// ============================================================
// IMPORTS
// ============================================================

// Path se utiliza para REPRESENTAR una ruta del sistema.
// Esa ruta puede apuntar a un fichero o a un directorio.
//
// Ejemplos de rutas:
// "datos.txt"
// "documentos/informe.pdf"
// "/Users/ana/Documentos"
import java.nio.file.Path;


// Files contiene métodos para TRABAJAR con ficheros y directorios.
//
// Por ejemplo:
// Files.exists(...)
// Files.isDirectory(...)
// Files.size(...)
// Files.isReadable(...)
import java.nio.file.Files;


// Algunas operaciones realizadas con Files pueden producir errores.
// Por ejemplo, intentar acceder a un directorio para el que
// no tenemos permisos.
//
// Java representa muchos de estos errores mediante IOException.
import java.io.IOException;


// FileTime permite almacenar una fecha/hora asociada a un fichero.
// Lo utilizaremos para obtener la fecha de última modificación.
import java.nio.file.attribute.FileTime;


// Estas clases nos permitirán convertir y mostrar la fecha
// de última modificación de una forma comprensible.
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


// Stream nos permitirá recorrer los elementos de un directorio.
import java.util.stream.Stream;



public class MuestraInformacionDir {

    public static void main(String[] args) {


        // ============================================================
        // 1. OBTENER LA RUTA
        // ============================================================

        // args contiene los argumentos que escribimos al ejecutar
        // el programa desde el terminal.
        //
        // Por ejemplo:
        //
        // java MuestraInformacionDir documentos
        //
        // En ese caso:
        //
        // args[0] contiene "documentos"
        //
        //
        // Comprobamos si el usuario ha indicado una ruta:
        //
        // args.length > 0
        //
        // Si es TRUE:
        //      utilizamos args[0]
        //
        // Si es FALSE:
        //      utilizamos "."
        //
        // "." significa "directorio actual".
        //
        // El operador ? : se llama OPERADOR TERNARIO.
        //
        // condicion ? valorSiTrue : valorSiFalse
        //
        // Por tanto:
        //
        // (args.length > 0) ? args[0] : "."
        //
        // es equivalente a escribir:
        //
        // if (args.length > 0) {
        //     ruta = args[0];
        // } else {
        //     ruta = ".";
        // }

        String ruta = (args.length > 0) ? args[0] : ".";



        // ============================================================
        // 2. CREAR UN PATH
        // ============================================================

        // Creamos un objeto Path a partir del String anterior.
        //
        // IMPORTANTE:
        //
        // Path representa una RUTA.
        //
        // No significa que el fichero o directorio exista realmente.
        //
        // Por ejemplo:
        //
        // Path fich = Path.of("datos.txt");
        //
        // simplemente representa la ruta "datos.txt".
        //
        // Después tendremos que comprobar si realmente existe.

        Path fich = Path.of(ruta);



        // ============================================================
        // 3. COMPROBAR SI LA RUTA EXISTE
        // ============================================================

        // Files.exists(fich) devuelve:
        //
        // true  -> si existe
        // false -> si no existe
        //
        // Como ponemos ! delante:
        //
        // !Files.exists(fich)
        //
        // significa:
        //
        // "si NO existe..."

        if (!Files.exists(fich)) {

            // %s será sustituido por el valor de "ruta".

            System.out.printf(
                "No existe el fichero o directorio (%s).",
                ruta
            );


            // return termina la ejecución del método main.
            //
            // Si la ruta no existe, no tiene sentido
            // continuar ejecutando el programa.

            return;
        }



        // ============================================================
        // 4. ¿ES UN FICHERO?
        // ============================================================

        // Files.isRegularFile(fich) comprueba si la ruta
        // corresponde a un fichero normal.
        //
        // Por ejemplo:
        //
        // datos.txt
        // foto.jpg
        // informe.pdf

        if (Files.isRegularFile(fich)) {


            // Llamamos al método que hemos creado más abajo.
            //
            // Le pasamos el Path "fich".
            //
            // Ese método se encargará de mostrar:
            //
            // - nombre
            // - tamaño
            // - permisos
            // - fecha de modificación

            mostrarInformacion(fich);



        // ============================================================
        // 5. ¿ES UN DIRECTORIO?
        // ============================================================

        // Si no era un fichero, comprobamos si es un directorio.
        //
        // Files.isDirectory(fich) devuelve true si la ruta
        // corresponde a un directorio.

        } else if (Files.isDirectory(fich)) {


            System.out.printf(
                "%s es un directorio. Contenidos:\n",
                ruta
            );



            // ========================================================
            // 6. OBTENER EL CONTENIDO DEL DIRECTORIO
            // ========================================================

            // Files.list(fich) obtiene los elementos que están
            // dentro del directorio.
            //
            // Por ejemplo, si tenemos:
            //
            // documentos/
            //      apuntes.pdf
            //      notas.txt
            //      fotos/
            //
            // Files.list() nos permitirá recorrer:
            //
            // apuntes.pdf
            // notas.txt
            // fotos/
            //
            //
            // Files.list() devuelve un Stream<Path>.
            //
            // Podemos pensar en un Stream como una secuencia
            // de elementos que podemos recorrer.
            //
            // Cada elemento será un Path.


            // Files.list() puede lanzar una IOException.
            //
            // Por eso utilizamos try.
            //
            // Además, esta forma:
            //
            // try (Stream<Path> ficheros = ...)
            //
            // se llama TRY-WITH-RESOURCES.
            //
            // Hace que Java cierre automáticamente el Stream
            // cuando terminemos de utilizarlo.

            try (Stream<Path> ficheros = Files.list(fich)) {



                // ====================================================
                // 7. RECORRER LOS ELEMENTOS
                // ====================================================

                // forEach significa:
                //
                // "para cada elemento del Stream..."
                //
                //
                // f representa cada uno de los elementos.
                //
                // Por ejemplo:
                //
                // primera vuelta:
                // f -> apuntes.pdf
                //
                // segunda vuelta:
                // f -> notas.txt
                //
                // tercera vuelta:
                // f -> fotos/
                //
                //
                // f -> { ... }
                //
                // es una expresión LAMBDA.
                //
                // Podemos leerla como:
                //
                // "para cada f, ejecuta lo que hay entre llaves".

                ficheros.forEach(f -> {


                    // Para cada fichero/directorio encontrado,
                    // llamamos a nuestro método mostrarInformacion().
                    //
                    // Le pasamos el Path correspondiente.

                    mostrarInformacion(f);

                });



            // ========================================================
            // 8. CONTROLAR POSIBLES ERRORES
            // ========================================================

            // Si Files.list() produce una IOException,
            // el programa entra en este catch.

            } catch (IOException e) {


                // e contiene información sobre el error.
                //
                // e.getMessage() nos devuelve el mensaje
                // asociado al error.

                System.out.println(
                    "Error al acceder al directorio: "
                    + e.getMessage()
                );
            }
        }
    }



    // ================================================================
    // MÉTODO mostrarInformacion()
    // ================================================================

    // Este método recibe un Path llamado "f".
    //
    // Ese Path puede representar:
    //
    // - un fichero
    // - un directorio
    //
    //
    // El objetivo del método es mostrar información sobre él.
    //
    // Es static porque lo llamamos directamente desde main(),
    // que también es static.
    //
    // Es void porque no devuelve ningún resultado.
    // Simplemente muestra información por pantalla.

    public static void mostrarInformacion(Path f) {


        // Algunas operaciones que vamos a realizar con Files
        // pueden producir IOException.
        //
        // Por eso utilizamos try-catch.

        try {


            // ========================================================
            // 1. MOSTRAR EL NOMBRE
            // ========================================================

            // getFileName() devuelve solamente el nombre final
            // de la ruta.
            //
            // Por ejemplo:
            //
            // /Users/ana/documentos/apuntes.pdf
            //
            // getFileName() devuelve:
            //
            // apuntes.pdf

            System.out.print(f.getFileName());



            // ========================================================
            // 2. INDICAR SI ES UN DIRECTORIO
            // ========================================================

            // Comprobamos si "f" representa un directorio.

            if (Files.isDirectory(f)) {


                // Si es un directorio, añadimos "/".
                //
                // Así podremos distinguir fácilmente:
                //
                // apuntes.pdf
                //
                // de:
                //
                // documentos/

                System.out.print("/");
            }



            // ========================================================
            // 3. MOSTRAR EL TAMAÑO
            // ========================================================

            // Solo vamos a mostrar el tamaño cuando sea
            // un fichero normal.

            if (Files.isRegularFile(f)) {


                // Files.size(f) devuelve el tamaño
                // del fichero en BYTES.
                //
                // Por ejemplo:
                //
                // 245678 bytes

                System.out.print(
                    "\t" + Files.size(f) + " bytes"
                );
            }



            // ========================================================
            // 4. MOSTRAR LOS PERMISOS
            // ========================================================

            // "\t" introduce una tabulación para separar
            // visualmente las columnas.

            System.out.print("\t");


            // --------------------------------------------------------
            // PERMISO DE LECTURA
            // --------------------------------------------------------

            // Files.isReadable(f) devuelve true si podemos
            // leer el fichero/directorio.
            //
            // Utilizamos el operador ternario:
            //
            // condición ? siTrue : siFalse
            //
            // Si podemos leer:
            //
            // r
            //
            // Si no:
            //
            // -

            System.out.print(
                Files.isReadable(f) ? "r" : "-"
            );



            // --------------------------------------------------------
            // PERMISO DE ESCRITURA
            // --------------------------------------------------------

            // Si podemos escribir mostramos "w".
            // Si no podemos, mostramos "-".

            System.out.print(
                Files.isWritable(f) ? "w" : "-"
            );



            // --------------------------------------------------------
            // PERMISO DE EJECUCIÓN
            // --------------------------------------------------------

            // Si podemos ejecutar mostramos "x".
            // Si no podemos, mostramos "-".

            System.out.print(
                Files.isExecutable(f) ? "x" : "-"
            );


            // De esta forma podemos obtener:
            //
            // rwx -> lectura, escritura y ejecución
            // rw- -> lectura y escritura
            // r-x -> lectura y ejecución
            // r-- -> solo lectura
            // --- -> ninguno



            // ========================================================
            // 5. FECHA DE ÚLTIMA MODIFICACIÓN
            // ========================================================

            // Files.getLastModifiedTime(f)
            // obtiene la fecha y hora en la que el fichero
            // fue modificado por última vez.
            //
            // El resultado es un objeto FileTime.

            FileTime tiempo =
                Files.getLastModifiedTime(f);



            // FileTime no está pensado directamente para
            // mostrar una fecha con nuestro formato.
            //
            // Por eso lo convertimos a LocalDateTime.
            //
            // ZoneId.systemDefault() indica que queremos utilizar
            // la zona horaria configurada en el ordenador.

            LocalDateTime fecha =
                LocalDateTime.ofInstant(
                    tiempo.toInstant(),
                    ZoneId.systemDefault()
                );



            // ========================================================
            // 6. DAR FORMATO A LA FECHA
            // ========================================================

            // Creamos el formato con el que queremos
            // mostrar la fecha.
            //
            // dd   -> día
            // MM   -> mes
            // yyyy -> año
            // HH   -> hora (formato 24 horas)
            // mm   -> minutos
            //
            // Ejemplo:
            //
            // 29/09/2026 10:15

            DateTimeFormatter formato =
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
                );



            // Mostramos la fecha utilizando el formato anterior.
            //
            // "\t" añade una tabulación antes de la fecha.

            System.out.print(
                "\t" + fecha.format(formato)
            );



            // Finalmente hacemos un salto de línea.
            //
            // De esta forma, el siguiente fichero aparecerá
            // en una nueva línea.

            System.out.println();



        // ============================================================
        // CONTROL DE ERRORES
        // ============================================================

        // Si se produce una IOException al consultar información
        // sobre el fichero, entraremos aquí.

        } catch (IOException e) {


            System.out.println(
                "Error al obtener información de "
                + f.getFileName()
            );
        }
    }
}