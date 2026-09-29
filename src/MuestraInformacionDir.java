// Importamos la clase File del paquete java.io.
// File nos permite trabajar con rutas de ficheros y directorios.
import java.io.File;

public class MuestraInformacionDir {

    public static void main(String[] args) {

        // args contiene los argumentos que escribimos al ejecutar el programa.
        //
        // Si hemos pasado algún argumento, utilizamos args[0] como ruta.
        // Si no hemos pasado ninguno, utilizamos ".".
        //
        // "." representa el directorio actual desde el que se ejecuta el programa.
        //
        // Es equivalente a:
        //
        // if (args.length > 0) {
        //     ruta = args[0];
        // } else {
        //     ruta = ".";
        // }
        String ruta = (args.length > 0) ? args[0] : ".";


        // Creamos un objeto File que representa la ruta indicada.
        //
        // IMPORTANTE:
        // Crear un objeto File NO significa que el fichero exista.
        // Simplemente estamos creando un objeto Java que representa esa ruta.
        File fich = new File(ruta);


        // Comprobamos si la ruta existe realmente en el sistema.
        if (!fich.exists()) {

            // Si no existe, mostramos un mensaje.
            //
            // %s será sustituido por el contenido de la variable ruta.
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
        // Ahora comprobamos si corresponde a un FICHERO.
        if (fich.isFile()) {

            System.out.printf(
                "%s es un fichero.\n",
                ruta
            );


        // Si no es un fichero, comprobamos si es un DIRECTORIO.
        } else if (fich.isDirectory()) {

            System.out.printf(
                "%s es un directorio. Contenidos: \n",
                ruta
            );


            // listFiles() obtiene el contenido del directorio.
            //
            // Devuelve un array de objetos File.
            // Cada objeto File representa uno de los elementos
            // que hay dentro del directorio.
            //
            // Esos elementos pueden ser tanto:
            // - ficheros
            // - directorios
            File[] ficheros = fich.listFiles();


            // Recorremos todos los elementos del array.
            //
            // Es un bucle for-each:
            // en cada vuelta, "f" representa uno de los
            // ficheros o directorios encontrados.
            for (File f : ficheros) {


                // getName() devuelve únicamente el nombre
                // del fichero o directorio.
                //
                // Por ejemplo:
                // /usuarios/ana/documentos/apuntes.pdf
                //
                // getName() devolvería:
                // apuntes.pdf
                System.out.print(f.getName());


                // Comprobamos si el elemento actual es un directorio.
                if (f.isDirectory()) {

                    // Si es un directorio añadimos "/"
                    // para poder distinguir visualmente los directorios
                    // de los ficheros.
                    System.out.print("/");
                }


                // Hacemos un salto de línea para que el siguiente
                // elemento aparezca en una línea diferente.
                System.out.println();
            }
        }
    }
}