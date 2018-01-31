package live.soilandpimp.batch.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.exception.JvcSiteUnavailible;

@Repository
public class JvcMusicJsonDao {

    @Autowired
    private ObjectMapper objectMapper;

    private static final String JVC_MUSIC_SOIL_INFORMATION_URL = "http://www.jvcmusic.co.jp/-/Information/A018653.json";

    public List<Event> getCurrentSiteEvents() {

        try {

            URL url = new URL(JVC_MUSIC_SOIL_INFORMATION_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) throw new JvcSiteUnavailible(responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine = null;
            StringBuffer response = new StringBuffer();

            inputLine = reader.readLine();

            while (inputLine != null) {
                response.append(inputLine);
                inputLine = reader.readLine();
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONObject("contents").getJSONArray("live");

            return objectMapper.readValue(jsonArray.toString(), new TypeReference<List<Event>>() {});

        } catch (IOException | JSONException | JvcSiteUnavailible e) {
            e.printStackTrace();
            throw new RuntimeException();// TODO enhance
        }

    }
}