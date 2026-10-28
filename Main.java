import java.util.ArrayList;
import java.util.List;

public class Main {

    // Lista de todas las líneas registradas (para validar números duplicados, HU-01)
    private static final List<LineaTelefonica> lineas = new ArrayList<>();

    private static boolean existeNumero(String numero) {
        for (LineaTelefonica l : lineas) {
            if (l.getNumero().equals(numero)) return true;
        }
        return false;
    }

    public static void main(String[] args) {

        System.out.println("===== 1. REGISTRO DE LÍNEAS =====");
        String numPre = "3001234567";
        if (!existeNumero(numPre)) {
            LineaPrepago prepago = new LineaPrepago(numPre, "Ana Gómez", 0, 5000);
            lineas.add(prepago);
        }
        String numPos = "3109876543";
        if (!existeNumero(numPos)) {
            LineaPostpago postpago = new LineaPostpago(numPos, "Carlos Ruiz", 0, 0, 100000, 30000);
            lineas.add(postpago);
        }

        // Intento de registrar un número duplicado
        if (existeNumero("3001234567")) {
            System.out.println("Error: el número 3001234567 ya está registrado.");
        }

        LineaPrepago prepago = (LineaPrepago) lineas.get(0);
        LineaPostpago postpago = (LineaPostpago) lineas.get(1);

        System.out.println("\n===== 2. DATOS INICIALES (get) =====");
        prepago.mostrarInformacion();
        System.out.println();
        postpago.mostrarInformacion();

        System.out.println("\n===== 3. VALIDACIONES DE LOS SET =====");
        prepago.setSaldo(-500);
        prepago.setMinutosConsumidos(-10);
        System.out.println("Saldo tras el intento inválido: "
                + LineaTelefonica.formatoPesos(prepago.getSaldo()));
        postpago.setLimiteCredito(-1);

        System.out.println("\n===== 4. OPERACIONES PREPAGO =====");
        prepago.simularLlamada(10);   // 1.500 -> saldo 3.500
        prepago.simularLlamada(40);   // 6.000 > 3.500 -> rechazada
        prepago.recargar(10000);      // saldo 13.500
        prepago.recargar(-100);       // rechazada

        System.out.println("\n===== 5. OPERACIONES POSTPAGO =====");
        postpago.simularLlamada(20);  // 3.000 -> aceptada
        postpago.simularLlamada(700); // 105.000 > límite -> rechazada
        System.out.println("Factura del mes: "
                + LineaTelefonica.formatoPesos(postpago.calcularFactura())); // 33.000

        System.out.println("\n===== 6. RESULTADOS FINALES =====");
        prepago.mostrarInformacion();
        System.out.println();
        postpago.mostrarInformacion();
    }
}