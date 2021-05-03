package com.cabezasfive.truekapp.ui.publicar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.material.textfield.TextInputEditText;

public class PublicarFragment02 extends Fragment  {

    private TextInputEditText inputDescripcion;
    private Button btn_Siguiente;
    private Publicacion mPublicacion = new Publicacion();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar02, container, false);

        Bundle getUsuario = getArguments();
        if (getUsuario != null){
            mPublicacion = (Publicacion) getUsuario.getSerializable("pub");
        }

        inputDescripcion = view.findViewById(R.id.tie_Descripción);

        btn_Siguiente = view.findViewById(R.id.btn_GoForm3Publicar);


        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validacion()){
                    Publicacion publicacion = new Publicacion();
                    publicacion = mPublicacion;
                    String descripcion = inputDescripcion.getText().toString();
                    publicacion.setDescripcion(descripcion);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub", publicacion);
                    Navigation.findNavController(view).navigate(R.id.publicarFragment032,bundle);
                }
            }
        });

        return view;
    }


    //Valida que este ingresado los valores necesarios
    private boolean validacion() {
        String descripcion = inputDescripcion.getText().toString();
        if(descripcion.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar una Descripcion", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

}