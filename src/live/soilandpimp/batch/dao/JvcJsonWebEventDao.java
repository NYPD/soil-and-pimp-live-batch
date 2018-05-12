package live.soilandpimp.batch.dao;

import java.io.IOException;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.util.EventContentProvidor;

@Repository
public class JvcJsonWebEventDao implements WebEventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JvcJsonWebEventDao.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EventContentProvidor eventContentProvidor;

    @Override
    public List<Event> getCurrentPostedEvents() {

        try {

            byte[] content = eventContentProvidor.getContent();

            boolean noContent = content == null;
            if (noContent) return null;

            JSONObject jsonObject = new JSONObject(new String(content));
            JSONArray jsonArray = jsonObject.getJSONObject("contents").getJSONArray("live");

            return objectMapper.readValue(jsonArray.toString(), new TypeReference<List<Event>>() {});

        } catch (IOException | JSONException exception) {
            LOGGER.error("Error reading the JVC JSON response", exception);
            return null;
        }
    }

}
