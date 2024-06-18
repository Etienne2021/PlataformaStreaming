package Contenedoras;
import Contenido.AudioVisual;
import Contenido.Pelicula;
import Contenido.Serie;
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


public class ServicioStreaming implements ABM<AudioVisual>{

    private HashSet<Usuarios> usuariosHashSet;
    private TreeMap<String, List<String>> historialInicioSesion;
    private Set<Pelicula> peliculas;
    private Set<Serie> series;


    public ServicioStreaming() {

        this.usuariosHashSet = new HashSet<>();
        this.historialInicioSesion = new TreeMap<>();
        this.peliculas=new HashSet<>();
        this.series=new HashSet<>();
        leerenarchivo();
        leerEnArchivoHistorial();
    }



    public void validarcontraseña(String contraseña,String confirmacontraseña) throws ContraseñaNoCoincidenException
    {

        if(!contraseña.equals(confirmacontraseña))
        {
            throw  new ContraseñaNoCoincidenException("Las contraseñas no coinciden");
        }
        if(contraseña.length()<8)
        {
            throw  new ContraseñaNoCoincidenException("La primer clave ingresada es muy pqueña,ingrese mas de 8 caracteres");
        }

    }



    public String  AgregarUsuario(Usuarios usuario) {

        if (!usuariosHashSet.contains(usuario)) {
            usuariosHashSet.add(usuario);
            guardarenarchivo();
        }
        return "Nuevo usuario agregado";
    }





    public void verificarsiexiste(String nombre,String Contraseña)throws UsuarioNoEncontradoException
    {

        for (Usuarios u : usuariosHashSet) {
            if (u.getNombre().equals(nombre) && u.getContraseña().equals(Contraseña)) {
                return;
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


    public void guardarenarchivo()
    {

        File file=new File("Usuarios.json");
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
            objectMapper.writeValue(file,usuariosHashSet);
            System.out.println("Archivo guardado correctamente.");

        }catch (IOException e){
            System.out.println("No se pudo guardar el archivo"+e.getMessage());
        }

    }



    public void leerenarchivo()
    {

        File file=new File("Usuarios.json");
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
            usuariosHashSet=objectMapper.readValue(file, new TypeReference<HashSet<Usuarios>>() {});
            System.out.println("Archivo leído correctamente.");

        }catch (IOException e){
            System.out.println("No se pudo leer el archivo"+e.getMessage());
        }

    }

    public void mostrarUsuarios() {
        System.out.println("Usuarios registrados:");
        if (usuariosHashSet.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuarios usuario : usuariosHashSet) {
                System.out.println("Usuario: " + usuario.getNombre() + ", Contraseña: " + usuario.getContraseña() + ", Estado: " + usuario.isEstado()+  "Perfiles "+usuario.getPerfiles());
            }
        }
    }


    public void guardarEnArchivoHistorial() {
        File file = new File("HistorialInicioSesion.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, historialInicioSesion);
            System.out.println("Archivo de historial de inicio de sesión guardado correctamente.");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de historial de inicio de sesión: " + e.getMessage());
        }
    }

    public void leerEnArchivoHistorial() {
        File file = new File("HistorialInicioSesion.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            historialInicioSesion = objectMapper.readValue(file, new TypeReference<TreeMap<String, List<String>>>() {});
            System.out.println("Archivo de historial de inicio de sesión leído correctamente.");
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de historial de inicio de sesión: " + e.getMessage());
        }
    }

    public void agregarHistorialInicioSesion (String nombreUsuario) {

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
        if(nuevoElemento instanceof Pelicula){
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
            return true;
        }
        else{
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
            return true;

        }
    }

    /*public String eliminar(String titulo) {
        // Verificar si el título de la nueva película ya existe en el set
        for (T elemento : peliculas) {
            if (elemento.getTitulo().equals(titulo)){
                peliculas.remove(elemento);
                return "La pelicula/Serie se elimino con exito.";
            }
            else {
                return "La Pelicula/Serie no se encuentra.";
            }
        }
        return null;
    }


    public void modificar(T elemento) {
        // Implementar lógica para modificar un objeto
    }
*/
    public void mostrarpelicula() {
        System.out.println("Peliculas cargadas \n\n:");
        if (peliculas.isEmpty()) {
            System.out.println("No hay peliculas");
        } else {
            for (Pelicula pelicula : peliculas) {
                System.out.println("Pelicula cargada: " + pelicula);
            }
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
