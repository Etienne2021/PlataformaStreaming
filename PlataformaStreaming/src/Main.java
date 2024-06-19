import Contenedoras.ServicioStreaming;
import Excepciones.ContraseñaNoCoincidenException;
import Excepciones.InvalidRatingException;
import Excepciones.UsuarioYaexisteException;
import IU.Menu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        try
        {menu.menuprincipal();
        }
        catch (InvalidRatingException e){
            System.out.println(e.getMessage());
        }

        }
    }