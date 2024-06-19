package IU;

import Contenedoras.ServicioStreaming;
import Contenido.AudioVisual;
import Contenido.Episodio;
import Contenido.Pelicula;
import Contenido.Serie;
import Excepciones.*;
import Usuario.Administrador;
import Usuario.Perfil;
import Usuario.Usuarios;
import Excepciones.*;


import java.util.*;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    ServicioStreaming servicioStreaming = new ServicioStreaming();
    Administrador administrador = new Administrador("Admin", "12345678", true);


    public void menuprincipal() throws InvalidRatingException {
        int opcion;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese: \n 1- Iniciar Sesion \n 2- Crear Usuario \n 3- Iniciar Admin \n \n");
        opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                iniciarSesion();
                menuUsuario();
                break;
            case 2:
                registrarse();
                break;
            case 3:
                iniciarSesioncomoAdmin();
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
            nombre = scanner.nextLine();
            System.out.println("\nIngrese su  contraseña");
            String contraseña = scanner.nextLine();
            try {
                servicioStreaming.verificarsiexiste(nombre, contraseña);
                System.out.println("\nInicio de sesion exitoso");

                servicioStreaming.agregarHistorialInicioSesion(nombre);
            } catch (UsuarioNoEncontradoException e) {
                System.out.println("\nError al iniciar sesión: " + e.getMessage());
                continue;
            }
            break;
        } while (true);

        servicioStreaming.mostrarHistorialInicioSesion(nombre);

        //menuusuario

    }


    public void cerrarSesion() {

        System.out.println("\nSesion cerrada");

    }

    public boolean iniciarSesioncomoAdmin() {

        String nombre;
        boolean esadmin = false;
        Administrador administrador = new Administrador("Admin", "12345678", true);
        do {
            System.out.println("\nIngrese su Nombre");
            nombre = scanner.nextLine();
            System.out.println("\nIngrese su  contraseña");
            String contraseña = scanner.nextLine();
            Administrador administradorIntento = new Administrador(nombre, contraseña, true);
            if (administradorIntento.equals(administrador)) {
                System.out.println("Inicio de sesion como Admin exitoso.");
                esadmin = true;
            } else {
                System.out.println("\nError al iniciar sesión. ");
            }
        } while (esadmin == false);


        //menu admin

        cargarPelicula();
        cargarserie();
        eliminar();
        modificar();


        return esadmin;
    }

    public void registrarse() throws InvalidRatingException {


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

    public void cargarPelicula() {
        Pelicula nuevaPelicula = new Pelicula("Cars", "Animada", 2000, 5, true, 140);
        Pelicula nuevaPelicula2 = new Pelicula("Rapido y Furioso", "Accion", 1993, 5, true, 140);
        Pelicula nuevaPelicula3 = new Pelicula("El señor de los Anillos", "Historia", 1985, 4, true, 140);

        HashSet<AudioVisual> peliculas = new HashSet<>();

        servicioStreaming.agregar(nuevaPelicula);
        servicioStreaming.agregar(nuevaPelicula2);
        servicioStreaming.agregar(nuevaPelicula3);

        servicioStreaming.mostrarpelicula();
    }

    public void cargarserie() {

        Serie breakingBad = new Serie("Breaking Bad", "Drama", 2008, 5, true);
        Episodio episodio1 = new Episodio("Piloto", 1, 45.5);
        Episodio episodio2 = new Episodio("Cat's in the Bag...", 2, 48.0);
        Episodio episodio3 = new Episodio("...And the Bag's in the River", 3, 47.3);
        breakingBad.agregarEpisodio(episodio1);
        breakingBad.agregarEpisodio(episodio2);
        breakingBad.agregarEpisodio(episodio3);
        servicioStreaming.agregar(breakingBad);
        servicioStreaming.mostrarseries();
    }


    public void eliminar() {
        System.out.println("Ingrese el titulo de la pelicula");
        String titulo = scanner.nextLine();
        AudioVisual elemento = servicioStreaming.buscarPorTitulo(titulo);
        servicioStreaming.eliminar(elemento);
        servicioStreaming.mostrarpelicula();

    }

    public void modificar() {
        System.out.println("Ingrese el titulo de la pelicula");
        String titulo = scanner.nextLine();
        AudioVisual elemento = servicioStreaming.buscarPorTitulo(titulo);
        System.out.println(elemento);
        servicioStreaming.modificarestado(elemento);
        servicioStreaming.mostrarpelicula();
    }


    public void menuUsuario() throws InvalidRatingException {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            try {
                System.out.println("Ingrese: \n 1- Quiero ver una Pelicula \n 2- Quiero ver una Serie \n 3- Salir \n \n");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea pendiente

                switch (opcion) {
                    case 1:
                        servicioStreaming.mostrarpelicula();
                        System.out.println("\n\n");
                        System.out.println("Ingrese el nombre de la pelicula que desea ver: ");
                        String tituloPelicula = scanner.nextLine();
                        AudioVisual retornoPelicula = servicioStreaming.buscarPorTitulo(tituloPelicula);
                        if (retornoPelicula instanceof Pelicula) {
                            servicioStreaming.reproducirpelicula((Pelicula) retornoPelicula);
                            System.out.println("\n\n");
                            int calificacionPelicula;
                            do {
                                System.out.println("Ingrese su calificacion al episodio del 1 al 5: ");
                                calificacionPelicula = scanner.nextInt();
                                try {
                                    servicioStreaming.calificacion(calificacionPelicula, retornoPelicula);
                                    System.out.println("¡Calificación registrada con éxito!");
                                } catch (InvalidRatingException e) {
                                    System.out.println("Error" + e.getMessage());
                                    System.out.println("Por favor, ingrese una calificación válida del 1 al 5.");
                                }
                            }while (calificacionPelicula < 1 || calificacionPelicula > 5);
                        } else {
                            System.out.println("\n\n");
                            System.out.println("Titulo incorrecto. \n");
                        }
                        break;
                    case 2:
                        servicioStreaming.mostrarseries();
                        System.out.println("\n\n");
                        System.out.println("Ingrese el nombre de la serie que desea ver: ");
                        String tituloSerie = scanner.nextLine();
                        AudioVisual retornoSerie = servicioStreaming.buscarPorTitulo(tituloSerie);
                        if (retornoSerie instanceof Serie) {
                            servicioStreaming.reproducirserie((Serie) retornoSerie);
                            System.out.println("\n\n");
                            int calificacionSerie;

                            do {
                                System.out.println("Ingrese su calificacion al episodio del 1 al 5: ");
                                 calificacionSerie = scanner.nextInt();
                                try {
                                    servicioStreaming.calificacion(calificacionSerie, retornoSerie);
                                    System.out.println("¡Calificación registrada con éxito!");
                                } catch (InvalidRatingException e) {
                                    System.out.println("Error" + e.getMessage());
                                    System.out.println("Por favor, ingrese una calificación válida del 1 al 5.");
                                }
                            }while (calificacionSerie < 1 || calificacionSerie > 5);


                            int opcion1;
                            do {
                                System.out.println(" 1-Volver al menu.  2-Reproducir otro capitulo");
                                opcion1 = scanner.nextInt();

                                if (opcion1 == 2) {
                                    servicioStreaming.reproducirserie((Serie) retornoSerie);
                                }
                            } while (opcion1 != 1 && opcion1 != 2);
                        } else {
                            System.out.println("\n\n");
                            System.out.println("Titulo incorrecto. \n");
                        }
                        break;
                    case 3:
                        menuprincipal();
                        salir = true;
                        break;
                    default:
                        System.out.println("Numero ingresado incorrecto.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine(); // Limpiar el búfer del scanner
            }
        }
    }

}