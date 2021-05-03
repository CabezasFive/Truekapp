package com.cabezasfive.truekapp.ui.editarUsuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditarUsuarioFragment extends Fragment {

    private Button btnGuardar, btnCancelar;
    private EditText etNombre, etApellido, etDireccion, etTelefono, etCiudad;

    private Usuario mUsuario = new Usuario();

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference usersRef;


    public static EditarUsuarioFragment newInstance(String param1, String param2) {
        EditarUsuarioFragment fragment = new EditarUsuarioFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

        etNombre = view.findViewById(R.id.et_Edit_Nombre_User);
        etApellido = view.findViewById(R.id.et_Edit_Apellido_User);
        etDireccion = view.findViewById(R.id.et_Edit_Direccion_User);
        etTelefono = view.findViewById(R.id.et_Edit_Telefono_User);
        etCiudad = view.findViewById(R.id.et_Edit_Ciudad_User);

        btnCancelar = view.findViewById(R.id.btnCancelEditUsuario);
        btnGuardar = view.findViewById(R.id.btnGuardarEditUsuario);

        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        usersRef = firebaseDatabase.getReference("users").child(auth.getUid());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null){
                    mUsuario = snapshot.getValue(Usuario.class);

                    etNombre.setText(mUsuario.getNombre());
                    etApellido.setText(mUsuario.getApellido());
                    etDireccion.setText(mUsuario.getDireccion());
                    etTelefono.setText(mUsuario.getTelefono());
                    etCiudad.setText(mUsuario.getCiudad());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Volver hacia panel de usuario
                Navigation.findNavController(view).popBackStack();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre, appellido, direccion, telefono, ciudad;
                nombre = etNombre.getText().toString();
                appellido = etApellido.getText().toString();
                direccion = etDireccion.getText().toString();
                telefono = etTelefono.getText().toString();
                ciudad = etCiudad.getText().toString();

                mUsuario.setNombre(nombre);
                mUsuario.setApellido(appellido);
                mUsuario.setDireccion(direccion);
                mUsuario.setTelefono(telefono);
                mUsuario.setCiudad(ciudad);

                usersRef.setValue(mUsuario);

                Navigation.findNavController(view).navigate(R.id.action_editarUsuarioFragment_to_perfilFragment);

            }
        });

        return view;
    }



}