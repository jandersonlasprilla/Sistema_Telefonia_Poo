/**
 * Hija de LineaTelefonica. El cliente paga por adelantado:
 * las llamadas se descuentan del saldo.
 */
public class LineaPrepago extends LineaTelefonica {

    // Atributo propio: acumulado de todo lo recargado
    private double totalRecargado;

    public LineaPrepago(String numero, String propietario,
                        double minutosConsumidos, double saldo) {
        super(numero, propietario, minutosConsumidos, saldo);
        this.totalRecargado = 0;
    }

    public double getTotalRecargado() { return totalRecargado; }

    /** RF-07: recarga saldo. Debe ser mayor que cero. */
    public boolean recargar(double valor) {
        if (valor <= 0) {
            System.out.println("Error: la recarga debe ser mayor que cero.");
            return false;
        }
        setSaldo(getSaldo() + valor);
        totalRecargado += valor;
        System.out.println("Recarga exitosa de " + formatoPesos(valor)
                + ". Nuevo saldo: " + formatoPesos(getSaldo()));
        return true;
    }

    /** RF-08: simula una llamada y descuenta su costo del saldo. */
    public boolean simularLlamada(double minutos) {
        if (minutos <= 0) {
            System.out.println("Error: la duración debe ser mayor que cero.");
            return false;
        }
        double costo = minutos * TARIFA_MINUTO;
        if (costo > getSaldo()) {   // RD-04
            System.out.println("Llamada rechazada: saldo insuficiente. Costo "
                    + formatoPesos(costo) + ", saldo " + formatoPesos(getSaldo()) + ".");
            return false;
        }
        setSaldo(getSaldo() - costo);
        setMinutosConsumidos(getMinutosConsumidos() + minutos);
        System.out.println("Llamada de " + minutos + " min realizada. Costo: "
                + formatoPesos(costo) + ". Saldo restante: " + formatoPesos(getSaldo()));
        return true;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("--- Línea PREPAGO ---");
        super.mostrarInformacion();
        System.out.println("Total recargado: " + formatoPesos(totalRecargado));
    }
}