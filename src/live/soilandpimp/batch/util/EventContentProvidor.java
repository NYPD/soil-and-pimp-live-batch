package live.soilandpimp.batch.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.util.StreamUtils;

import live.soilandpimp.batch.exception.JvcSiteUnavailible;

/**
 * Class used to get the current SOIL & "PIMP" SESSIONS events as a byte array. This is used so it can easily be mocked and
 * tested in the junit tests.
 * 
 * @author NYPD
 *
 */
public class EventContentProvidor {

    private static final String JVC_MUSIC_SOIL_INFORMATION_URL = "http://www.jvcmusic.co.jp/-/Information/A018653.json";

    public byte[] getContent() {

        try {

            URL url = new URL(JVC_MUSIC_SOIL_INFORMATION_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) throw new JvcSiteUnavailible(responseCode);

            return StreamUtils.copyToByteArray(connection.getInputStream());

        } catch (IOException | JvcSiteUnavailible e) {
            e.printStackTrace();
            throw new RuntimeException();// TODO enhance
        }

    }
}
