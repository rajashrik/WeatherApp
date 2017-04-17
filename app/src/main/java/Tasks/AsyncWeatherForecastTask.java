package Tasks;

import android.os.AsyncTask;
import com.example.rajashrk.weatherapp.WeatherResponseListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AsyncWeatherForecastTask extends AsyncTask<String, Void, String> {

	private static final String FAILED_EVENT = "FailedEvent";
	private WeatherResponseListener listener;

	public AsyncWeatherForecastTask(WeatherResponseListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	protected void onPostExecute(String o) {
		super.onPostExecute(o);
		if(FAILED_EVENT.equals(o)) {
			listener.weatherForecastFailed();
		} else {
			listener.weatherForecastReceived(o);
		}
	}

	@Override
	protected String doInBackground(String... urls) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(urls[0]).build();
		Call newCall = client.newCall(request);
		try {
			Response response = newCall.execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILED_EVENT;
	}
}
