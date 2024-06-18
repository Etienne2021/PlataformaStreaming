package IU;

import Contenedoras.ServicioStreaming;
import Contenido.AudioVisual;
import Contenido.Pelicula;
import Excepciones.*;
import Usuario.Administrador;
import Usuario.Perfil;
import Usuario.Usuarios;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Menu {

    Scanner scanner=new Scanner(System.in);
    ServicioStreaming servicioStreaming=new ServicioStreaming();
    Administrador administrador=new Administrador("Admin","12345678",true);


    public void menuprincipal(){
        int opcion;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese: \n 1- Iniciar Sesion \n 2- Crear Usuario \n 3- Iniciar Admin \n \n");
        opcion = scanner.nextInt();

        switch (opcion){
            case 1: iniciarSesion();
                break;
            case 2: registrarse();
                break;
            case 3: iniciarSesioncomoAdmin();
                break;
            default:
                System.out.println("Numero ingresado incorrecto.");
                break;
        }

    }

    public void iniciarSesion() {

        String nombre;
        do {
            System.out.println("\nIngrese su Nombre");
            nombre= scanner.nextLine();
            System.out.println("\nIngrese su  contraseña");
            String contraseña=scanner.nextLine();
            try {
                servicioStreaming.verificarsiexiste(nombre,contraseña);
                System.out.println("\nInicio de sesion exitoso");

                servicioStreaming.agregarHistorialInicioSesion(nombre);
            } catch (UsuarioNoEncontradoException e) {
                System.out.println("\nError al iniciar sesión: " + e.getMessage());
                continue;
            }
            break;
        }while (true);

        servicioStreaming.mostrarHistorialInicioSesion(nombre);

        //menuusuario

    }



    public void cerrarSesion() {

        System.out.println("\nSesion cerrada");

    }

    public boolean iniciarSesioncomoAdmin() {

        String nombre;
        boolean esadmin = false;
        Administrador administrador=new Administrador("Admin","12345678",true);
        do {
            System.out.println("\nIngrese su Nombre");
            nombre = scanner.nextLine();
            System.out.println("\nIngrese su  contraseña");
            String contraseña = scanner.nextLine();
            Administrador administradorIntento = new Administrador(nombre, contraseña, true);
            if (administradorIntento.equals(administrador)) {
                System.out.println("Inicio de sesion como Admin exitoso.");
                esadmin=true;
            } else {
                System.out.println("\nError al iniciar sesión. ");
            }
        }while(esadmin == false);


        //menu admin

        cargarPelicula();


        return esadmin;
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

        menuprincipal();
    }

    public void cargarPelicula()
    {
        Pelicula nuevaPelicula = new Pelicula("Cars","Animada",2000,5,true,140);
        Pelicula nuevaPelicula2 = new Pelicula("Rapido y Furioso","Accion",1993,5,true,140);
        Pelicula nuevaPelicula3 = new Pelicula("El señor de los Anillos","Historia",1985,4,true,140);

        HashSet<AudioVisual>peliculas=new HashSet<>();

        servicioStreaming.agregar(nuevaPelicula);
        servicioStreaming.agregar(nuevaPelicula2);
        servicioStreaming.agregar(nuevaPelicula3);

        servicioStreaming.mostrarpelicula();


    }









}
