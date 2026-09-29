// Uso de clase File para mostrar ficheros y directorios
import java.io.File;

public class MuestraInformacionDir {

    public static void main(String[] args) {
        String ruta = (args.length > 0) ? args[0] : ".";

        File fich = new File(ruta);

        if (!fich.exists()) {
            System.out.printf("No existe el fichero o directorio (%s).", ruta);
            return;
        }

        if (fich.isFile()) {
            System.out.printf("%s es un fichero.\n", ruta);
        } else if (fich.isDirectory()) {
            System.out.printf("%s es un directorio. Contenidos: \n", ruta);

            File[] ficheros = fich.listFiles(); // Ficheros o directorios

            for (File f : ficheros) {
                System.out.print(f.getName());

                if (f.isDirectory()) {
                    System.out.print("/");
                }

                System.out.println();
            }
        }
    }
}