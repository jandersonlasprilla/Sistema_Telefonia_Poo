/**
 * Hija de LineaTelefonica. El cliente paga al final del mes:
 * las llamadas se acumulan y se factura cargo fijo + consumo.
 */
public class LineaPostpago extends LineaTelefonica {

    private double limiteCredito;
    private double cargoFijoMensual;

    public LineaPostpago(String numero, String propietario,
                         double minutosConsumidos, double saldo,
                         double limiteCredito, double cargoFijoMensual) {
        super(numero, propietario, minutosConsumidos, saldo);
        setLimiteCredito(limiteCredito);
        setCargoFijoMensual(cargoFijoMensual);
    }

    public double getLimiteCredito() { return limiteCredito; }
    public double getCargoFijoMensual() { return cargoFijoMensual; }

    public void setLimiteCredito(double limiteCredito) {
        if (limiteCredito < 0) {
            System.out.println("Error: el límite de crédito no puede ser negativo.");
            return;
        }
        this.limiteCredito = limiteCredito;
    }

    public void setCargoFijoMensual(double cargoFijoMensual) {
        if (cargoFijoMensual < 0) {
            System.out.println("Error: el cargo fijo no puede ser negativo.");
            return;
        }
        this.cargoFijoMensual = cargoFijoMensual;
    }

    /** RF-09: simula una llamada respetando el límite de crédito. */
    public boolean simularLlamada(double minutos) {
        if (minutos <= 0) {
            System.out.println("Error: la duración debe ser mayor que cero.");
            return false;
        }
        double costo = minutos * TARIFA_MINUTO;
        double consumoAcumulado = calcularCostoConsumo() + costo;
        if (consumoAcumulado > limiteCredito) {   // RD-05
            System.out.println("Llamada rechazada: superaría el límite de crédito ("
                    + formatoPesos(limiteCredito) + ").");
            return false;
        }
        setMinutosConsumidos(getMinutosConsumidos() + minutos);
        System.out.println("Llamada de " + minutos + " min realizada. Costo: "
                + formatoPesos(costo) + ". Consumo acumulado: "
                + formatoPesos(calcularCostoConsumo()));
        return true;
    }

    /** RF-10: factura = cargo fijo + consumo. */
    public double calcularFactura() {
        return cargoFijoMensual + calcularCostoConsumo();
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("--- Línea POSTPAGO ---");
        super.mostrarInformacion();
        System.out.println("Límite de crédito: " + formatoPesos(limiteCredito));
        System.out.println("Cargo fijo mensual: " + formatoPesos(cargoFijoMensual));
        System.out.println("Factura actual: " + formatoPesos(calcularFactura()));
    }
}