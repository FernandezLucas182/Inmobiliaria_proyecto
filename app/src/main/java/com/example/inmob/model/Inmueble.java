package com.example.inmob.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Inmueble implements Serializable {
    @Expose
    private int idInmueble;
    @Expose
    private String direccion;
    @Expose
    private String tipo;
    @Expose
    private String uso;
    @Expose
    private int ambientes;
    @Expose
    private int superficie;
    @Expose
    private double latitud;
    @Expose
    private double longitud;;
    @Expose
    private double valor;
    @Expose
    private String  imagen;
    @Expose
    private boolean disponible;
    @Expose
    private int idPropietario;
    @Expose
    private  Propietario duenio;

    public Inmueble() {
    }

    public Inmueble(int idInmueble, String direccion, String tipo, String uso, int ambientes, int superficie, double latitud, double longitud, double valor, String imagen, boolean disponible, int idPropietario, Propietario duenio) {
        this.idInmueble = idInmueble;
        this.direccion = direccion;
        this.tipo = tipo;
        this.uso = uso;
        this.ambientes = ambientes;
        this.superficie = superficie;
        this.latitud = latitud;
        this.longitud = longitud;
        this.valor = valor;
        this.imagen = imagen;
        this.disponible = disponible;
        this.idPropietario = idPropietario;
        this.duenio = duenio;
    }


    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "idInmueble=" + idInmueble +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", uso='" + uso + '\'' +
                ", ambientes=" + ambientes +
                ", superficie=" + superficie +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", valor=" + valor +
                ", imagen='" + imagen + '\'' +
                ", disponible=" + disponible +
                ", idPropietario=" + idPropietario +
                ", duenio=" + duenio +
                '}';
    }


}


