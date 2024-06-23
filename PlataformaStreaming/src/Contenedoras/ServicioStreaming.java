package Contenedoras;
import Contenido.AudioVisual;
import Contenido.Episodio;
import Contenido.Pelicula;
import Contenido.Serie;
import IU.Menu;
import Interfaces.ABM;
import Usuario.Usuarios;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import IU.*;
import Usuario.*;

import Excepciones.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ServicioStreaming implements ABM<AudioVisual> {


    private Set<Pelicula> peliculas;
    private Set<Serie> series;
    private ArrayList<String> historialvistos;

    Scanner scanner=new Scanner(System.in);

    public ServicioStreaming() {

        this.peliculas = new HashSet<>();
        this.series = new HashSet<>();
        this.historialvistos = new ArrayList<>();


        cargarPeliculasDesdeArchivo();
        cargarSeriesDesdeArchivo();
        leerHistorialVistosDesdeArchivo();

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
        agregar(nuevaPelicula);


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
            if (opcion == 0) {
                agregarEpisodios = false;
            }
        }

        agregar(nuevaSerie);

        System.out.println("Serie cargada exitosamente:");
        System.out.println(nuevaSerie);

        scanner.nextLine();
    }

    public void eliminar() {
        System.out.println("Ingrese el titulo de la pelicula");
        String titulo = scanner.nextLine();
        AudioVisual elemento = buscarPorTitulo(titulo);
        if(elemento!=null)
        {
            eliminar(elemento);
            mostrarpelicula();
        }


    }

    public void modificar() {
        System.out.println("Ingrese el titulo de la pelicula");
        String titulo = scanner.nextLine();
        AudioVisual elemento = buscarPorTitulo(titulo);
        System.out.println(elemento);
        modificar(elemento);
        mostrarpelicula();
    }




    public void agregarVisualizacion(String nombreUsuario, String tituloVisualizado) {
        String visualizacion = nombreUsuario + " vio " + tituloVisualizado;
        historialvistos.add(visualizacion);
        guardarenarchivohistorialvistos();
    }




    @Override
    public void agregar(AudioVisual nuevoElemento) {
        if (nuevoElemento instanceof Pelicula) {
            boolean verifiacion=true;
            // Verificar si el título de la nueva película ya existe en el set
            for (Pelicula elemento : peliculas) {
                if (elemento.getTitulo().equalsIgnoreCase(nuevoElemento.getTitulo())) {
                    System.out.println("Error: Esa Pelicula/Serie ya existe.");
                    verifiacion=false;
                    break;
                }

            }
            if(verifiacion) {
                // Si el título no está repetido, agregar la película
                peliculas.add((Pelicula) nuevoElemento);
                System.out.println("Película/Serie agregada correctamente.");
                guardarPeliculasEnArchivo();
            }
        } else {
            boolean verifiacion=true;
            // Verificar si el título de la nueva película ya existe en el set
            for (Serie elemento : series) {
                if (elemento.getTitulo().equalsIgnoreCase(nuevoElemento.getTitulo())) {
                    System.out.println("Error: Esa Pelicula/Serie ya existe.");
                    verifiacion=false;
                    break;

                }
            }

            if(verifiacion) {// Si el título no está repetido, agregar la película
                series.add((Serie) nuevoElemento);
                System.out.println("Película/Serie agregada correctamente.");
                guardarSeriesEnArchivo();
            }

        }
    }

    //Eliminar


    @Override
    public void eliminar(AudioVisual elemento) {
        if (elemento instanceof Pelicula) {
            if (peliculas.remove(elemento)) {
                System.out.println("Película eliminada correctamente.");
                guardarPeliculasEnArchivo();
            } else {
                System.out.println("Error: La Película no existe.");
            }
        } else if (elemento instanceof Serie) {
            if (series.remove(elemento)) {
                System.out.println("Serie eliminada correctamente.");
                guardarSeriesEnArchivo();
            } else {
                System.out.println("Error: La Serie no existe.");

            }
        }
    }



    //Mostrar


    public void mostrarHistorialVistosPorUsuario(String nombreusuario) {
        boolean encontrado = false;
        for (String visualizacion : historialvistos) {
            if (visualizacion.startsWith(nombreusuario + " vio ")) {
                System.out.println(visualizacion);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún historial de visualizaciones para el usuario: " + nombreusuario);
        }
    }


    public void mostrarpeliculaparausuario() {
        System.out.println("Peliculas: \n\n");
        if (peliculas.isEmpty()) {
            System.out.println("No hay peliculas");
        } else {
            for (Pelicula pelicula : peliculas) {
                if(pelicula.isEstado())
                { System.out.println("Titular: " + pelicula.getTitulo()+" Genero: "+pelicula.getGenero()+" Duracion: "+pelicula.getDuracion()+" Año: "+pelicula.getAño()+"\n");
                }
            }
        }
    }


    public void mostrarpelicula() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay peliculas");
        } else {
            System.out.println("Peliculas: "+"\n");
            for (Pelicula pelicula : peliculas) {
                System.out.println(" Titulo: " + pelicula.getTitulo()+" Genero: "+pelicula.getGenero()+" Duracion: "+pelicula.getDuracion()+" Año: "+pelicula.getAño()+"\n");
            }
        }
    }

    public void mostrarseriesparausuarios()
    {
        if (series.isEmpty()) {
            System.out.println("No hay series");
        } else {
            for (Serie serie : series) {
                if(serie.isEstado())
                {System.out.println("series: " + serie.getTitulo()+"\n");
                    System.out.println("Episodios:");
                    mostrarepisodios(serie.getEpisodios());


                }
            }
        }
    }
    public void mostrarepisodios(Set<Episodio>episodios)
    {
        for (Episodio episodio:episodios)
        {
            System.out.println("Titulo: "+episodio.getTitulo()+" -NumeroCapitulo:"+episodio.getNrocap()+" -Duracion:"+episodio.getDuracionEp()+"\n");
        }

    }

    public void mostrarseries()
    {
        if (series.isEmpty()) {
            System.out.println("No hay series");
        } else {
            for (Serie serie : series) {
                System.out.println("Serie: " + serie.getTitulo()+"\n");

                System.out.println("Episodios:");
                mostrarepisodios(serie.getEpisodios());
            }
        }
    }







    public void guardarenarchivohistorialvistos() {

        File file = new File("historialvistos.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file,historialvistos);

        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo" + e.getMessage());
        }
    }





    public void leerHistorialVistosDesdeArchivo() {
        File file = new File("historialvistos.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            historialvistos = objectMapper.readValue(file, new TypeReference<ArrayList<String>>() {});
        } catch (IOException e) {
            System.out.println("No se pudo cargar el historial de visualizaciones desde el archivo: " + e.getMessage());
        }
    }


    public void guardarPeliculasEnArchivo() {
        File file = new File("Peliculas.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, peliculas);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de películas: " + e.getMessage());
        }
    }

    public void cargarPeliculasDesdeArchivo() {
        File file = new File("Peliculas.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            peliculas = objectMapper.readValue(file, new TypeReference<Set<Pelicula>>() {});
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de películas: " + e.getMessage());
        }
    }

    public void guardarSeriesEnArchivo() {
        File file = new File("Series.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, series);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de series: " + e.getMessage());
        }
    }

    public void cargarSeriesDesdeArchivo() {
        File file = new File("Series.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            series = objectMapper.readValue(file, new TypeReference<Set<Serie>>() {});
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de series: " + e.getMessage());
        }
    }

    //Buscar


    public AudioVisual buscarPorTitulo(String titulo) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                if(pelicula.isEstado())
                {
                    return pelicula;
                }
            }
        }
        for (Serie serie : series) {
            if (serie.getTitulo().equalsIgnoreCase(titulo)) {
                if(serie.isEstado())
                {
                    return serie;
                }

            }
        }
        return null;
    }


    public String buscarepisodio(int numerocap,Serie serie)
    {

        Set<Episodio>episodio=new HashSet<>();
        episodio=serie.getEpisodios();

        for(Episodio episodios: episodio)
        {
            if(episodios.getNrocap()==numerocap)
            {
                return  episodios.getTitulo();
            }
        }
        return "No se encontro";
    }


