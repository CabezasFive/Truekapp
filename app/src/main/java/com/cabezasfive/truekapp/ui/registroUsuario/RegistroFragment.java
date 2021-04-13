package com.cabezasfive.truekapp.ui.registroUsuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroFragment extends Fragment {

    private EditText editTextnombre;
    private EditText editTextapellido;
    private EditText editTextdireccion;
    private EditText editTexttelefono;
    private EditText editTextnick;
    private EditText editTextemail;
    private EditText editTextpassword;
    private Button btnRegistrar;

    // Variables de los datos que se van a registrar
    private String nombre = "";
    private String apellido = "";
    private String direccion = "";
    private String telefono = "";
    private String email = "";
    private String nick = "" ;
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    public RegistroFragment() {
        // Required empty public constructor
    }

    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();

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
        View view=  inflater.inflate(R.layout.fragment_registro, container, false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextnombre = view.findViewById(R.id.et_Registro_Nombre);
        editTextapellido = view.findViewById(R.id.et_Registro_Apellido);
        editTextdireccion = view.findViewById(R.id.et_Registro_Direccion);
        editTexttelefono = view.findViewById(R.id.et_Registro_Telefono);
        editTextnick = view.findViewById(R.id.et_Registro_NickName);
        editTextemail = view.findViewById(R.id.et_Registro_Email);
        editTextpassword = view.findViewById(R.id.et_Registro_Password);
        btnRegistrar = view.findViewById(R.id.btn_RegistroUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = editTextnombre.getText().toString();
                apellido = editTextapellido.getText().toString();
                direccion = editTextdireccion.getText().toString();
                telefono = editTexttelefono.getText().toString();
                nick = editTextnick.getText().toString();
                email = editTextemail.getText().toString();
                password = editTextpassword.getText().toString();

                validarTextos();
            }
        });

        return view;
    }

    private void validarTextos() {
        if(!nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty() && !nombre.isEmpty()
                && !email.isEmpty() && !password.isEmpty() && !nick.isEmpty()){
            if (password.length() >= 6){
                //registrarUsuario();
            }else{
                Toast.makeText(getContext(), "El password debe tener como minimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    // Mapa de valores para agregar al campo usuario
                    Map<String, Object> user = new HashMap<>();
                    user.put("nombre", nombre);
                    user.put("apellido", apellido);
                    user.put("nick", nick);
                    user.put("direccion", direccion);
                    user.put("telefono", telefono);
                    user.put("email", email);


                    // obtener el id que se le asigna al usuario en Auth
                    String id = mAuth.getCurrentUser().getUid();

                    // Creo un nuevo nodo hijo de Users con el nombre del id del usuario
                    databaseReference.child("users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                // Se guarda en Nicks solo el nombre de usuario para facilitar la busqueda
                                // en que no sea usuario repetido (A CORREGIR - NECESITA VERIFICAR QUE NO EXISTA EL NICK)
                                databaseReference.child("nicks").child("name").setValue(nick);

                                // El registro fue exitoso se envia al usuario a otro Fragment
                                // (Home por el momento)
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft;
                                ft = fm.beginTransaction();
                                ft.replace(R.id.contenido,new HomeFragment());
                                ft.commit();
                            }else{
                                Toast.makeText(getContext(), "Error escribiendo los datos del usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

     */
}