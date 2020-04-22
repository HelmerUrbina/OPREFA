package BusinessServices.Beans;

import java.io.Serializable;

public class BeanMenu implements Serializable {

    private String Mode;
    private String Modulo;
    private String Codigo;
    private String Nombre;
    private String Servlet;
    private String Modo;
    private String Estado;

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String Modulo) {
        this.Modulo = Modulo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getServlet() {
        return Servlet;
    }

    public void setServlet(String Servlet) {
        this.Servlet = Servlet;
    }

    public String getModo() {
        return Modo;
    }

    public void setModo(String Modo) {
        this.Modo = Modo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

}
