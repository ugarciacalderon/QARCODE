package com.example.usuario.control_vehicular;


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


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.pdf.fonts.otf.GlyphSubstitutionTableReader;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;





/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nuevo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nuevo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevo extends Fragment {
    Spinner combolicenciatura, comboTipo;
    String a="";
    EditText etInput,etObser,etColor, etVehiculo, etPlacas ;
    Button btnCreateQR,btnCreaPDF;
    ImageView imageView;
    Image image;



    private  String[]header={"Nombre","Placas","Vehiculo","Color","Usuario","Carrera" };
    private String shorText="Comprobante de registro";
    private String longText="Datos";
    private TemplatePDF templatePDF;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int bandera=0;
    public Bitmap bitmap;
    public nuevo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nuevo.
     */
    // TODO: Rename and change types and number of parameters
    public static nuevo newInstance(String param1, String param2) {
        nuevo fragment = new nuevo();
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
        etObser= (EditText)view.findViewById(R.id.etObs);
        etColor=(EditText)view.findViewById(R.id.etColor);
        etVehiculo=(EditText)view.findViewById(R.id.etVehiculo);
        etPlacas=(EditText)view.findViewById(R.id.etPlacas);
        btnCreateQR=(Button) view.findViewById(R.id.btnCreate);
        etInput=(EditText) view.findViewById(R.id.etInput);
        imageView=(ImageView) view.findViewById(R.id.imageView);

        //Generando Código QR...
        btnCreateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                String text= etPlacas.getText().toString()+","+etInput.getText().toString()+","+etVehiculo.getText().toString().trim()+","+etColor.getText().toString()+","+comboTipo.getSelectedItemPosition()+","+combolicenciatura.getSelectedItemPosition();
                if (text != null){
                    MultiFormatWriter multiFormatWriter= new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix= multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                        bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);

                    }catch (Exception e){
                        e.printStackTrace();

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
        //Generando PDF
        btnCreaPDF=(Button) view.findViewById(R.id.btncreaPDF);
            btnCreaPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                    templatePDF = new TemplatePDF(getActivity().getApplicationContext());
                    String nombre = etInput.getText().toString().trim();
                    templatePDF.openDocument(nombre.trim());
                    templatePDF.addMetaData("Usuarios", "Nuevo", "Letu");
                    templatePDF.addTitles("Registro", "Estacionamiento UAP Tianguistenco", "26/10/2018");
                    templatePDF.addParagraph(shorText);
                   templatePDF.addParagraph(longText);
                    templatePDF.createTable(header, getUsuarios());
                    templatePDF.closeDocument();

                        String imageName="uaemex.JPG";
                        templatePDF.addImgName(imageName);

                     btnCreaPDF.setEnabled(false);
                     Toast toast= Toast.makeText(getActivity().getApplicationContext(),"Archivo creado en Carpeta PDF ",Toast.LENGTH_SHORT);
                     toast.show();
                    }catch (Exception e){


                    }
                }
            });

        return view;

    }

    private ArrayList<String[]>getUsuarios(){

        ArrayList<String[]>rows=new ArrayList<>();
        rows.add(new String[]{etInput.getText().toString().trim(),
                etPlacas.getText().toString().trim(),
                        etVehiculo.getText().toString(),
                etColor.getText().toString().trim(),
                comboTipo.getSelectedItem().toString(),
                combolicenciatura.getSelectedItem().toString().trim()});


        return  rows;

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

    public void pdfView(View view){

    //templatePDF.viewPDF();
    }

    public void pdfApp(View view){
        templatePDF.appviewPDF(nuevo.this);
    }



}
