package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ActivityRepository extends JpaRepository<Activity,String> {

   /**
    * Finds if an activity exists in the repository
    * @param id String representing the Id of the activity
    * @return True if the activity exists in the repository
    */
 //  boolean existsById(String id);

   /**
    * Deletes an activity with the give Id from the repository
    * @param activityId String representing the Id of the activity
    */

  // void deleteById(String activityId);
}
