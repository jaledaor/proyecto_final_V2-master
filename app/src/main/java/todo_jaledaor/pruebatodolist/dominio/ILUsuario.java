package todo_jaledaor.pruebatodolist.dominio;

import todo_jaledaor.pruebatodolist.modelo.Usuario;

/**
 * Created by jggomez on 24-Oct-17.
 */

public interface ILUsuario {

    void crearUsuario(String password, Usuario usuario,
                      CallBackInteractor<String> callBackInteractor);

    void authUsuario(String email, String password,
                     CallBackInteractor<String> callBackInteractor);

    void recordarUsuario(String email,
                         CallBackInteractor<String> callBackInteractor);
}
