package com.example.demo.APIResponse;

public class LoginResponse {
    private String mensaje;
    private String nombreUsuario;
    private String email;
    private double montoSaldo;

    public LoginResponse(String mensaje, String nombreUsuario, String email, double montoSaldo) {
        this.mensaje = mensaje;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.montoSaldo = montoSaldo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public double getMontoSaldo() {
        return montoSaldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setMontoSaldo(double montoSaldo) {
        this.montoSaldo = montoSaldo;
    }
}
