package com.example.infractuc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//import com.google.android.gms.auth.api.Auth;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class VistaLogin extends Fragment {


    private EditText txt_email, txt_password;
    private Button b_facebook;

    private Button b_login;
    private Button b_registrarme;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressDialog progressDialog;

    private GoogleApiClient googleApiClient;
    GoogleSignInClient mGoogleSignInClient;

    private SignInButton signInButtonGoogle;
    private LoginButton  signInButtonFacebook;
    private CallbackManager callbackManager;

    public static final int SIGN_IN_CODE_GOOGLE = 777;
    public static final String TAG  = "FACELOG";



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_vista_login, container, false);
        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance();
        //signInButtonFacebook =  vista.findViewById(R.id.login_button_facebook);
        b_facebook = vista.findViewById(R.id.boton_continuar_con_facebook);
        b_login =  vista.findViewById(R.id.boton_login);
        b_registrarme =  vista.findViewById(R.id.boton_registrarme);
        txt_email =  vista.findViewById(R.id.campo_mail);
        txt_password =  vista.findViewById(R.id.campo_password);

        signInButtonGoogle = (SignInButton) vista.findViewById(R.id.boton_login_google);
        progressDialog = new ProgressDialog(getContext());


        //para iniciar con facebook

        b_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_facebook.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        Toast.makeText(getActivity(), "No se pudo acceder intente con otro metodo", Toast.LENGTH_LONG).show();
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        Toast.makeText(getActivity(), "No se pudo acceder intente con otro metodo", Toast.LENGTH_LONG).show();
                        // ...
                    }
                });
            }
        });

        // para iniciar con google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        signInButtonGoogle.setSize(SignInButton.SIZE_WIDE);
        signInButtonGoogle.setColorScheme(SignInButton.COLOR_DARK);

        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // para iniciar sesion con mail

        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OnClickButtonLogin();
            }
        });
        b_registrarme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LlamarARegistrarme();
            }
        });
        // escuchador de firebase listener


        return vista;
    }
    //Codigo del ciclo de vida
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE_GOOGLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            ParaPasar(null);
        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        if(account != null){
            updateUI(null);
        }
    }

    private void updateUI( FirebaseUser user) {

        if(user != null){
            Toast.makeText(getActivity(), "Has Ingresado Correctamente!", Toast.LENGTH_LONG).show();
            LlamarAOpciopnes();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN_CODE_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


    }



    // para google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);
        } catch (Exception e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e);
            updateUI(null);
        }


    }
    // para el face
    public void ParaPasar(FirebaseUser user) {

        if(user!=null){
            String nombre = user.getDisplayName();
            String foto = user.getPhotoUrl().toString();

            Bundle enviar = new Bundle();

            enviar.putString("Usuario",nombre);
            enviar.putString("Foto",foto);
            VistaEnviar_y_detalleDeDenuncia vistaEnviar_y_detalleDeDenuncia = new VistaEnviar_y_detalleDeDenuncia();
            vistaEnviar_y_detalleDeDenuncia.setArguments(enviar);
            LlamarAOpciopnes();
        }
    }

    // para facebook
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            b_facebook.setEnabled(true);
                            ParaPasar(user);
                            //updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Error al autentificar.",
                                    Toast.LENGTH_SHORT).show();
                            b_facebook.setEnabled(true);
                            //updateUI();
                            ParaPasar(null);
                        }

                        // ...
                    }
                });
    }



    // para todo

    public void LlamarAOpciopnes() {
        Toast.makeText(getActivity(), "Has Ingresado Correctamente!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaOpciones()).addToBackStack(null).commit();
    }

    public void LlamarARegistrarme() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ic_contenedor,
                new VistaRegistro()).addToBackStack(null).commit();
    }


    public void OnClickButtonLogin(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            LlamarAOpciopnes();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(getActivity(), "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo identificar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }



}