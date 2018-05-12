package com.example.dell.firebase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link signupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link signupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button singup;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText username;
    private Spinner spinner;
    private Spinner spinner2;
    private String name;
    private String pass;
    private String Email;
    private String userlevel;
    private String time;
    private String id;
    private String departments;
    private ScrollView scroll;
    private Spinner spinner3;
    String status;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private OnFragmentInteractionListener mListener;
    private DatabaseReference db;
    private StudentKeys keys;
    private StudentHandler student;
    private boolean flag=true;
    private StudentHandler SH;
    public signupFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static signupFragment newInstance(String param1, String param2) {
        signupFragment fragment = new signupFragment();
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
        View view=inflater.inflate(R.layout.fragment_signup, container, false);
        singup=(Button)view.findViewById(R.id.signupbtn);
        password=(EditText) view.findViewById(R.id.password);
        confirmPassword=(EditText) view.findViewById(R.id.confirmpassword);
        email=(EditText) view.findViewById(R.id.email);
        username=(EditText) view.findViewById(R.id.name);
        spinner=(Spinner)view.findViewById(R.id.spinner);
        spinner2=(Spinner)view.findViewById(R.id.spinner2);
        spinner3=(Spinner)view.findViewById(R.id.spinner3);
        scroll=(ScrollView) view.findViewById(R.id.scroll);
        scroll.fullScroll(View.FOCUS_DOWN);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        ref= FirebaseDatabase.getInstance().getReference("Student");
        //db=FirebaseDatabase.getInstance().getReference("Student");
        auth = FirebaseAuth.getInstance();
        SH=new StudentHandler(ref);
        keys=new StudentKeys();
        fillSpinner();
        ButtonPressed();
        return view;
    }
    public void fillSpinner(){
        String[] levels = getResources().getStringArray(R.array.levels);
        String[] depart = getResources().getStringArray(R.array.departments);
        String[] statusarr= getResources().getStringArray(R.array.status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, levels);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, depart);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,statusarr);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).toString().isEmpty()) {
             userlevel=adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).toString().isEmpty()) {
                departments=adapterView.getItemAtPosition(i).toString();
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).toString().isEmpty()) {
                    status=adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void UserAuthenticate(){
        progressBar.setVisibility(View.VISIBLE);
        id  = ref.push().getKey();
        Log.d("LogID",id);
       keys.setKey("studentid",id,getActivity());
       keys.setKey("level",userlevel,getActivity());
       keys.setKey("department",departments,getActivity());
       keys.setKey("name",name,getActivity());
       keys.setKey("status",status,getActivity());
        auth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        if (!task.isSuccessful()) {
            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                    Toast.LENGTH_SHORT).show();
        } else {
            SH.saveNewStudent(id,status,userlevel,name,departments,email.getText().toString());
            Intent i =new Intent(getActivity(),NavigationActivity.class);
            startActivity(i);}
         //else  //Toast.makeText(getActivity(), "Failed to insert data" + task.getException(),
                 //   Toast.LENGTH_SHORT).show();
        //}
    }
});
    }
    public void ButtonPressed(){
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValues();
                if(flag){
                UserAuthenticate();
            }

            }
        });

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

public void checkValues(){
        name=username.getText().toString();
        pass=password.getText().toString();
        Email=email.getText().toString();
    String [] em=Email.split("@");
    if (name.equals("")){
            username.setError("Plese enter your name");
            flag=false;
        }
    if (pass.equals("")){
        password.setError("Plese enter your password");
        flag=false;
    }
    if(confirmPassword.equals("")){
        confirmPassword.setError("Please enter the confirmed password");
        flag=false;
    }
        if(!pass.equals(confirmPassword.getText().toString())){
            confirmPassword.setError("The two passwords are not matched");
            flag=false;
        }
        if(pass.length()<8){
            password.setError("Your password must be at least 8 characters");
        }

    if(Email.equals("")){
            email.setError("Plese enter your college email");
        flag=false;
    }
    else if (!isEmailValid(Email)){
        email.setError("Please enter correct format for email");
        flag=false;
    }

    else if (!em[1].equals("eng.asu.edu.eg")){
        email.setError("Please enter the right email");
        flag=false;
    }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
