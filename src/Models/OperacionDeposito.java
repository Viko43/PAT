package Models;

public class OperacionDeposito implements Operacion {
    @Override
    public void ejecutar(Cuenta cuenta, double monto) {
        if (monto > 0) {
            cuenta.depositar(monto);
            System.out.println(" Depósito de $" + monto + " realizado con éxito.");
        } else {
            System.out.println("⚠ El monto debe ser mayor a 0.");
        }
    }
}