package com.example.inmob.model;

import java.io.Serializable;

public class Propietario implements Serializable {

    private int id;
    private String dni;
    private String apellido;
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String avatar; // El campo existe

    // Constructor completo
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

    // Getters y Setters para todos los campos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // --- ¡AQUÍ ESTÁ LA SOLUCIÓN! ---
    // El getter y setter que faltaban para el campo 'avatar'
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
