package todo_jaledaor.pruebatodolist.fragmentos;

/**
 * Created by Karen on 24/10/2017.
 */

public interface IRecordatorioFragmentView {


    void habilitarControles();

    void deshabilitarControles();

    void mostrarProgress();

    void ocultarProgress();

    void recordar();

    void mostrarError(String error);

    void irALogin();

    void finalizarRecordatorio();
}
