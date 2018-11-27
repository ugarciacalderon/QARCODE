package com.example.usuario.control_vehicular;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link visitante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link visitante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class visitante extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    //
    Spinner combolicenciatura, comboTipo;
    String a="";
    EditText nombre_usuario,etObser,etColor, etVehiculo, etPlacas ;
    Button btnCreateQR,btnCreaPDF;
    ImageView imageView;
    Image image;

    private  String[]header={"Nombre","Placas","Vehiculo","Color","Usuario","Carrera" };
    private String shorText="Comprobante de registro";
    private String longText="Datos";
    private TemplatePDF templatePDF;
    private int bandera=0;
    public Bitmap bitmap;
    //database
    private DatabaseReference mDatabase;


    public visitante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment visitante.
     */
    // TODO: Rename and change types and number of parameters
    public static visitante newInstance(String param1, String param2) {
        visitante fragment = new visitante();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Vista para poder usar {métodos findViewById en fragmentos
        View view = inflater.inflate(R.layout.fragment_nuevo, container, false);


        //Variables de registro
        nombre_usuario=(EditText) view.findViewById(R.id.nombre_usuario);
        //Licenciatura
        //Tipo Usuario
        etVehiculo=(EditText)view.findViewById(R.id.etVehiculo);
        etColor=(EditText)view.findViewById(R.id.etColor);
        etPlacas=(EditText)view.findViewById(R.id.etPlacas);
        etObser= (EditText)view.findViewById(R.id.etObs);

        btnCreateQR=(Button) view.findViewById(R.id.btnCreate);
        imageView=(ImageView) view.findViewById(R.id.imageView);

        //instancia a la base de datos de firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Generando Código QR...
        btnCreateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!nombre_usuario.getText().toString().contentEquals("")
                        && !etVehiculo.getText().toString().contentEquals("")
                        && !etColor.getText().toString().contentEquals("")
                        && !etPlacas.getText().toString().contentEquals("")
                        && !etObser.getText().toString().contentEquals("")
                        && !comboTipo.getSelectedItem().toString().contentEquals("Selecciona una opción")
                        && !combolicenciatura.getSelectedItem().toString().contentEquals("Selecciona una opción")) {



                                bandera=1;
                                String text= etPlacas.getText().toString()+","+nombre_usuario.getText().toString()+","+etVehiculo.getText().toString().trim()+
                                        ","+etColor.getText().toString()+","+comboTipo.getSelectedItemPosition()+
                                        ","+combolicenciatura.getSelectedItemPosition()+","+etObser.getText();
                                //extrae datos de las cajas
                                String nombre_user = nombre_usuario.getText().toString();
                                String licenciatura = combolicenciatura.getSelectedItem().toString();
                                String tipousuario = comboTipo.getSelectedItem().toString();
                                String vehiculo = etVehiculo.getText().toString();
                                String color = etColor.getText().toString();
                                String placas = etPlacas.getText().toString();
                                String observaciones = etObser.getText().toString();

                                if (text != null){
                                    try {
                                        //guarda datos en firebase
                                        //String id = mDatabase.push().getKey();
                                        UserData user = new UserData(nombre_user,licenciatura,tipousuario,vehiculo,color,placas,observaciones);
                                        //carga datos
                                        mDatabase.child("visitas").child(nombre_user).setValue(user);
                                    }catch (Exception e){
                                        e.printStackTrace();

                                    }
                                }

                } else {
                    Toast toast= Toast.makeText(getActivity().getApplicationContext(),"Por favor ingresa los campos   c: ",Toast.LENGTH_SHORT);
                    toast.show();
                    //etValida.setText("Por favor ingresa todos los campos :)");
                    //etValida.setFocusable(true);
                    // scrollView.resetPivot();

                    if (nombre_usuario.getText().toString().contentEquals("")){
                        nombre_usuario.setHint("*");

                    }else{

                        if (etVehiculo.getText().toString().contentEquals("")){
                            etVehiculo.setHint("*");

                        }else{

                            if (etPlacas.getText().toString().contentEquals("") && (etPlacas.getText().length()==7)){
                                etPlacas.setHint("*");
                               // etValida.setText("Mínimo de 7 digitos");
                            }else{

                                if (etColor.getText().toString().contentEquals("")){
                                    etColor.setHint("*");
                                }else{

                                    if (combolicenciatura.getSelectedItem().toString().contentEquals("Selecciona una opción")){
                                        combolicenciatura.performClick();
                                    }else{
                                        if (comboTipo.getSelectedItem().toString().contentEquals("Selecciona una opción")){
                                            comboTipo.performClick();
                                        }else{

                                            if (etObser.getText().toString().contentEquals("")){
                                                etObser.setHint("*");
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }




            }
        });
        //Carga  info Spinner Carrera y tipo de usuario
        combolicenciatura = (Spinner) view.findViewById(R.id.carrera);
        comboTipo = (Spinner) view.findViewById(R.id.tipo_usu);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_carrera,
                android.R.layout.simple_spinner_item);
        combolicenciatura.setAdapter(adapter);
        combolicenciatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String opcion=String.valueOf(combolicenciatura.getSelectedItemId());
                a=adapter.getItem(1).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.Tipo_usua,
                android.R.layout.simple_spinner_item);

        comboTipo.setAdapter(adapter1);
        comboTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_visitante, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
