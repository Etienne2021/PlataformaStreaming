package Contenedoras;

import Excepciones.ContraseñaNoCoincidenException;
import Excepciones.InvalidRatingException;
import Excepciones.UsuarioNoEncontradoException;
import Excepciones.UsuarioYaexisteException;
import IU.Menu;
import Interfaces.ABM;
import Usuario.Administrador;
import Usuario.Perfil;
import Usuario.Usuarios;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ContenedoraUsuario implements ABM<Usuarios> {
    private HashSet<Usuarios> usuariosHashSet;
    private HashSet<Administrador>administradores;
    private TreeMap<String, List<String>> historialInicioSesion;
    ServicioStreaming servicioStreaming=new ServicioStreaming();
    Scanner scanner=new Scanner(System.in);



    public ContenedoraUsuario() {
        this.usuariosHashSet = new HashSet<>();
        this.historialInicioSesion = new TreeMap<>();
        this.administradores=new HashSet<>();

        leerenarchivo();
        leerEnArchivoHistorial();
        leerenarchivoadmin();
    }






    public void mostrarHistorialSiExiste() {

        boolean usuarioEncontrado;
        do {
            System.out.println("Ingrese el nombre del usuario:");
            String nombre = scanner.nextLine();
            usuarioEncontrado = verificarsiexisteusuarioparahistorial(nombre);
            if (usuarioEncontrado) {
                mostrarHistorialInicioSesion(nombre);
            } else {
                System.out.println("Usuario no encontrado. Por favor, intente de nuevo.");
            }
        } while (!usuarioEncontrado);
    }

    public void eliminarUsuario() {
        boolean existe = true;
        while (existe) {
            System.out.println("Ingrese el nombre del usuario:");
            String nombre = scanner.nextLine();
            Usuarios usuarioBorrar = buscarUsuarioPorNombre(nombre);
            if (usuarioBorrar == null) {
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            } else {

                eliminar(usuarioBorrar);
                System.out.println("Usuario eliminado con éxito.");
                existe = false; // Salir del ciclo
            }
        }
    }



    public void eliminarAdministrador() {
        boolean existe=true;
        while (existe) {
            System.out.println("Ingrese el nombre del administrador a eliminar:");
            String nombre = scanner.nextLine();
            Usuarios usuarioBorrar =buscarAdministrador(nombre);
            if (usuarioBorrar == null) {
                System.out.println("Admin no encontrado. Intente nuevamente.");
            } else {

                eliminar(usuarioBorrar);
                System.out.println("Admin eliminado con éxito.");
                existe = false; // Salir del ciclo
            }
        }
    }



    public void  modificarestadousuario()
    {
        System.out.println("Ingrese el nombre del usuario");
        String nombre=scanner.nextLine();
        Usuarios usuario=buscarUsuarioPorNombre(nombre);
        if(usuario!=null)
        {
            modificar(usuario);
        }

    }
    public  void modificarestadoadmin()
    {
        System.out.println("Ingrese el nombre del admin");
        String nombre=scanner.nextLine();
        Administrador admin=buscarAdministrador(nombre);
        if(admin!=null)
        {
            modificar(admin);
        }
    }





    public void agregarNuevoAdministrador() {
        System.out.println("Ingrese el nombre del nuevo administrador:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la contraseña del nuevo administrador:");
        String contraseña = scanner.nextLine();
        // Puedes establecer el estado inicial como activo (true) o inactivo (false)
        boolean estado = true;
        Administrador nuevoAdmin = new Administrador(nombre, contraseña, estado);
        agregar(nuevoAdmin);
        System.out.println("Ingrese el nombre del perfil para el administrador");
        String nombrePerfil = scanner.nextLine();
        Perfil perfil = new Perfil(nombrePerfil);
        nuevoAdmin.agregarPerfil(perfil);
        guardarenarchivoadmin();
    }




    //Validaciones


    public void validarcontraseña(String contraseña, String confirmacontraseña) throws ContraseñaNoCoincidenException {

        if (!contraseña.equals(confirmacontraseña)) {
            throw new ContraseñaNoCoincidenException("Las contraseñas no coinciden");
        }
        if (contraseña.length() < 8) {
            throw new ContraseñaNoCoincidenException("La primer clave ingresada es muy pqueña,ingrese mas de 8 caracteres");
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
        ArrayList<Perfil> perfiles=new ArrayList<>();
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


//Agregar


    @Override
    public void agregar(Usuarios nuevoElemento) {
        if (nuevoElemento instanceof Administrador) {
            agregarAdministrador((Administrador) nuevoElemento);

        }
        else
        {
            agregarUsuario(nuevoElemento);
        }
    }

    public String agregarUsuario(Usuarios usuario) {

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

    //eliminar

    @Override
    public void eliminar(Usuarios nuevoElemento){
        if (nuevoElemento instanceof Administrador) {
            eliminarAdministrador(nuevoElemento.getNombre());

        }
        else {
            eliminarUsuario(nuevoElemento);
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

    public void eliminarUsuario(Usuarios usuario)
    {
        usuariosHashSet.remove(usuario);
        guardarenarchivo();
    }

    //Mostrar


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

    //Archivos

    public void guardarenarchivoadmin() {

        File file = new File("admin.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file,administradores);

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

    public void guardarenarchivo() {

        File file = new File("Usuarios.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, usuariosHashSet);

        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo" + e.getMessage());
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

    public Usuarios buscarUsuarioPorNombre(String nombre) {
        for (Usuarios usuario : usuariosHashSet) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }



    @Override
    public void modificar(Usuarios nuevoElemento){

        if (nuevoElemento instanceof Administrador) {
            nuevoElemento.setEstado(!nuevoElemento.isEstado());
            guardarenarchivoadmin();

        }
        else {
            nuevoElemento.setEstado(!nuevoElemento.isEstado());
            guardarenarchivo();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenedoraUsuario that = (ContenedoraUsuario) o;
        return Objects.equals(usuariosHashSet, that.usuariosHashSet) && Objects.equals(administradores, that.administradores) && Objects.equals(historialInicioSesion, that.historialInicioSesion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuariosHashSet, administradores, historialInicioSesion);
    }
}
