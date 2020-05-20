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
public class BeanPersonas implements Serializable {

    private String Mode;
    private String Paterno;
    private String Materno;
    private String Nombres;
    private String Instituto;
    private String DNI;
    private String CIP;
    private String GradoMilitar;
    private String GradoPensionable;
    private String RegimenPensionario;
    private String TipoPension;
    private String Banco;
    private String CCI;
    private String CuentaBancaria;
    private Date FechaNacimiento;
    private Date FechaFallecido;
    private Date FechaEgreso;
    private Date FechaRetiro;
    private Date FechaAltaCPMP;
    private Date FechaBajaCPMP;
    private Integer Ano;
    private Integer Mes;
    private Integer Dias;

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

    public String getInstituto() {
        return Instituto;
    }

    public void setInstituto(String Instituto) {
        this.Instituto = Instituto;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCIP() {
        return CIP;
    }

    public void setCIP(String CIP) {
        this.CIP = CIP;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaFallecido() {
        return FechaFallecido;
    }

    public void setFechaFallecido(Date FechaFallecido) {
        this.FechaFallecido = FechaFallecido;
    }

    public Date getFechaEgreso() {
        return FechaEgreso;
    }

    public void setFechaEgreso(Date FechaEgreso) {
        this.FechaEgreso = FechaEgreso;
    }

    public Date getFechaRetiro() {
        return FechaRetiro;
    }

    public void setFechaRetiro(Date FechaRetiro) {
        this.FechaRetiro = FechaRetiro;
    }

    public Date getFechaAltaCPMP() {
        return FechaAltaCPMP;
    }

    public void setFechaAltaCPMP(Date FechaAltaCPMP) {
        this.FechaAltaCPMP = FechaAltaCPMP;
    }

    public Date getFechaBajaCPMP() {
        return FechaBajaCPMP;
    }

    public void setFechaBajaCPMP(Date FechaBajaCPMP) {
        this.FechaBajaCPMP = FechaBajaCPMP;
    }

    public String getGradoMilitar() {
        return GradoMilitar;
    }

    public void setGradoMilitar(String GradoMilitar) {
        this.GradoMilitar = GradoMilitar;
    }

    public String getGradoPensionable() {
        return GradoPensionable;
    }

    public void setGradoPensionable(String GradoPensionable) {
        this.GradoPensionable = GradoPensionable;
    }

    public String getRegimenPensionario() {
        return RegimenPensionario;
    }

    public void setRegimenPensionario(String RegimenPensionario) {
        this.RegimenPensionario = RegimenPensionario;
    }

    public String getTipoPension() {
        return TipoPension;
    }

    public void setTipoPension(String TipoPension) {
        this.TipoPension = TipoPension;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String Banco) {
        this.Banco = Banco;
    }

    public String getCCI() {
        return CCI;
    }

    public void setCCI(String CCI) {
        this.CCI = CCI;
    }

    public String getCuentaBancaria() {
        return CuentaBancaria;
    }

    public void setCuentaBancaria(String CuentaBancaria) {
        this.CuentaBancaria = CuentaBancaria;
    }

    public Integer getAno() {
        return Ano;
    }

    public void setAno(Integer Ano) {
        this.Ano = Ano;
    }

    public Integer getMes() {
        return Mes;
    }

    public void setMes(Integer Mes) {
        this.Mes = Mes;
    }

    public Integer getDias() {
        return Dias;
    }

    public void setDias(Integer Dias) {
        this.Dias = Dias;
    }

}
