import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mike on 2/21/2015.
 */
public class Main {
    static int modifiedFiles = 0;
    public static void main(String[] args) {
        final String filePath;

        if(args.length == 0) {
            System.out.println("Please privde the directory path!");
            return;
        }

        filePath = args[0];

        File folder = new File(filePath);
        final File[] dirFiles = folder.listFiles();

        int nThreads = Runtime.getRuntime().availableProcessors();
        Executor exec = Executors.newFixedThreadPool(nThreads);
        ExecutorService service;
        for(int i = 0; i < dirFiles.length; i++) {
            if(dirFiles[i].isFile()) {
                exec.execute(new Runnable() {
                    int idx;
                    @Override
                    public void run() {
                        ReadLevel rLevel = new ReadLevel(dirFiles[idx].getAbsolutePath());
                        ModifyLevel modLevel = new ModifyLevel(dirFiles[idx].getAbsolutePath(), rLevel.levelData);
                        modifiedFiles++;
                    }
                    public Runnable setIdx(int i) {
                        idx = i;
                        return this;
                    }
                }.setIdx(i));

            }
        }

        System.out.println("Modified " + modifiedFiles + " files/levels!");
    }
}
