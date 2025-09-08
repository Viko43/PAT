package Controllers;
import Models.CajeroModel;
import Views.CajeroView;


public class CajeroController {
    private CajeroModel model;
    private CajeroView view;
    private boolean sistemaActivo;


    public CajeroController(CajeroModel model, CajeroView view){
        this.model = model;
        this.view = view;
        this.sistemaActivo = true;
    }


    public void iniciarSistema(){
        view.mostrarBienvenida();
        while (sistemaActivo) {
            if (autenticarUsuario()) {
                ejecutarMenuPrincipal();
            }else{
                view.mostrarMensaje("Credenciales incorrectas ");
            }
        }
        view.mostrarMensaje("Gracias por usar nuestro cajero ");
    }


    private boolean autenticarUsuario(){
        String numeroCuenta = view.solicitarNumeroCuenta();
        String pin = view.solcitarPin();
        return model.autenticar(numeroCuenta, pin);
    }

    private void ejecutarMenuPrincipal(){
        boolean sessionActiva = true;
        while (sessionActiva) {
            view.mostrarMenuPrincipal(model.getCuentaActual().getTitular());
            int opcion = view.leerOpcion();
            switch (opcion) {
                case 1:
                    consultarSaldo();

                    break;

                case 2:
                    this.realizarRetiro();
                    break;

                case 3:
                    this.realizarDeposito();
                    break;

                case 4:
                    this.realizarTransferencia();
                    break;

                case 5:
                    this.realizarCambioPin();
                    break;

                case 6:
                    this.mostrarPin();
                case 7:
                    this.cerrarSistema();
                    sessionActiva = false;   // salir del menú
                    sistemaActivo = false;   // apagar el sistema completo
                    break;

                default:
                    view.mostrarMensaje("Opción inválida, intenta de nuevo");
            }
        }
    }


    public void consultarSaldo(){
        double saldo = model.consultarSaldo();
        view.mostrarSaldo(saldo);
    }

    public void mostrarPin(){
        String pin = model.consultarPIN();  // usar String, no double
        if(pin != null){
            view.mostrarMensaje("Tu PIN actual es: " + pin);
        } else {
            view.mostrarMensaje("No hay cuenta activa.");
        }
    }


    public void realizarRetiro(){
        double cantidad = view.solicitarCantidad("Retirar");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad ");
            return;
        }

        if (model.realizarRetiro(cantidad)) {
            view.mostrarMensaje("Retiro exitoso de "+cantidad);
        }else{
            view.mostrarMensaje("Fondos insuficientes ");
        }
    }

    public void realizarDeposito(){
        double cantidad = view.solicitarCantidad("Deposito ");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad ");
            return;
        }
        if (model.realizarDeposito(cantidad)) {
            view.mostrarMensaje("Deposito exitoso por la cantidad de "+cantidad);
        }else{
            view.mostrarMensaje("Fondos insuficientes ");
        }
    }


    public void realizarTransferencia(){
        String cuentaDestino = view.solicitarCuentaDestino();

        if (!model.cuentaExistente(cuentaDestino)) {
            view.mostrarMensaje("La cuenta destino no existe");
            return;
        }

        String cuentaOrigen = model.getCuentaActual().getNumeroCuenta();
        if (cuentaOrigen.equals(cuentaDestino)) {
            view.mostrarMensaje("No puedes transferir a la MISMA cuenta");
            return;
        }

        double cantidad = view.solicitarCantidad("transferir");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad ");
            return;
        }


        boolean ok = model.transferir(cuentaDestino, cantidad);
        if (ok) {
            view.mostrarMensaje("Transferencia exitosa de $" + cantidad + " a la cuenta " + cuentaDestino);
        } else {
            view.mostrarMensaje("No se pudo completar la transferencia (fondos insuficientes o datos invalidos)");
        }
    }


    public void realizarCambioPin(){
        String pinActual = view.solcitarPin();
        String nuevo = view.solicitarNuevoPin();
        String confirm = view.solicitarConfirmacionPin();

        if (!nuevo.equals(confirm)) {
            view.mostrarMensaje("El nuevo PIN y la confirmación no coinciden.");
            return;
        }

        if (!nuevo.matches("\\d{4}")) {
            view.mostrarMensaje("El PIN debe contener exactamente 4 dígitos.");
            return;
        }

        boolean actualizado = model.cambiarPinActual(pinActual, nuevo);
        if (actualizado) {
            view.mostrarMensaje("PIN actualizado correctamente. Ahora puedes confirmar en 'Ver PIN'.");
        } else {
            view.mostrarMensaje("Error: PIN actual incorrecto o no se pudo actualizar.");
        }
    }

    public void cerrarSistema(){
        view.cerrar();
    }
}