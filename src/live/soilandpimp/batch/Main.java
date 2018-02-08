package live.soilandpimp.batch;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import live.soilandpimp.batch.util.AppConstants;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Main {

    public static void main(String[] args) throws IOException, JSONException {

        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.setAdditionalProfiles(AppConstants.DEV_PROFILE);

        springApplication.run(args);

    }

}