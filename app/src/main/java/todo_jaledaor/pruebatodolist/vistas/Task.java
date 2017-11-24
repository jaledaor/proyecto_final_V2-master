package todo_jaledaor.pruebatodolist.vistas;

public class Task {
    private String pregunta;
    private String respuesta;
    private String categoria;
    private String fecha;
    private boolean respondida;
    private String uid_preg;
    private String uid_resp;


    public Task() {
    }

    public Task(String pregunta, String categoria, String respuesta, String fecha, boolean respondida, String uid_preg, String uid_resp) {
        this.pregunta = pregunta;
        this.categoria = categoria;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.respondida = respondida;
        this.uid_preg= uid_preg;
        this.uid_resp=uid_resp;

    }


    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }

    public String getUid_preg() {
        return uid_preg;
    }

    public void setUid_preg(String uid_preg) {
        this.uid_preg = uid_preg;
    }

    public String getUid_resp() {
        return uid_resp;
    }

    public void setUid_resp(String uid_resp) {
        this.uid_resp = uid_resp;
    }
}