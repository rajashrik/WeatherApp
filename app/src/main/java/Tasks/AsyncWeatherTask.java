package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.rajashrk.weatherapp.WeatherResponseListener;
import okhttp3.*;

import javax.xml.transform.Result;
import java.io.IOException;

/**
 * Created by rajashrk on 4/8/17.
 */
public class AsyncWeatherTask  extends AsyncTask<String, Void, String>{


    WeatherResponseListener listener;
    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        listener.weatherDataReceived(o);
    }

    public AsyncWeatherTask(WeatherResponseListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String ...urls) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(urls[0]).build();
        Response response = null;
        Call newCall = client.newCall(request);
        try {
            response = newCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful() ){
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Failed";
    }
}
