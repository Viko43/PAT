package Controllers;

import Models.CajeroModel;
import Models.Cuenta;
import Models.Operacion;
import Models.OperacionRetiro;
import Models.OperacionDeposito;
import Models.OperacionConsulta;
import Views.CajeroView;


public class CajeroController {
    private CajeroModel model;
    private CajeroView view;
    private boolean sistemaActivo;

    private Operacion operacion; // Estrategia actual

    public CajeroController(CajeroModel model, CajeroView view){
        this.model = model;
        this.view = view;
        this.sistemaActivo = true;
    }

    // Setter de estrategia
    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    // Ejecutar estrategia
    public void ejecutarOperacion(double monto) {
        if (operacion != null && model.getCuentaActual() != null) {
            Cuenta cuenta = model.getCuentaActual();
            operacion.ejecutar(cuenta, monto);
        } else {
            view.mostrarMensaje("No hay operación definida o cuenta activa.");
        }
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
                case 1: // Consulta saldo
                    setOperacion(new OperacionConsulta());
                    ejecutarOperacion(0);
                    break;

                case 2: // Retiro
                    double retiro = view.solicitarCantidad("Retirar");
                    setOperacion(new OperacionRetiro());
                    ejecutarOperacion(retiro);
                    break;

                case 3: // Depósito
                    double deposito = view.solicitarCantidad("Depositar");
                    setOperacion(new OperacionDeposito());
                    ejecutarOperacion(deposito);
                    break;

                case 4: // Transferencia (aún no la migramos a Strategy porque requiere 2 cuentas)
                    this.realizarTransferencia();
                    break;

                case 5: // Cambio de PIN
                    this.realizarCambioPin();
                    break;

                case 6: // Ver PIN
                    this.mostrarPin();

                case 7: // Salir
                    this.cerrarSistema();
                    sessionActiva = false;   // salir del menú
                    sistemaActivo = false;   // apagar el sistema completo
                    sessionActiva = false;
                    sistemaActivo = false;
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
        String pin = model.consultarPIN();
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


    // Transferencia todavía no la pasamos a Strategy (puede hacerse después)
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
            view.mostrarMensaje("No se pudo completar la transferencia (fondos insuficientes o datos inválidos)");
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