
import org.json.JSONException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by mike on 2/21/2015.
 */
public class ReadLevel {
    String levelData = new String();

    public ReadLevel(String fileName) {
        Path filePath = FileSystems.getDefault().getPath(fileName);
        try {
            byte fileData[];
            fileData = Files.readAllBytes(filePath);
            levelData = new String(fileData, "UTF-8");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}
