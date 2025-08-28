package Views;

import java.util.Scanner;

public class CajeroView {
    private Scanner sc;
    public CajeroView() {
        sc = new Scanner(System.in);
    }

    public void mostrarCajero() {
        System.out.println("=======================");
        System.out.println("Bienvenido al cajero");
        System.out.println("=======================");
    }
    public String solicitarNumeroCuenta() {
        System.out.println("Ingresa tu numero de cuenta: ");
        return sc.nextLine();
    }
    public String solicitarPIN() {
        System.out.println("Ingresa PIN");
        return sc.nextLine();
    }
    public void mostrarMenuPrincipal() {
        System.out.println("=========================");
        System.out.println("Bienvenido"+titular);
        System.out.println("========================");
        System.out.println("1.-Consultar Saldo");
        System.out.println("2.- Retirar");
        System.out.println("3.- Depositar");
        //Poner las faltantes
        System.out.println("9.- Salir");
    }

    public int leerOpcion(){
        try{
            return Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public void mostrarSaldo() {
        System.out.println("===================");
        System.out.println("Tu saldo es:"+ saldo);
        System.out.println("====================");
    }
    public double solitarCantidad() {
        System.out.println("Ingresa la camtidad "+ operacion+" : ");
        try{
            return Double.parseDouble(sc.nextLine());
        }catch(NumberFormatException e){
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje){
        System.out.println("===="+mensaje);
    }


}
