package BusinessServices.Beans;

import java.io.Serializable;

/**
 *
 * @author H-URBINA-M
 */
public class BeanUsuario implements Serializable {

    private String Mode;
    private String Paterno;
    private String Materno;
    private String Nombres;
    private String Usuario;
    private String Password;
    private String Cargo;
    private String Correo;
    private String Telefono;
    private String Sistema;
    private String Estado;
    private String AreaLaboral;
    private String Modulo;
    private String Menu;
    private Boolean Autorizacion;

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getPaterno() {
        return Paterno;
    }

    public void setPaterno(String Paterno) {
        this.Paterno = Paterno;
    }

    public String getMaterno() {
        return Materno;
    }

    public void setMaterno(String Materno) {
        this.Materno = Materno;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getSistema() {
        return Sistema;
    }

    public void setSistema(String Sistema) {
        this.Sistema = Sistema;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getAreaLaboral() {
        return AreaLaboral;
    }

    public void setAreaLaboral(String AreaLaboral) {
        this.AreaLaboral = AreaLaboral;
    }

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String Modulo) {
        this.Modulo = Modulo;
    }

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String Menu) {
        this.Menu = Menu;
    }

    public Boolean getAutorizacion() {
        return Autorizacion;
    }

    public void setAutorizacion(Boolean Autorizacion) {
        this.Autorizacion = Autorizacion;
    }

}
