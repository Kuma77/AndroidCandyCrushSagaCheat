/**
 * Created by mike on 2/21/2015.
 */
public class Main {

    public static void main(String[] args) {
        final String filePath;

        if(args.length == 0) {
            System.out.println("Please privde the res_output directory path!");
            return;
        }
        filePath = args[0];

        CandyApp app = new CandyApp(filePath);

        app.decompile();

        app.makeLevelChanges();

        app.compile();

    }

}
