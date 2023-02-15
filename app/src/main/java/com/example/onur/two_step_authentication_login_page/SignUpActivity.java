package com.example.onur.two_step_authentication_login_page;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends Activity {

    List<user> userList =   new ArrayList<user>();


    EditText userNameText , userPasswordText  ;
    Button signUpButton ,signInButton ;
    private static final int CAMERA_REQUEST = 1888;
    //ImageView img = (ImageView)findViewById(R.id.imageView);
    TextView warningText ;
    ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_new);
        userNameText = (EditText)findViewById(R.id.usernameText);
        userPasswordText = (EditText)findViewById(R.id.userPasswordText);
        signInButton = (Button)findViewById(R.id.signInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        warningText = (TextView)findViewById(R.id.warningText);
        img = (ImageView)findViewById(R.id.imageView);




        String inputString = "Input Normal String";
        String encryptedString = encryption(inputString);
        String decryptedString = decryption(encryptedString);
        if (decryptedString.equals(inputString)) {

            Log.e(inputString, "OKAY BITCH !");

        } else {
            Log.e(inputString, "WTF !");
        }
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Burada Buttona tıklandığında çalıştırılacak kodlar yer alıyor.
                warningText.setVisibility(View.INVISIBLE);


                String newUserName = userNameText.getText().toString();
                String newPassword = userPasswordText.getText().toString();
                if(newUserName != "" & newPassword != "")   {
                    user newUser = new user(newUserName,"");
                    newUser.encryptedPassword = encryption(newPassword);
                    userList.add(newUser);



                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);


                } else {

                    warningText.setVisibility(View.VISIBLE);

                }



            }
        });











        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Burada Buttona tıklandığında çalıştırılacak kodlar yer alıyor.
                warningText.setVisibility(View.INVISIBLE);


                String userName = userNameText.getText().toString();
                String password = userPasswordText.getText().toString();
                if(userName != "" & password != "")   {
                    user currentUser = new user(userName,"");
                    currentUser.encryptedPassword = encryption(password);
                    for (int i = 0 ; i < userList.size() ; i++) {
                        if(currentUser.userName.equals(userList.get(i).userName) & currentUser.encryptedPassword.equals(userList.get(i).encryptedPassword)){
                            warningText.setText("You have succesfully entered "+ userList.get(i).userName + " !" );
                            warningText.setVisibility(View.VISIBLE);
                        }  else if (i == userList.size() - 1 ) {
                            warningText.setText("You entered a wrong password or username !" );
                            warningText.setVisibility(View.VISIBLE);
                        }
                    }



                } else {

                    warningText.setVisibility(View.VISIBLE);

                }



            }
        });








    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }
    }











    public String encryption(String strNormalText) {
        String seedValue = "YourSecKey";
        String normalTextEnc = "";
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }
    public String decryption(String strEncryptedText) {
        String seedValue = "YourSecKey";
        String strDecryptedText = "";
        try {
            strDecryptedText = AESHelper.decrypt(seedValue, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }
}
