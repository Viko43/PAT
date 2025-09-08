package Models;
import java.util.HashMap;
import java.util.Map;


public class CajeroModel {
    private Map<String, Cuenta<String>> cuentas;
    private Cuenta<String> cuentaActual;

    public CajeroModel(){
        cuentas = new HashMap<>();
        inicializarCuentas();
    }

    private void inicializarCuentas(){
        cuentas.put("12345",  new Cuenta<>("12345", "1111", 5000, "Juan Perez"));
        cuentas.put("13579",  new Cuenta<>("13579", "2222", 100000, "Xochilt"));
        cuentas.put("10009",  new Cuenta<>("10009", "3333", 999999, "Leonardo"));
    }

    public boolean autenticar(String numeroCuenta, String pin){
        Cuenta<String> cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null && cuenta.validarPin(pin)) {
            this.cuentaActual = cuenta;
            return true;

        }
        return false;
    }

    public Cuenta<String> getCuentaActual(){
        return this.cuentaActual;
    }

    public double consultarSaldo(){
        return this.cuentaActual != null ? cuentaActual.getSaldo():0;
    }

    public String consultarPIN(){
        return this.cuentaActual != null ? cuentaActual.getPin() : null;
    }

    public boolean realizarRetiro(double cantidad){
        return cuentaActual != null && cuentaActual.retirar(cantidad);
    }

    public boolean realizarDeposito(double cantidad){
        if (cuentaActual != null && cantidad > 0) {
            cuentaActual.depositar(cantidad);
            return true;

        }
        return false;
    }


    public boolean transferir(String numeroCuentaDestino, double cantidad){
        if (cuentaActual == null || cantidad <= 0) return false;

        Cuenta<String> destino = cuentas.get(numeroCuentaDestino);
        if (destino == null) return false; // no existe
        if (destino.getNumeroCuenta().equals(cuentaActual.getNumeroCuenta())) return false;

        // Retira primero; si hay fondos, deposita en destino
        if (cuentaActual.retirar(cantidad)) {
            destino.depositar(cantidad);
            return true;
        }
        return false;
    }


    public boolean cambiarPinActual(String pinActual, String nuevoPin){
        if (cuentaActual == null) return false;
        return cuentaActual.cambiarPin(pinActual, nuevoPin);
    }

    public boolean cuentaExistente(String numeroCuenta){
        return cuentas.containsKey(numeroCuenta);
    }


}