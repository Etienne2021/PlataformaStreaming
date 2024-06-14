package IU;

import Contenedoras.ServicioStreaming;
import Excepciones.ContraseñaNoCoincidenException;
import Excepciones.UsuarioNoEncontradoException;
import Usuario.Perfil;
import Usuario.Usuarios;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    Scanner scanner=new Scanner(System.in);
    ServicioStreaming servicioStreaming=new ServicioStreaming();


    public void menu() {
        // Implementar lógica del menú principal
        // Este método puede mostrar opciones al usuario y llamar a otros métodos como menuSeries o menuPeliculas
    }


    public void menuSeries() {
        // Implementar lógica del menú de series
        // Este método puede mostrar una lista de series y permitir al usuario reproducir o calificar una serie
    }



    public void menuPeliculas() {
        // Implementar lógica del menú de películas
        // Este método puede mostrar una lista de películas y permitir al usuario reproducir o calificar una película
    }

    public void iniciarSesion() {

        System.out.println("Ingrese su Nombre");
        String nombre= scanner.nextLine();
        System.out.println("Ingrese su  contraseña");
        String contraseña=scanner.nextLine();
        try {
            servicioStreaming.verificarsiexiste(nombre,contraseña);
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }


    }

    public void cerrarSesion() {

        System.out.println("Sesion cerrada");

    }

    public void registrarse() {

        System.out.println("Ingrese su Nombre");
        String nombre= scanner.nextLine();
        System.out.println("Ingrese su nueva contraseña");
        String contraseña=scanner.nextLine();
        System.out.println("Ingrese devuelta su nueva contraseña");
        String contraseñaconfirmar=scanner.nextLine();

        try {
           servicioStreaming.validarcontraseña(contraseña,contraseñaconfirmar);
        } catch (ContraseñaNoCoincidenException e)
        {
            System.out.println("No se puede registrar"+e.getMessage());
        }

        Usuarios usuario1=new Usuarios(nombre,contraseña);

        System.out.println("Creando perfil");
        System.out.println("Ingrese el nombre del nuevo perfil");
        String nombreperfil=scanner.nextLine();
        Perfil perfil1=new Perfil(nombreperfil);
        usuario1.agregarPerfil(perfil1);
        servicioStreaming.AgregarUsuario(usuario1);

    }





}
