package Views;

import java.util.Scanner;

public class CajeroView {
    private Scanner scanner;
    public CajeroView() {
        scanner = new Scanner(System.in);
    }
    public void mostrarBienvenida(){
        System.out.println("===========");
        System.out.println("Bienvenido al Cajero");
        System.out.println("============");
    }
    public String solicitarNumeroCuenta(){
        System.out.println("Ingresa tu numero de cuenta: ");
        return scanner.nextLine();
    }
    public String solicitarPin(){
        System.out.println("Ingresa una pin: ");
        return scanner.nextLine();
    }
    public void mostrarMenu(String titular){
        System.out.println("==================");
        System.out.println("Bienvenido: " + titular);
        System.out.println("==================");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Retirar saldo");
        System.out.println("3. Depositar saldo");
        //definir las opciones faltantes
        System.out.println("9. Salir");

    }

    public int leerOpcion(){
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch(NumberFormatException e){
            return -1;
        }
    }
    public void mostrarSaldo(double saldo){
        System.out.println("================");
        System.out.println("tu saldo actual es: $" + saldo);
        System.out.println("===============");

    }

    public double solicitarCantidad(String operacion){
        System.out.println("Ingrese la cantidad a " + operacion+": ");
        try{
            return Double.parseDouble(scanner.nextLine());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje){
        System.out.println("===== "+mensaje);
    }
    //personalizar mensajes de error y de exito
    //metodo para salir y cerrar el scanner
}
