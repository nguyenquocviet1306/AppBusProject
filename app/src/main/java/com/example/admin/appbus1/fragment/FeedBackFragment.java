package com.example.admin.appbus1.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.appbus1.R;
import com.example.admin.appbus1.models.FeedBack;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.et_name_send)
    EditText nameSend;
    @BindView(R.id.et_phone_send)
    EditText phoneSend;
    @BindView(R.id.et_content)
    EditText contentSend;
    @BindView(R.id.bt_gui)
    Button send;
    @BindView(R.id.radio_speed)
    RadioGroup radioGroupSpeed;
    @BindView(R.id.good_speed)
    RadioButton radioButtonGoodSpeed;
    @BindView(R.id.tb_speed)
    RadioButton radioButtonTBSpeed;
    @BindView(R.id.yeu_speed)
    RadioButton radioButtonYeuSpeed;
    @BindView(R.id.radio_accuracy)
    RadioGroup radioGroupAccuracy;
    @BindView(R.id.accuracy_good)
    RadioButton radioButtonAccuracyGood;
    @BindView(R.id.accuracy_tb)
    RadioButton radioButtonAccuracyTB;
    @BindView(R.id.accuracy_yeu)
    RadioButton radioButtonAccuracyYeu;
    FeedBack feedBack;


    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        ButterKnife.bind(this, view);
        send.setOnClickListener(this);
        setHasOptionsMenu(true);
        getActivity().setTitle("Feedback");
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public static String POST(String url, FeedBack feedBack){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", feedBack.getName());
            jsonObject.accumulate("phone", feedBack.getPhone());
            jsonObject.accumulate("content", feedBack.getContent());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_gui:
                if(!validate()) {
                    Toast.makeText(getActivity(), "Bạn vui lòng điền đầy đủ thông tin ở mục *", Toast.LENGTH_LONG).show();
                } else if (!isValidPhoneNumber(phoneSend.getText().toString())){
                    Toast.makeText(getActivity(), "Bạn vui lòng kiểm tra lại số điện thoại", Toast.LENGTH_LONG).show();
                }


                else {
                    // call AsynTask to perform network operation on separate thread
                    new HttpAsyncTask().execute("https://istudent.herokuapp.com/api/feedback");
                    break;
                }
        }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            feedBack = new FeedBack();
            feedBack.setName(nameSend.getText().toString());
            feedBack.setPhone(phoneSend.getText().toString());
            feedBack.setContent(contentSend.getText().toString());

            return POST(urls[0],feedBack);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), "Cám ơn bạn đã giúp chúng tôi hoàn thiện sản phẩm", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }
    private boolean validate(){
        if(nameSend.getText().toString().trim().equals(""))
            return false;
        else if(phoneSend.getText().toString().trim().equals(""))
            return false;
        else if(contentSend.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
