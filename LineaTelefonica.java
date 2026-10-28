import java.util.Locale;

/**
 * Clase padre. Representa una línea telefónica genérica.
 * Aplica ENCAPSULAMIENTO: atributos privados + get/set con validaciones.
 */
public class LineaTelefonica {

    // Requerimiento RD-01: tarifa fija del minuto en Colombia
    public static final double TARIFA_MINUTO = 150;

    // Atributos PRIVADOS (RNF-01)
    private String numero;
    private String propietario;
    private double minutosConsumidos;
    private double saldo;

    // Constructor: usa los set para reutilizar las validaciones
    public LineaTelefonica(String numero, String propietario,
                           double minutosConsumidos, double saldo) {
        setNumero(numero);
        setPropietario(propietario);
        setMinutosConsumidos(minutosConsumidos);
        setSaldo(saldo);
    }

    // ---------- GET ----------
    public String getNumero() { return numero; }
    public String getPropietario() { return propietario; }
    public double getMinutosConsumidos() { return minutosConsumidos; }
    public double getSaldo() { return saldo; }

    // ---------- SET (con validaciones) ----------
    public void setNumero(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            System.out.println("Error: el número no puede estar vacío.");
            return;
        }
        this.numero = numero.trim();
    }

    public void setPropietario(String propietario) {
        if (propietario == null || propietario.trim().isEmpty()) {
            System.out.println("Error: el propietario no puede estar vacío.");
            return;
        }
        this.propietario = propietario.trim();
    }

    public void setMinutosConsumidos(double minutosConsumidos) {
        if (minutosConsumidos < 0) {
            System.out.println("Error: los minutos no pueden ser negativos (" + minutosConsumidos + ").");
            return; // el atributo conserva su valor anterior
        }
        this.minutosConsumidos = minutosConsumidos;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            System.out.println("Error: el saldo no puede ser negativo (" + formatoPesos(saldo) + ").");
            return; // el atributo conserva su valor anterior
        }
        this.saldo = saldo;
    }

    // ---------- Lógica de negocio ----------
    /** Costo del consumo mensual = minutosConsumidos * $150 (RF-06). */
    public double calcularCostoConsumo() {
        return minutosConsumidos * TARIFA_MINUTO;
    }

    public void mostrarInformacion() {
        System.out.println("Número: " + numero);
        System.out.println("Propietario: " + propietario);
        System.out.println("Minutos consumidos: " + minutosConsumidos);
        System.out.println("Saldo: " + formatoPesos(saldo));
        System.out.println("Costo del consumo: " + formatoPesos(calcularCostoConsumo()));
    }

    /** Da formato de pesos colombianos: 1500 -> $1.500 */
    public static String formatoPesos(double valor) {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,.0f", valor);
    }
}