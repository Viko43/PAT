package Models;

import java.util.HashMap;
import java.util.Map;


public class CajeroModel {
    private Map<String, Cuenta> cuentas;
    private Cuenta cuentaActual;

    public CajeroModel() {
        cuentas = new HashMap<>();
        inicializarCuentas();
    }

    private void inicializarCuentas() {
        cuentas.put("12345",
                new Cuenta( "12345",
                        "1111",
                        5000,
                        "Juan Perez"));
        cuentas.put("54321",
                new Cuenta("54321",
                        "2222",
                        10000,
                        "Pablo Reyes"));
        cuentas.put("13142",
                new Cuenta("13142",
                        "3333",
                        20000,
                        "Tocayo Perez"));
    }

    public boolean autenticar(String numeroCuenta, String pin) {
        Cuenta cuenta= cuentas.get(numeroCuenta);
        if(cuentas != null && cuenta.valdiarPin(pin)){
            this.cuentaActual = cuenta;
            return true;
        }
        return false;
    }
    public Cuenta getCuentaActual(){
        return this.cuentaActual;
    }

    public double consultarSaldo(){
        return this.cuentaActual != null? cuentaActual.getSaldo():0;
    }
    public boolean realizarPedido(double cantidad) {
        if (cuentaActual != null && cantidad > 0) {
            cuentaActual.depositar(cantidad);
            return true;
        }return false;
    }
    public boolean cuentaExistente(String numeroCuenta){
        return cuentas.containsKey(numeroCuenta);
    }
}
    //tarea  definir el metodo para transferir
