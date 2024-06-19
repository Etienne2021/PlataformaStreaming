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

    public void iniciarSesioncomoAdmin() {

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

        // Una vez iniciada la sesión como admin, se muestra el menú de administrador
        if (esadmin) {
            menuAdmin();
        }


    }

    public void menuAdmin() {
        int opcion;
        boolean salir = false;

        while (!salir) {
            System.out.println("\n-- Menú de Administrador --");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Cargar Película");
            System.out.println("2. Cargar Serie");
            System.out.println("3. Eliminar");
            System.out.println("4. Modificar Estado");
            System.out.println("5. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    cargarPelicula();
                    break;
                case 2:
                    cargarserie();
                    break;
                case 3:
                    eliminar();
                    break;
                case 4:
                    modificar();
                    break;
                case 5:
                    salir = true;
                    System.out.println("Sesión de administrador finalizada.");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese los datos de la película:");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año de lanzamiento: ");
        int año = scanner.nextInt();
        System.out.print("Calificación: ");
        int calificacion = scanner.nextInt();
        System.out.print("Disponible (1-Sí / 0-No): ");
        boolean disponible = scanner.nextInt() == 1;
        System.out.print("Duración (en minutos): ");
        int duracion = scanner.nextInt();

        Pelicula nuevaPelicula = new Pelicula(titulo, genero, año, calificacion, disponible, duracion);
        servicioStreaming.agregar(nuevaPelicula);

        System.out.println("Película cargada exitosamente:");
        System.out.println(nuevaPelicula);

        scanner.nextLine(); // Limpiar el buffer del scanner
    }

    public void cargarserie() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese los datos de la serie:");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año de lanzamiento: ");
        int año = scanner.nextInt();
        System.out.print("Calificación: ");
        int calificacion = scanner.nextInt();
        System.out.print("Disponible (1-Sí / 0-No): ");
        boolean disponible = scanner.nextInt() == 1;

        Serie nuevaSerie = new Serie(titulo, genero, año, calificacion, disponible);

        // Agregar episodios
        boolean agregarEpisodios = true;
        while (agregarEpisodios) {
            scanner.nextLine(); // Limpiar el buffer del scanner
            System.out.println("Ingrese los datos de un episodio:");
            System.out.print("Nombre del episodio: ");
            String nombreEpisodio = scanner.nextLine();
            System.out.print("Número del episodio: ");
            int numeroEpisodio = scanner.nextInt();
            System.out.print("Duración del episodio (en minutos): ");
            double duracionEpisodio = scanner.nextDouble();

            Episodio episodio = new Episodio(nombreEpisodio, numeroEpisodio, duracionEpisodio);
            nuevaSerie.agregarEpisodio(episodio);

            System.out.print("¿Desea agregar otro episodio? (1-Sí / 0-No): ");
            int opcion = scanner.nextInt();
            if(opcion==0)
            {
                agregarEpisodios=false;
            }
        }

        servicioStreaming.agregar(nuevaSerie);

        System.out.println("Serie cargada exitosamente:");
        System.out.println(nuevaSerie);

        scanner.nextLine();
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