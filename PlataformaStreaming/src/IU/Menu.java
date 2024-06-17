package IU;

import Contenedoras.ServicioStreaming;
import Excepciones.*;
import Usuario.Perfil;
import Usuario.Usuarios;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    Scanner scanner=new Scanner(System.in);
    ServicioStreaming servicioStreaming=new ServicioStreaming();



    public void iniciarSesion() {

        String nombre;

        do {
            System.out.println("Ingrese su Nombre");
             nombre= scanner.nextLine();
            System.out.println("Ingrese su  contraseña");
            String contraseña=scanner.nextLine();
            try {
                servicioStreaming.verificarsiexiste(nombre,contraseña);
                System.out.println("Inicio de sesion exitoso");
                servicioStreaming.agregarHistorialInicioSesion(nombre);
            } catch (UsuarioNoEncontradoException e) {
                System.out.println("Error al iniciar sesión: " + e.getMessage());
            }
            break;
        }while (true);

        servicioStreaming.mostrarHistorialInicioSesion(nombre);

    }



    public void cerrarSesion() {

        System.out.println("Sesion cerrada");

    }



    public void registrarse() {


        String nombre;
        String contraseña;
        String contraseñaConfirmar;


        do {

            System.out.println("Ingrese su Nombre:");
            nombre = scanner.nextLine();

            System.out.println("Ingrese su nueva contraseña (al menos 8 caracteres):");
            contraseña = scanner.nextLine();

            System.out.println("Ingrese de nuevo su nueva contraseña:");
            contraseñaConfirmar = scanner.nextLine();



            try {
                servicioStreaming.validarcontraseña(contraseña, contraseñaConfirmar);
            } catch (ContraseñaNoCoincidenException e) {
                System.out.println("Error: " + e.getMessage());
                continue; // Continuar al siguiente ciclo del bucle
            }

            try {
                servicioStreaming.verificarsiexisteusuario(nombre);
            } catch (UsuarioYaexisteException e) {
                System.out.println("Error: " + e.getMessage());
                continue; // Continuar al siguiente ciclo del bucle
            }

            // Salir del bucle si las validaciones fueron exitosas
            break;

        } while (true);

        // Si se llega aquí, se han cumplido todas las validaciones
        Usuarios usuario1 = new Usuarios(nombre, contraseña);

        System.out.println("Creando perfil...");
        System.out.println("Ingrese el nombre del nuevo perfil:");
        String nombrePerfil = scanner.nextLine();

        Perfil perfil1 = new Perfil(nombrePerfil);
        usuario1.agregarPerfil(perfil1);

        servicioStreaming.AgregarUsuario(usuario1);



        servicioStreaming.mostrarUsuarios();






    }



}
