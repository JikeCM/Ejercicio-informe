package dam.procesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class RecursosSistema {

    private static final String[] COMANDO_PS = {"ps", "-e"};
    private static final String[] COMANDO_DF = {"df", "-h"};
    private static final String[] COMANDO_FREE = {"free", "-h"};
    private static final String MENSAJE_ERROR_COMANDO = "Error al ejecutar el comando: ";
    private static final String MENSAJE_PS = "## <---PROCESOS DEL SISTEMA--->";
    private static final String MENSAJE_DF = "## <---ESPACIO EN DISCO--->";
    private static final String MENSAJE_FREE = "## <---MEMORIA DEL SISTEMA--->";

    public static void main(String[] args) {
        ejecutarProcesoInforme(MENSAJE_PS, COMANDO_PS);
        ejecutarProcesoInforme(MENSAJE_DF, COMANDO_DF);
        ejecutarProcesoInforme(MENSAJE_FREE, COMANDO_FREE);
    }

    public static void ejecutarProcesoInforme(String mensaje, String[] comando) {
        try {
            System.out.println(mensaje);
            Process proceso = Runtime.getRuntime().exec(comando);
            BufferedReader salida = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            StringBuilder md = new StringBuilder();
            BufferedWriter escribirInforme = new BufferedWriter(new FileWriter("src/main/resources/INFORME.md", true));

            md.append(mensaje).append("\n").append("\n").append("```text\n");
            String linea;
            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
                md.append(linea).append("\n");
            }
            md.append("```\n\n");
            salida.close();
            escribirInforme.write(md.toString());
            escribirInforme.close();
            proceso.waitFor();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            System.out.println(MENSAJE_ERROR_COMANDO + comando[0]);
        }
    }
}
