package net.farid.project_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class Add_company extends AppCompatActivity {

    private Button buttonChoose;
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="http://10.0.2.2:80/project/upload_c_icon.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    private String c_type;
    private ProgressDialog pDialog;
    RadioButton radioF,radopT;
    private String TAG = Add_company.class.getSimpleName();
    TextView c_name, c_username, c_password, c_details;
    Button b;
    String id,name, username, password, details;
    String s;
    boolean bool;
    AwesomeValidation mAwesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        imageView=(ImageView) findViewById(R.id.c_v_l_imageView);
        radioF=(RadioButton)findViewById(R.id.c_radioF) ;
        radopT=(RadioButton)findViewById(R.id.c_radioT);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showFileChooser();
            }
        });


        b=(Button)findViewById(R.id.add_company);


        s=getIntent().getStringExtra("manage");
        c_name = (TextView) findViewById(R.id.c_name);
        c_username = (TextView) findViewById(R.id.c_username);
        c_password = (TextView) findViewById(R.id.c_pass);
        c_details = (TextView) findViewById(R.id.c_details);
        if(s.equals("2")){
            c_name.setText(getIntent().getStringExtra("name"));
            c_username.setText(getIntent().getStringExtra("username"));
            c_password.setText(getIntent().getStringExtra("pass"));
            c_details.setText(getIntent().getStringExtra("details"));
            b.setText("UPDATE COMPANY");
        }

        //Step 1: designate a style

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set

// Step 2: add validations
// support regex string, java.util.regex.Pattern and Guava#Range
// you can pass resource or string
        mAwesomeValidation.addValidation(Add_company.this, R.id.c_name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(Add_company.this, R.id.c_username, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(Add_company.this, R.id.c_details, "[a-zA-Z\\s]+", R.string.err_name);

// to validate the confirmation of another field
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{8,}";
        if (s.equals("1"))
            mAwesomeValidation.addValidation(Add_company.this, R.id.c_pass, regexPassword, R.string.err_password);

// Step 3: set a trigger
        findViewById(R.id.add_company).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioF.isChecked())
                    c_type="f";
                else
                    c_type="t";
                bool= mAwesomeValidation.validate();
                if(bool==true){
                    name = c_name.getText().toString();
                    username = c_username.getText().toString();
                    password = c_password.getText().toString();
                    details = c_details.getText().toString();

                    if (s.equals("1")) {
                        // call AsynTask to perform network operation on separate thread
                        new addCompany().execute();
                    } else {
                        id = getIntent().getStringExtra("id");
                        new updateCompany().execute();

                        Toast.makeText(getBaseContext(), id + " update", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    public void finishThisA(){
        this.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }}}

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response

                        Toast.makeText(Add_company.this, s, Toast.LENGTH_LONG).show();
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog

                        loading.dismiss();
                        //Showing toast

                        Toast.makeText(Add_company.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }}){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                //Getting Image Name


                String name = c_name.getText().toString().trim();
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters

                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                //returning parameters
                return params;
            }};
        //Creating a Request Queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
        finishThisA();
    }

    private class addCompany extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/add_company.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("username", username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String pass=Base64.encodeToString(hash, Base64.DEFAULT);


                jsonObject.put("password", SHA256.GenerateHash(password));
                jsonObject.put("details", details);
                jsonObject.put("c_type",c_type);
                String message = jsonObject.toString();

                Log.e(TAG, " start:2 ");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                Log.e(TAG, " start:3 ");
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Log.e(TAG, " start:4 ");
                //open
                conn.connect();

                Log.e(TAG, " start:5 ");
                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();
                Log.e(TAG, " start: "+is);
                //String contentAsString = readIt(is,len);

            } catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, " error: " + e.getMessage());
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Add_company.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (pDialog.isShowing())
                pDialog.dismiss();
            uploadImage();

        }
    }

    private class updateCompany extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/update_company.php");
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", id);
                jsonObject.put("name", name);
                jsonObject.put("username", username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String pass=Base64.encodeToString(hash, Base64.DEFAULT);


                jsonObject.put("password",SHA256.GenerateHash(password));
                jsonObject.put("details", details);
                jsonObject.put("c_type",c_type);
                String message = jsonObject.toString();

                Log.e(TAG, " start:2 ");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                Log.e(TAG, " start:3 ");
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Log.e(TAG, " start:4 ");
                //open
                conn.connect();

                Log.e(TAG, " start:5 ");
                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();
                Log.e(TAG, " start: "+is);
                //String contentAsString = readIt(is,len);

            } catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, " error: " + e.getMessage());
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Add_company.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (pDialog.isShowing())
                pDialog.dismiss();
            uploadImage();
        }
    }



    }
