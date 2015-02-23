/**
 * Created by mike on 2/22/2015.
 */
public class ModifyLevel {

    public ModifyLevel(String filePath, String jsonData) {
        JsonLevel jlevel = new JsonLevel(jsonData);
        jlevel.modifyNoMoves();
        jlevel.modifyTimeLimit();
        jlevel.writeJSON(filePath);
    }
}
