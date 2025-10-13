package com.example.inmob.model;

public class Propietario {

    // Estos son los campos que vienen del JSON del servidor
    private int id;
    private String dni;
    private String apellido;
    private String nombre;    private String email;
    private String telefono;
    // Nota: No incluimos la contraseña por seguridad

    // Constructor (opcional, pero buena práctica)
    public Propietario(int id, String dni, String apellido, String nombre, String email, String telefono) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // --- MÉTODOS GETTERS ---
    // Estos son los métodos que tu PerfilFragment necesita y que faltaban.
    // Android Studio los genera automáticamente, pero aquí están escritos.

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    // --- MÉTODOS SETTERS ---
    // Son necesarios para que GSON (el convertidor de Retrofit) pueda construir el objeto.

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
