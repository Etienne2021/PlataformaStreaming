import Contenedoras.ServicioStreaming;
import IU.Menu;

public class Main {
    public static void main(String[] args) {


        ServicioStreaming servicioStreaming=new ServicioStreaming();
        servicioStreaming.leerenarchivo();
        Menu menu=new Menu();
        menu.iniciarSesion();


    }
}