package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Activity {
    @Id
    private String id;  // String representing the id found in the repository.
    private String image_path; //String representing the file path of the image.
    private String title;//String representing the activity's title.
    private long consumption_in_wh;//Int representing the consumption_in_wh of the activity.
    private String source; // String representing the source of the information.

    //this will be implemented later
//    @Transient
//    private Image image;
    /**
     *
     * @param id Int representing the activity's id
     * @param image_path String representing the file path of the image.
     * @param title String representing the activity's title.
     * @param consumption_in_wh Int representing the correct value of the activity's energy consumption.
     * @param source String representing the source of the information.
     */

    public Activity(String id, String image_path, String title, long consumption_in_wh, String source) {
        this.id = id;
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    public Activity(long id, String title, long consumption_in_wh) {
        this.id = Long.toString(id);
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.image_path = "change/this";
        this.source = "change/this";
    }


    public Activity() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getConsumption_in_wh() {
        return consumption_in_wh;
    }

    public void setConsumption_in_wh(long consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return consumption_in_wh == activity.consumption_in_wh && id.equals(activity.id)
                && image_path.equals(activity.image_path) && title.equals(activity.title)
                && source.equals(activity.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_path, title, consumption_in_wh, source);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", image_path='" + image_path + '\'' +
                ", title='" + title + '\'' +
                ", consumption_in_wh=" + consumption_in_wh +
                ", source='" + source + '\'' +
                '}';
    }
}
