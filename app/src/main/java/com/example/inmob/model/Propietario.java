package com.example.inmob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Propietario implements Serializable {


    @Expose
    @SerializedName("idPropietario")
    private int id;

    @Expose
    @SerializedName("dni")
    private String dni;

    @Expose
    @SerializedName("apellido")
    private String apellido;

    @Expose
    @SerializedName("nombre")
    private String nombre;

    @Expose
    @SerializedName("email")
    private String email;

    @SerializedName("clave")
    private String password;

    @Expose
    @SerializedName("telefono")
    private String telefono;


    @Expose
    @SerializedName("avatar")
    private String avatar;



    public Propietario() {
    }


    public Propietario(int id, String dni, String apellido, String nombre, String email, String password, String telefono, String avatar) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.avatar = avatar;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
