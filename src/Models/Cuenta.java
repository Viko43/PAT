package Models;

public class Cuenta<T> {
    private T numeroCuenta;  // Número de cuenta genérico (String, Integer, etc.)
    private String pin;      // PIN de 4 dígitos
    private double saldo;    // Saldo en la cuenta
    private T titular;       // Titular de la cuenta (String u otro objeto)


    public Cuenta(T numeroCuenta, String pin, double saldo, T titular) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldo;
        this.titular = titular;
    }

    // Getters y Setters

    public T getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(T numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public T getTitular() {
        return titular;
    }
    public void setTitular(T titular) {
        this.titular = titular;
    }

    public boolean validarPin(String pinIngresado) {
        return this.pin.equals(pinIngresado);
    }

    public boolean retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= this.saldo) {
            saldo -= cantidad;
            return true;
        }
        return false;
    }

    public boolean cambiarPin(String pinActual, String nuevoPin) {
        if (pinActual == null || nuevoPin == null) return false;
        if (!this.pin.equals(pinActual)) return false;
        if (!nuevoPin.matches("\\d{4}")) return false;
        this.pin = nuevoPin;
        return true;
    }

    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
    }
}