package com.cabezasfive.truekapp.ui.editarUsuario;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Usuario;
import com.cabezasfive.truekapp.ui.registroUsuario.RegistroFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditarUsuarioFragment extends Fragment {

    private Button btnModificar;
    private EditText et_Editar_NickName;
    private EditText et_Editar_Nombre;
    private EditText et_Edit_Apellido;
    private EditText et_Editar_Direccion;
    private EditText et_Editar_Telefono;
    private FirebaseAuth auth;

    private String nombre = "";
    private String apellido = "";
    private String direccion = "";
    private String telefono = "";
    private String nick = "" ;




    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Usuario mUsuario = new Usuario();

    private FirebaseUser user;
    private DatabaseReference usersRef;

    private EditarUsuarioViewModel mViewModel;

    public static EditarUsuarioFragment newInstance() {
        return new EditarUsuarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.editar_usuario_fragment, container, false);

        btnModificar = (Button) view.findViewById(R.id.btn_Modificar);
        et_Editar_NickName = (EditText) view.findViewById(R.id.et_Edit_NickName);
        et_Editar_Nombre = (EditText) view.findViewById(R.id.et_Edit_Nombre);
        et_Edit_Apellido = (EditText) view.findViewById(R.id.et_Edit_Apellido);
        et_Editar_Direccion = (EditText) view.findViewById(R.id.et_Editar_Direccion);
        et_Editar_Telefono = (EditText) view.findViewById(R.id.et_Editar_Telefono);

        inicializarFirebase();

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        usersRef = firebaseDatabase.getReference("users").child(auth.getUid());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                mUsuario = user;

                String nick = user.getNick();
                String nombre = user.getNombre();
                String apellido = user.getApellido();
                String direccion = user.getDireccion();
                String telefono = user.getTelefono();

                String uid = dataSnapshot.getKey();


                et_Editar_NickName.setText(nick);
                et_Editar_Nombre.setText(nombre);
                et_Edit_Apellido.setText(apellido);
                et_Editar_Direccion.setText(direccion);
                et_Editar_Telefono.setText(telefono);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = et_Editar_Nombre.getText().toString();
                String apellido = et_Editar_Nombre.getText().toString();
                String direccion = et_Editar_Direccion.getText().toString();
                String telefono = et_Editar_Telefono.getText().toString();
                String nick = et_Editar_NickName.getText().toString();


                mUsuario.setNombre(nombre);
                mUsuario.setApellido(apellido);
                mUsuario.setDireccion(direccion);
                mUsuario.setTelefono(telefono);
                mUsuario.setNick(nick);

                usersRef.setValue(mUsuario);


            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarUsuarioViewModel.class);
        // TODO: Use the ViewModel
    }

    // Metodo para inicializar Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}