//modificar

    @Override
    public void modificar(AudioVisual elemento) {
        if (elemento == null) {
            System.out.println("Error: El elemento es nulo.");
            return;
        }

        // Modificar el estado del elemento
        elemento.setEstado(!elemento.isEstado());

        // Actualizar el estado en el archivo correspondiente
        if (elemento instanceof Pelicula) {
            guardarPeliculasEnArchivo(); // Llama al método para guardar las películas actualizadas en el archivo
        } else if (elemento instanceof Serie) {
            guardarSeriesEnArchivo(); // Llama al método para guardar las series actualizadas en el archivo
        } else {
            System.out.println("Error: Tipo de elemento no reconocido.");
        }

        System.out.println("Estado de " + elemento.getTitulo() + " modificado correctamente.");
    }





    //Reproducir

    public void reproducirserie(Serie serie) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUsted esta por reproducir la serie: " + serie.getTitulo());
        System.out.println("\n\n");
        int opcion;
        int nummin = 1;
        int nummax = serie.getEpisodios().size();
        do {
            System.out.println("\nIngrese el numero de episodio que desea reproducir: ");
            opcion = scanner.nextInt();
        }while(opcion<nummin || opcion>nummax);

        System.out.println("\nSe va a reproducir el  episodio ... \n\n"+buscarepisodio(opcion,serie));
        System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
        opcion = scanner.nextInt();

        do {

            if (opcion == 1) {
                System.out.println("\nEl episodio esta en pausa... \n\n");
                System.out.println("\nIngrese 2 para continuar con el episodio o 0 para salir.");
                opcion = scanner.nextInt();
            } else if (opcion == 2) {
                System.out.println("\nEl episodio se esta reproduciendo... \n\n");
                System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
                opcion = scanner.nextInt();
            } else if(opcion!=0) {
                System.out.println("\nCaracter invalido.");
                System.out.println("\nIngrese otro caracter.");
                opcion=scanner.nextInt();
            }

        } while (opcion != 0);

    }



    public void reproducirpelicula(Pelicula pelicula) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nReproduciendo Pelicula: " + pelicula.getTitulo());
        System.out.println("\n\n");
        int opcion;
        System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
        opcion = scanner.nextInt();
        do{
            if (opcion == 1) {
                System.out.println("\nLa Pelicula esta en pausa... \n\n");
                System.out.println("\nIngrese 2 para continuar con la pelicula o 0 para salir.");
                opcion = scanner.nextInt();
            }
            else if (opcion == 2){
                System.out.println("\nLa pelicula se esta reproduciendo... \n\n");
                System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
                opcion = scanner.nextInt();
            }
            else if (opcion != 0) {
                System.out.println("\nCaracter invalido.");
                System.out.println("\nIngrese otro caracter.");
            }
        } while (opcion != 0);

        System.out.println("\nSaliendo de la reproducción de la película.");
    }

    public void calificacion(int calificacion,AudioVisual elemento) throws InvalidRatingException
    {
        if(calificacion<1  || calificacion>5)
        {
            throw new InvalidRatingException("Calificacion invalida,debe ser entre 1 y 5");

        }else {

            if(elemento instanceof Serie)
            {
                System.out.println("Usted califico el episodio con "+calificacion+" estrella");
            }
            if(elemento instanceof  Pelicula)
            {
                System.out.println("Usted califico la pelicula con "+calificacion+" estrella");
            }

        }
    }




}
