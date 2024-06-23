package Interfaces;

import Contenido.AudioVisual;
import Usuario.Usuarios;

import java.util.HashSet;

public interface ABM<T> {

      public void agregar(T nuevoElemento);
      public void eliminar(T elemento);
      public void modificar(T elemento);



}
