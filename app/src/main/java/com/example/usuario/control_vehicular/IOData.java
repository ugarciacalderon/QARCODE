package com.example.usuario.control_vehicular;

public class IOData {
    //Variables de registro
    public String a_Usuario;
    public String b_Licenciatura;
    public String c_tipoUsuario;
    public String d_Vehiculo;
    public String e_Color;
    public String f_Placas;
    public String g_Observaciones;
    public String h_hora_Entrada;
    public String i_hora_Salida;

    public IOData() { }

    public String getA_Usuario() {
        return a_Usuario;
    }

    public void setA_Usuario(String a_Usuario) {
        this.a_Usuario = a_Usuario;
    }

    public String getB_Licenciatura() {
        return b_Licenciatura;
    }

    public void setB_Licenciatura(String b_Licenciatura) {
        this.b_Licenciatura = b_Licenciatura;
    }

    public String getD_Vehiculo() {
        return d_Vehiculo;
    }

    public void setD_Vehiculo(String d_Vehiculo) {
        this.d_Vehiculo = d_Vehiculo;
    }

    public String getE_Color() {
        return e_Color;
    }

    public void setE_Color(String e_Color) {
        this.e_Color = e_Color;
    }

    public String getF_Placas() {
        return f_Placas;
    }

    public void setF_Placas(String f_Placas) {
        this.f_Placas = f_Placas;
    }

    public String getH_hora_Entrada() {
        return h_hora_Entrada;
    }

    public void setH_hora_Entrada(String h_hora_Entrada) {
        this.h_hora_Entrada = h_hora_Entrada;
    }

    public String getI_hora_Salida() {
        return i_hora_Salida;
    }

    public void setI_hora_Salida(String i_hora_Salida) {
        this.i_hora_Salida = i_hora_Salida;
    }

    public IOData(String nombre_usuario, String licenciatura, String vehiculo, String color, String placas,
                  String horaEntrada, String horaSalida){
        this.a_Usuario = nombre_usuario;
        this.b_Licenciatura = licenciatura;
        this.d_Vehiculo = vehiculo;
        this.e_Color = color;
        this.f_Placas = placas;
        this.h_hora_Entrada = horaEntrada;
        this.i_hora_Salida = horaSalida;
    }
}
