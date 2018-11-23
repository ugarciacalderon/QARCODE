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

    public UserData(String nombre_usuario,String licenciatura,String tipoUsuario, String vehiculo, String color, String placas,
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
