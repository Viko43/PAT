package Controllers;

import Models.CajeroModel;
import Views.CajeroView;

public class CajeroController {
    private CajeroModel model;
    private CajeroView view;
    private boolean sistemaActivo;

    public CajeroController(CajeroModel model, CajeroView view) {
        this.model = model;
        this.view = view;
        this.sistemaActivo = true;
    }

    public void iniciarSistema() {
        view.mostrarBienvenida();
        while (sistemaActivo) {
            if (autenticarUsuario()){
                ejecutarMenuPrincipal();
            }else{
                view.mostrarMensaje("Credenciales incorrectas");
            }
        }
        view.mostrarMensaje("Grcias por usar nuestro cajero");
    }
    private boolean autenticarUsuario() {
        String numeroCuenta = view.solicitarNumeroCuenta();
        String pin = view.solicitarPin();
        return model.autenticar(numeroCuenta, pin);

    }
    private void ejecutarMenuPrincipal() {
        boolean sessionActiva = true;
        while (sessionActiva) {
            view.mostrarMenu(model.getCuentaActual().getTitular());
            int option = view.leerOpcion();
            switch (option) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    this.realizarRetiro();
                    break;
                case 3:
                    break;
                case 9:
                    break;
                default:
                    break;
            }
        }

    }
    private void consultarSaldo() {
        double saldo = model.consultarSaldo();
        view.mostrarSaldo(saldo);
    }

    public void realizarRetiro() {
        double cantidad = view.solicitarCantidad("Retirar Saldo");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad");
            return;
        }

        if(model.realizarPedido(cantidad)){
            view.mostrarMensaje("Retiro exitoso de " + cantidad);
        }else {
            view.mostrarMensaje("Saldo Insuficiente");
        }

    }

    public void realizarDeposito() {
        double cantidad = view.solicitarCantidad("Realizar Deposito");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad");
            return;
        }
        if(model.realizarPedido(cantidad)){
            view.mostrarMensaje("Deposito exitoso de " + cantidad);
        }
    }

}