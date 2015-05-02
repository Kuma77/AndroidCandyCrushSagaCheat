import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mike on 5/2/2015.
 */
public class CandyApp {
    int modifiedFiles = 0;
    String apkName;

    private static void modifyProperties(String filePath) {
        PropSettings prop = new PropSettings(filePath);
        prop.ModifyStoredLives();
        prop.Write();
    }

    CandyApp(String appName) {
        apkName = appName;
    }

    private void modifyLevels(String filePath) {
        File folder = new File(filePath + "levels");
        final File[] dirFiles = folder.listFiles();

        int nThreads = Runtime.getRuntime().availableProcessors();
        Executor exec = Executors.newFixedThreadPool(nThreads);
        ExecutorService service = Executors.newFixedThreadPool(nThreads);
        for(int i = 0; i < dirFiles.length; i++) {
            if(dirFiles[i].isFile()) {
                service.execute(new Runnable() {
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
        service.shutdown();
        try {
            while(service.isTerminated() != true)
                Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.println("Modified " + modifiedFiles + " files/levels!");
    }

    private void exec(String option) {
        String output = "dudud";
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "apktool_2.0.0rc4.jar", option, apkName,
                "-o", output);
        try {
            Process pApkTool = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decompile() {
        exec("d");
    }

    public void compile() {
        exec("b");
    }


    public void makeLevelChanges() {
        modifyLevels();
        modifyProperties();
    }
}
