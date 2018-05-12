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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SigninFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference database;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private Button sigin;
private EditText email;
private EditText pass;
private    String email_text;
private    String pass_text;
private FirebaseAuth auth;
private ProgressBar progressBar;
private TextView resetpass;
private boolean flag=true;
    private OnFragmentInteractionListener mListener;

    public SigninFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
        View view=inflater.inflate(R.layout.fragment_signin, container, false);
        sigin=(Button)view.findViewById(R.id.signbtn);
        email=(EditText)view.findViewById(R.id.email);
        pass=(EditText)view.findViewById(R.id.password);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.setBackgroundColor(0x33b5e5);
        resetpass=(TextView) view.findViewById(R.id.resetpass);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Intent i =new Intent(getActivity(),NavigationActivity.class);

            startActivity(i);
            getActivity().finish();
        }
        else {
            ButtonPressed();
        }
        return view;
    }
public void checkValues(){
        flag=true;
     email_text=email.getText().toString();
     pass_text=pass.getText().toString();
    if (email_text.equals("")){
        email.setError("You must enter your email");
        flag=false;
    }
    else{
        String [] em=email_text.split("@");
        if (!(isEmailValid(email_text) )){
            email.setError("This is not an Email format");
            flag=false;
        }
        else if (!em[1].equals("eng.asu.edu.eg")){
            email.setError("Please enter the right email");
            flag=false;
        }
    }
    if (pass_text.equals("")){
        pass.setError("You must enter your password");
        flag=false;
    }

}

    @Override
    public void onStop() {
        super.onStop();
    }

    public void ButtonPressed(){
        database= FirebaseDatabase.getInstance().getReference("Post");
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent( getActivity(), ResetPasswordActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValues();
               // AuthenticateReady();
                if (flag){
                progressBar.setVisibility(View.VISIBLE);
                AuthenticateUser();
               // database.child("-LBhpD-yzzcC2CGBZ9rz").removeValue();
                //database.child("-LBqrd-ZKATBrpB6WOqv").removeValue();
                //database.child("-LBqtMFopEY9rNlBm7kT").removeValue();
                //database.child("-LBquG4XKaebDhj6yJnx").removeValue();
               // addpost();

             // Intent i = new Intent(getActivity(), NavigationActivity.class);
               // startActivity(i);
                }
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void addpost(){

        final String userId=database.push().getKey();

                Date currentTime = Calendar.getInstance().getTime();

                Post p=new Post();
                p.name="Menna Adel";
                p.time=currentTime.toString();
                p.department="Computer Engineering";
                p.post="AI on Monday";
                p.level="Senior";
                p.status="Student";
                p.studentid="-LBquG4XKaebDhj6yJnx";
/*
                Student s=new Student();
                s.setname("Tasneem");
                s.setstatus("Student");
                s.setdepartment("Computer Engineering");
                s.time.add(currentTime.toString());
                s.setlevel("Senior");
                s.posts.add("Deep Learning on Monday at 3:00pm");*/
        database.child(userId).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Test_User","User "+ userId +" Updated");
            }
        });


    }
public void AuthenticateReady(){
    auth = FirebaseAuth.getInstance();

    if (auth.getCurrentUser() != null) {
        startActivity(new Intent(getActivity(), NavigationActivity.class));
    }
}
public void AuthenticateUser(){
    //auth = FirebaseAuth.getInstance();
    auth.signInWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
            Intent intent = new Intent(getActivity(), NavigationActivity.class);
            startActivity(intent);
        }else{
                Toast.makeText(getActivity(), "Sorry email or password is not correct", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }

        }
    });
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
