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



import java.util.*;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    ServicioStreaming servicioStreaming = new ServicioStreaming();
    Administrador administrador = new Administrador("Admin", "12345678", true);


    public void menuprincipal() throws InvalidRatingException {
        int opcion;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Ingrese: \n 1- Iniciar Sesion \n 2- Crear Usuario \n 3- Iniciar Admin \n 0-Cerrar aplicacion\n \n");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                        registrarse();

                    break;
                case 3:
                    iniciarSesioncomoAdmin();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion\n Hasta la proximaaaa");
                    break;
                default:
                    System.out.println("Numero ingresado incorrecto.");

            }
        } while (opcion != 0);
    }

    public void menuAdmin() {
        int opcion;
        boolean salir = false;

        while (!salir) {
            System.out.println("\n-- Menú de Administrador --");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Cargar Película");
            System.out.println("2. Cargar Serie");
            System.out.println("3. Eliminar Pelicula o Serie");
            System.out.println("4. Modificar Estado");
            System.out.println("5. Eliminar Usuario");
            System.out.println("6. ver todas las peliculas y series");
            System.out.println("7. ver todos los usuarios registrados");
            System.out.println("8. Mostrar historial inicio de sesion de un usuario");
            System.out.println("9. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    servicioStreaming.mostrarpelicula();
                    cargarPelicula();
                    break;
                case 2:
                    servicioStreaming.mostrarseries();
                    cargarserie();
                    break;
                case 3:
                    servicioStreaming.mostrarpelicula();
                    servicioStreaming.mostrarseries();
                    eliminar();
                    break;
                case 4:
                    servicioStreaming.mostrarpelicula();
                    servicioStreaming.mostrarseries();
                    modificar();
                    break;
                case 5:
                    servicioStreaming.mostrarUsuarios();
                    eliminarUsuario();
                case 6:
                    servicioStreaming.mostrarpelicula();
                    servicioStreaming.mostrarseries();
                case 7:
                    servicioStreaming.mostrarUsuarios();

                    break;
                case 8:
                    mostrarHistorialSiExiste();
                    break;

                case 9:
                    salir = true;
                    System.out.println("Sesión de administrador finalizada.");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }

    public void menuUsuario(Usuarios actual) throws InvalidRatingException {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            try {
                System.out.println("Ingrese: \n 1- Quiero ver una Pelicula \n 2- Quiero ver una Serie \n 3-Ver mis peliculas y series vistas \n4- Salir \n");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea pendiente

                switch (opcion) {
                    case 1:
                        servicioStreaming.mostrarpeliculaparausuario();
                        System.out.println("\n\n");
                        System.out.println("Ingrese el nombre de la pelicula que desea ver: ");
                        String tituloPelicula = scanner.nextLine();
                        AudioVisual retornoPelicula = servicioStreaming.buscarPorTitulo(tituloPelicula);
                        if (retornoPelicula instanceof Pelicula) {
                            servicioStreaming.reproducirpelicula((Pelicula) retornoPelicula);
                            servicioStreaming.agregarVisualizacion(actual.getNombre(),tituloPelicula);
                            System.out.println("\n\n");
                            int calificacionPelicula;
                            do {
                                System.out.println("Ingrese su calificacion a la pelicula del 1 al 5: ");
                                calificacionPelicula = scanner.nextInt();
                                try {
                                    servicioStreaming.calificacion(calificacionPelicula, retornoPelicula);
                                    System.out.println("¡Calificación registrada con éxito!");
                                } catch (InvalidRatingException e) {
                                    System.out.println("Error" + e.getMessage());
                                    System.out.println("Por favor, ingrese una calificación válida del 1 al 5.");
                                }
                            } while (calificacionPelicula < 1 || calificacionPelicula > 5);
                        } else {
                            System.out.println("\n\n");
                            System.out.println("Titulo incorrecto. \n");
                        }
                        break;
                    case 2:
                        servicioStreaming.mostrarseriesparausuarios();
                        System.out.println("\n\n");
                        System.out.println("Ingrese el nombre de la serie que desea ver: ");
                        String tituloSerie = scanner.nextLine();
                        AudioVisual retornoSerie = servicioStreaming.buscarPorTitulo(tituloSerie);
                        if (retornoSerie instanceof Serie) {
                            servicioStreaming.reproducirserie((Serie) retornoSerie);
                            servicioStreaming.agregarVisualizacion(actual.getNombre(),tituloSerie);
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
                            } while (calificacionSerie < 1 || calificacionSerie > 5);


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
                        servicioStreaming.mostrarHistorialVistosPorUsuario(actual.getNombre());
                        break;

                    case 4:
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

   public void iniciarSesion() throws InvalidRatingException {

        String nombre;
        do {
            System.out.println("\nIngrese su Nombre");
            nombre = scanner.nextLine();
            System.out.println("\nIngrese su  contraseña");
            String contraseña = scanner.nextLine();
            try {
                Usuarios usuarioactual=new Usuarios();
                usuarioactual=servicioStreaming.verificarsiexiste(nombre, contraseña);
                System.out.println("\nInicio de sesion exitoso");
                servicioStreaming.mostrarperfiles(nombre);
                int opcionperfil;

                do {
                    System.out.println("1-seleccionar  perfil");
                    System.out.println("2-crear nuevo perfil");
                    System.out.println("3-eliminar perfil");

                    opcionperfil=scanner.nextInt();
                    scanner.nextLine();
                    boolean existe;

                    if(opcionperfil==1)
                    {
                        do{
                            System.out.println("Ingrese el nombre del perfil al que quiere ingresar");
                            nombre=scanner.nextLine();
                            existe=servicioStreaming.verificarexisteperfil(nombre,usuarioactual);
                        }while (!existe);

                    }
                    else if(opcionperfil==2)
                    {
                        System.out.println("Ingrese el nombre del nuevo perfil");
                        nombre=scanner.nextLine();
                        Perfil perfilnuevo= new Perfil(nombre);
                        usuarioactual.agregarPerfil(perfilnuevo);
                        servicioStreaming.guardarenarchivo();
                    }else if(opcionperfil==3)
                    {
                        ArrayList<Perfil> perfiles = usuarioactual.getPerfiles();
                        if (perfiles.isEmpty()) {
                            System.out.println("No hay perfiles cargados para el usuario actual.");
                            return; // Salir si no hay perfiles cargados
                        }
                        do {
                            System.out.println("Ingrese el nombre del perfil que quiere eliminar");
                            nombre=scanner.nextLine();
                            existe=servicioStreaming.verificarexisteperfil(nombre,usuarioactual);
                            if(existe)
                            {
                                usuarioactual.eliminarPerfil(nombre);
                                System.out.println("Perfil eliminado correctamente");
                                servicioStreaming.guardarenarchivo();
                            }
                            else
                            {
                                System.out.println("El perfil no existe");
                            }

                        }while (!existe);

                    }

                    else {
                        System.out.println("Opcion incorrecta");
                    }
                }while (opcionperfil!=1);

                servicioStreaming.agregarHistorialInicioSesion(nombre);
                menuUsuario(usuarioactual);
            } catch (UsuarioNoEncontradoException e) {
                System.out.println("\nError al iniciar sesión: " + e.getMessage());
                continue;
            }
            break;
        } while (true);


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



    public void mostrarHistorialSiExiste() {

        boolean usuarioEncontrado;
        do {
            System.out.println("Ingrese el nombre del usuario:");
            String nombre = scanner.nextLine();
            usuarioEncontrado = servicioStreaming.verificarsiexisteusuarioparahistorial(nombre);
            if (usuarioEncontrado) {
                servicioStreaming.mostrarHistorialInicioSesion(nombre);
            } else {
                System.out.println("Usuario no encontrado. Por favor, intente de nuevo.");
            }
        } while (!usuarioEncontrado);
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
    public void eliminarUsuario() {
        boolean existe = true;
        while (existe) {
            System.out.println("Ingrese el nombre del usuario:");
            String nombre = scanner.nextLine();
            Usuarios usuarioBorrar = servicioStreaming.buscarUsuarioPorNombre(nombre);
            if (usuarioBorrar == null) {
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            } else {

                servicioStreaming.eliminarUsuario(usuarioBorrar);
                System.out.println("Usuario eliminado con éxito.");
                existe = false; // Salir del ciclo
            }
        }
    }
    public void modificar() {
        System.out.println("Ingrese el titulo de la pelicula");
        String titulo = scanner.nextLine();
        AudioVisual elemento = servicioStreaming.buscarPorTitulo(titulo);
        System.out.println(elemento);
        servicioStreaming.modificarestado(elemento);
        servicioStreaming.mostrarpelicula();
    }



}