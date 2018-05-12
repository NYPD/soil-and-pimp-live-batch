package live.soilandpimp.batch.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EventContentProvidor.class);
    private static final String JVC_MUSIC_SOIL_INFORMATION_URL = "http://www.jvcmusic.co.jp/-/Information/A018653.json";

    public byte[] getContent() {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(JVC_MUSIC_SOIL_INFORMATION_URL);
            connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) throw new JvcSiteUnavailible(responseCode);

            return StreamUtils.copyToByteArray(connection.getInputStream());

        } catch (IOException exception) {

            if (connection == null)
                LOGGER.error("Something went wrong opening the JVC connection", exception);
            else
                LOGGER.error("Something went wrong reading JVC input stream", exception);

            return null;

        } catch (JvcSiteUnavailible jvcSiteUnavailible) {

            int webResponseCode = jvcSiteUnavailible.getWebResponseCode();
            String error = "Error accessing the JVC site, error code: " + webResponseCode;

            LOGGER.error(error);

            return null;

        }

    }
}
