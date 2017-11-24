package todo_jaledaor.pruebatodolist.modelo;



/**
 * Created by jggomez on 05-Sep-17.
 */


public class Tarea {


    private int id;


    private String nombre;


    private boolean realizada;

    public Tarea() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
}
