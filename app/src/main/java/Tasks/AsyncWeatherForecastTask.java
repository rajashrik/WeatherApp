package Tasks;

import android.os.AsyncTask;
import com.example.rajashrk.weatherapp.WeatherResponseListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by rajashrk on 4/9/17.
 */
public class AsyncWeatherForecastTask  extends AsyncTask<String, Void, String> {

    WeatherResponseListener listener;

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        listener.weatherForecastReceived(o);

    }

    public AsyncWeatherForecastTask(WeatherResponseListener listener) {
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
        return "FailedEvent";
    }

}
