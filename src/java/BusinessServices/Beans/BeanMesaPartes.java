/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices.Beans;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author H-URBINA-M
 */
public class BeanMesaPartes implements Serializable {

    private String Mode;
    private String Periodo;
    private String Tipo;
    private String Numero;
    private String Mes;
    private String Institucion;
    private String Area;
    private String Prioridad;
    private String Documento;
    private String NumeroDocumento;
    private String Clasificacion;
    private String Estado;
    private String Usuario;
    private String Asunto;
    private String PostFirma;
    private String UsuarioResponsable;
    private String Archivo;
    private String Referencia;
    private Date Fecha;
    private Date FechaRecepcion;
    private Integer Cantidad;
    private Integer Legajo;
    private Integer Folio;
    private Integer Decreto;

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(String Periodo) {
        this.Periodo = Periodo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String Mes) {
        this.Mes = Mes;
    }

    public String getInstitucion() {
        return Institucion;
    }

    public void setInstitucion(String Institucion) {
        this.Institucion = Institucion;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(String Prioridad) {
        this.Prioridad = Prioridad;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String Documento) {
        this.Documento = Documento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String NumeroDocumento) {
        this.NumeroDocumento = NumeroDocumento;
    }

    public String getClasificacion() {
        return Clasificacion;
    }

    public void setClasificacion(String Clasificacion) {
        this.Clasificacion = Clasificacion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String Asunto) {
        this.Asunto = Asunto;
    }

    public String getPostFirma() {
        return PostFirma;
    }

    public void setPostFirma(String PostFirma) {
        this.PostFirma = PostFirma;
    }

    public String getUsuarioResponsable() {
        return UsuarioResponsable;
    }

    public void setUsuarioResponsable(String UsuarioResponsable) {
        this.UsuarioResponsable = UsuarioResponsable;
    }

    public String getArchivo() {
        return Archivo;
    }

    public void setArchivo(String Archivo) {
        this.Archivo = Archivo;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Date getFechaRecepcion() {
        return FechaRecepcion;
    }

    public void setFechaRecepcion(Date FechaRecepcion) {
        this.FechaRecepcion = FechaRecepcion;
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Integer getLegajo() {
        return Legajo;
    }

    public void setLegajo(Integer Legajo) {
        this.Legajo = Legajo;
    }

    public Integer getFolio() {
        return Folio;
    }

    public void setFolio(Integer Folio) {
        this.Folio = Folio;
    }

    public Integer getDecreto() {
        return Decreto;
    }

    public void setDecreto(Integer Decreto) {
        this.Decreto = Decreto;
    }

}
