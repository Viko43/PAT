package Models;

public class OperacionRetiro implements Operacion {
    @Override
    public void ejecutar(Cuenta cuenta, double monto) {
        if (monto > 0 && monto <= cuenta.getSaldo()) {
            cuenta.retirar(monto);
            System.out.println(" Retiro de $" + monto + " realizado con éxito.");
        } else {
            System.out.println("⚠ Fondos insuficientes o monto inválido.");
        }
    }
}