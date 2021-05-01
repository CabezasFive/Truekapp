package com.cabezasfive.truekapp.ui.publicar;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.material.textfield.TextInputEditText;

public class PublicarFragment extends Fragment {

    private TextInputEditText inputTitulo, inputPrecio;
    private Button btn_Siguiente;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);

        inputTitulo = view.findViewById(R.id.tie_Nombre_Producto);
        inputPrecio = view.findViewById(R.id.tie_Precio);

        btn_Siguiente = view.findViewById(R.id.btn_GoForm2Publicar);

        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validacion()) {
                    Publicacion publicacion = new Publicacion();
                    String titulo = inputTitulo.getText().toString();
                    String precio = inputPrecio.getText().toString();
                    publicacion.setTitulo(titulo);
                    publicacion.setPrecio(precio);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub", publicacion);
                    Navigation.findNavController(view).navigate(R.id.publicarFragment022, bundle);
                }
            }
        });

        return view;
    }


    //Valida que este ingresado los valores necesarios
    private boolean validacion() {
        String titulo = inputTitulo.getText().toString().trim();
        String precio = inputPrecio.getText().toString().trim();

        if (titulo.equals("") || precio.equals("")) {
            Toast.makeText(getActivity(), "Debe ingresar los datos", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}