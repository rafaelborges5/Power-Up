package server.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import server.database.ActivityRepository;

import java.io.IOException;
import java.util.List;

@Configuration
public class Activity_config {


    private final ActivityRepository activityRepository;


    @Autowired
    public Activity_config(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * This method gets the json file from the activities and parses it into a list of activities using jackson
     *
     * @throws IOException
     */
    @Bean
    public void addActivities() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Activity> listOfActivities = objectMapper.readValue(ResourceUtils.
                getFile("classpath:Activity_bank/activities.json"), new
                TypeReference<>() {
                });
        truncateString(listOfActivities);
        activityRepository.deleteAll();
        activityRepository.saveAll(listOfActivities);
    }


    /**
     * This method truncates the strings that are over 255 characters. The database would require a CLOB and it is
     * unnecessary
     *
     * @param list of activities
     */
    public void truncateString(List<Activity> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSource().length() >= 255) {
                list.get(i).setSource(list.get(i).getSource().substring(0, 254));
            }
        }
    }
}
