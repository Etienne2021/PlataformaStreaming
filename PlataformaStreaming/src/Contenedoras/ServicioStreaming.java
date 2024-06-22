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

import Usuario.*;

import Excepciones.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ServicioStreaming implements ABM<AudioVisual> {

    private HashSet<Usuarios> usuariosHashSet;
    private HashSet<Administrador>administradores;
    private TreeMap<String, List<String>> historialInicioSesion;
    private Set<Pelicula> peliculas;
    private Set<Serie> series;
    private ArrayList<String> historialvistos;


    public ServicioStreaming() {

        this.usuariosHashSet = new HashSet<>();
        this.historialInicioSesion = new TreeMap<>();
        this.peliculas = new HashSet<>();
        this.series = new HashSet<>();
        this.historialvistos = new ArrayList<>();
        this.administradores=new HashSet<>();
        leerenarchivo();
        leerEnArchivoHistorial();
        cargarPeliculasDesdeArchivo();
        cargarSeriesDesdeArchivo();
        leerHistorialVistosDesdeArchivo();
        leerenarchivoadmin();
    }


    public void validarcontraseña(String contraseña, String confirmacontraseña) throws ContraseñaNoCoincidenException {

        if (!contraseña.equals(confirmacontraseña)) {
            throw new ContraseñaNoCoincidenException("Las contraseñas no coinciden");
        }
        if (contraseña.length() < 8) {
            throw new ContraseñaNoCoincidenException("La primer clave ingresada es muy pqueña,ingrese mas de 8 caracteres");
        }

    }


    public String AgregarUsuario(Usuarios usuario) {

        if (!usuariosHashSet.contains(usuario)) {
            usuariosHashSet.add(usuario);
            guardarenarchivo();
        }
        return "Nuevo usuario agregado";
    }



    public void agregarAdministrador(Administrador administrador) {
        if (buscarAdministrador(administrador.getNombre()) == null) {
            if (administradores.add(administrador)) {
                System.out.println("Administrador agregado: " + administrador);
                guardarenarchivoadmin();
            } else {
                System.out.println("El administrador no pudo ser agregado.");
            }
        } else {
            System.out.println("Ya existe un administrador con el nombre: " + administrador.getNombre());
        }
    }


    public void eliminarAdministrador(String nombre) {
        Administrador admin = buscarAdministrador(nombre);
        if (admin != null) {
            if (!admin.getNombre().equals("Admin")) {
                administradores.remove(admin);
                System.out.println("Administrador eliminado: " + admin);
                guardarenarchivoadmin();
            } else {
                System.out.println("No se puede eliminar el administrador predeterminado 'Admin'.");
            }
        } else {
            System.out.println("No se encontró el administrador con nombre: " + nombre);
        }
    }


    public Administrador buscarAdministrador(String nombre) {
        for (Administrador admin : administradores) {
            if (admin.getNombre().equals(nombre)) {
                return admin;
            }
        }
        return null;
    }


    public void mostrarAdministradores() {
        if (administradores.isEmpty()) {
            System.out.println("No hay administradores registrados.");
        } else {
            System.out.println("Lista de administradores:");
            for (Administrador admin : administradores) {
                System.out.println(admin);
            }
        }
    }


    public void guardarenarchivoadmin() {

        File file = new File("admin.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file,administradores);

        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo" + e.getMessage());
        }
    }


    public void leerenarchivoadmin() {
        File file = new File("admin.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (file.exists() && file.length() > 0) {
                administradores = objectMapper.readValue(file, new TypeReference<HashSet<Administrador>>() {});
            } else {
                // Si el archivo está vacío o no existe, agregamos un administrador por defecto
                administradores = new HashSet<>();
                Administrador adminDefault = new Administrador("Admin", "12345678", true);
                administradores.add(adminDefault);
                objectMapper.writeValue(file, administradores); // Guardamos el administrador por defecto en el archivo
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }







    public Usuarios verificarsiexiste(String nombre, String Contraseña) throws UsuarioNoEncontradoException {

        for (Usuarios u : usuariosHashSet) {
            if (u.getNombre().equals(nombre) && u.getContraseña().equals(Contraseña)) {
                return u;
            }
        }
        throw new UsuarioNoEncontradoException("Nombre de usuario o contraseña incorrecta");

    }


    public void verificarsiexisteusuario(String nombre) throws UsuarioYaexisteException {
        for (Usuarios u : usuariosHashSet) {
            if (u.getNombre().equals(nombre)) {
                throw new UsuarioYaexisteException("Nombre de usuario ya está en uso.");
            }
        }
    }


    public boolean verificarsiexisteusuarioparahistorial(String nombre) {
        for (Usuarios u : usuariosHashSet) {
            if (u.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }


    public boolean verificarexisteperfil(String nombreperfil,Usuarios usuarios)
    {
        ArrayList<Perfil>perfiles=new ArrayList<>();
        perfiles=usuarios.getPerfiles();

        for (Perfil perfil: perfiles)
        {
            if(perfil.getNombre().equals(nombreperfil))
            {
                return true;
            }
        }
        return false;
    }





    public void guardarenarchivo() {

        File file = new File("Usuarios.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, usuariosHashSet);

        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo" + e.getMessage());
        }

    }

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




    public void agregarVisualizacion(String nombreUsuario, String tituloVisualizado) {
        String visualizacion = nombreUsuario + " vio " + tituloVisualizado;
        historialvistos.add(visualizacion);
        guardarenarchivohistorialvistos();
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





    public void leerenarchivo() {

        File file = new File("Usuarios.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            usuariosHashSet = objectMapper.readValue(file, new TypeReference<HashSet<Usuarios>>() {
            });

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo" + e.getMessage());
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




    public void mostrarUsuarios() {
        System.out.println("Usuarios registrados: \n");
        if (usuariosHashSet.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuarios usuario : usuariosHashSet) {
                System.out.println("Usuario: " + usuario.getNombre() + ", Contraseña: " + usuario.getContraseña() + ", Estado: " + usuario.isEstado() + "Perfiles " + usuario.getPerfiles());
            }
        }
    }


    public void guardarEnArchivoHistorial() {
        File file = new File("HistorialInicioSesion.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, historialInicioSesion);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de historial de inicio de sesión: " + e.getMessage());
        }
    }

    public void leerEnArchivoHistorial() {
        File file = new File("HistorialInicioSesion.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            historialInicioSesion = objectMapper.readValue(file, new TypeReference<TreeMap<String, List<String>>>() {
            });
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de historial de inicio de sesión: " + e.getMessage());
        }
    }

    public void agregarHistorialInicioSesion(String nombreUsuario) {

        // Obtener la lista de fechas y horas de inicio de sesión del usuario, creándola si no existe
        List<String> historialUsuario = historialInicioSesion.get(nombreUsuario);

        // Si el historial del usuario es nulo (es decir, no existe), crear una nueva lista
        if (historialUsuario == null) {
            historialUsuario = new ArrayList<>();
            historialInicioSesion.put(nombreUsuario, historialUsuario);
        }

        // Agregar la fecha y hora actual al historial del usuario
        LocalDateTime fechaHora = LocalDateTime.now();
        String fechaComoString = fechaHora.toString();
        historialUsuario.add(fechaComoString);

        // Guardar el historial actualizado en el archivo
        guardarEnArchivoHistorial();
    }

    public void mostrarHistorialInicioSesion(String nombreUsuario) {
        List<String> historial = historialInicioSesion.get(nombreUsuario);
        if (historial == null || historial.isEmpty()) {
            System.out.println("No hay registros de inicio de sesión para el usuario: " + nombreUsuario);
        } else {
            System.out.println("Historial de inicio de sesión para el usuario: " + nombreUsuario);
            for (String fechaHora : historial) {
                System.out.println(fechaHora);
            }
        }
    }

    @Override
    public boolean agregar(AudioVisual nuevoElemento) {
        if (nuevoElemento instanceof Pelicula) {
            // Verificar si el título de la nueva película ya existe en el set
            for (Pelicula elemento : peliculas) {
                if (elemento.getTitulo().equalsIgnoreCase(nuevoElemento.getTitulo())) {
                    System.out.println("Error: Esa Pelicula/Serie ya existe.");
                    return false; // No se puede agregar la película
                }
            }

            // Si el título no está repetido, agregar la película
            peliculas.add((Pelicula) nuevoElemento);
            System.out.println("Película/Serie agregada correctamente.");
            guardarPeliculasEnArchivo();
            return true;
        } else {
            // Verificar si el título de la nueva película ya existe en el set
            for (Serie elemento : series) {
                if (elemento.getTitulo().equalsIgnoreCase(nuevoElemento.getTitulo())) {
                    System.out.println("Error: Esa Pelicula/Serie ya existe.");
                    return false; // No se puede agregar la película
                }
            }

            // Si el título no está repetido, agregar la película
            series.add((Serie) nuevoElemento);
            System.out.println("Película/Serie agregada correctamente.");
            guardarSeriesEnArchivo();
            return true;

        }
    }

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

    public Usuarios buscarUsuarioPorNombre(String nombre) {
        for (Usuarios usuario : usuariosHashSet) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }



    public void eliminarUsuario(Usuarios usuario)
    {
        usuariosHashSet.remove(usuario);
        guardarenarchivo();
    }

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

    public void modificarestado(AudioVisual elemento) {
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

    public void  modicarestadousuario(Usuarios usuario)
    {
        usuario.setEstado(!usuario.isEstado());
        System.out.println("Se modifico el estado del usuario");
        System.out.println("Estado de "+usuario.getNombre()+"es"+usuario.isEstado());
        guardarenarchivo();
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



    public void mostrarperfiles(String nombre)
    {
        for(Usuarios usuario:usuariosHashSet)
        {
            if(usuario.getNombre().equals(nombre))
            {
                mostrarNombrePerfiles(usuario.getPerfiles());
            }
        }
        System.out.println("\n");
    }

    public void mostrarNombrePerfiles(ArrayList<Perfil> perfiles){
        System.out.println("Perfiles: ");
        for(Perfil perfil:perfiles) {
            System.out.println(perfil.getNombre());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicioStreaming that)) return false;
        return Objects.equals(usuariosHashSet, that.usuariosHashSet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuariosHashSet);
    }


}
