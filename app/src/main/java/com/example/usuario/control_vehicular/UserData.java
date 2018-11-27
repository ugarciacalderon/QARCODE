package com.example.usuario.control_vehicular;

public class UserData {
    //Variables de registro
    public String a_Usuario;
    public String b_Licenciatura;
    public String c_tipoUsuario;
    public String d_Vehiculo;
    public String e_Color;
    public String f_Placas;
    public String g_Observaciones;

    public UserData(){
        //Default constructor required for calls to DataSnapshot.getvalue(UserData.class)
    }

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

    public String getC_tipoUsuario() {
        return c_tipoUsuario;
    }

    public void setC_tipoUsuario(String c_tipoUsuario) {
        this.c_tipoUsuario = c_tipoUsuario;
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

    public String getG_Observaciones() {
        return g_Observaciones;
    }

    public void setG_Observaciones(String g_Observaciones) {
        this.g_Observaciones = g_Observaciones;
    }

    public UserData(String nombre_usuario, String licenciatura, String tipoUsuario, String vehiculo, String color, String placas,
                    String observaciones){
        this.a_Usuario = nombre_usuario;
        this.b_Licenciatura = licenciatura;
        this.c_tipoUsuario = tipoUsuario;
        this.d_Vehiculo = vehiculo;
        this.e_Color = color;
        this.f_Placas = placas;
        this.g_Observaciones = observaciones;
    }


}
