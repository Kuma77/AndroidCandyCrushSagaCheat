import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mike on 5/2/2015.
 */
public class CandyApp {
    final static String RESOURCES_PATH =
            "C:\\Users\\mike\\IdeaProjects\\CandyCrushLevelModifiy\\src\\main\\resources\\";
    final static String APKTOOL_PATH = RESOURCES_PATH + "apktool_2.0.0rc4.jar";
    static String ASSETS_RES_PATH = "\\assets\\res_output\\";
    static String LEVELS_PATH = "levels";
    static String outputFileName = "output_app.apk";
    final String OUTPUT_FOLDER;
    final String MODDED_APK_PATH;
    final String SIGNED_APK_PATH;

    int modifiedFiles = 0;
    String apkName;
    String filePath;

    private void modifyProperties() {
        PropSettings prop = new PropSettings(filePath);
        prop.ModifyStoredLives();
        prop.Write();
    }

    CandyApp(String appName) {
        File file = new File(appName);
        apkName = file.getName();
        String absolutePath = file.getAbsolutePath();
        filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)) + "\\";
        OUTPUT_FOLDER = apkName.substring(0, apkName.lastIndexOf("."));
        MODDED_APK_PATH = filePath + "moded_app.apk";
        SIGNED_APK_PATH = filePath + "signed_moded_app.apk";
    }

    private void modifyLevels() {
        File folder = new File(filePath + OUTPUT_FOLDER + ASSETS_RES_PATH + LEVELS_PATH);
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
                        String levelpath = dirFiles[idx].getAbsolutePath();
                        ReadLevel rLevel = new ReadLevel(levelpath);
                        ModifyLevel modLevel = new ModifyLevel(levelpath, rLevel.levelData);
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

    private void exec(String [] processArguments) {
        ProcessBuilder processBuilder = new ProcessBuilder(processArguments);
        try {
            Process process = processBuilder.start();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStreamReader isr = new InputStreamReader(process.getErrorStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<ERROR>");
            while ( (line = br.readLine()) != null)
                System.out.println(line);
            System.out.println("</ERROR>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decompile() {
        System.out.println("Decompiling APK please wait ....");

        String[] decompileArgs = {
                "java",
                "-jar",
                APKTOOL_PATH,
                "d",
                filePath + apkName,
                "-o",
                OUTPUT_FOLDER
        };

        exec(decompileArgs);
    }

    public void compile() {
        System.out.println("Compiling APK please wait ....");

        String[] compileArgs = {
            "java",
            "-jar",
            APKTOOL_PATH,
            "b",
            filePath + OUTPUT_FOLDER,
            "-o",
            MODDED_APK_PATH
        };
        exec(compileArgs);
    }

    public void sign() {
        System.out.println("Signing APK please wait ....");

        String [] signArgs = {
            "java", "-jar",
            RESOURCES_PATH + "signapk.jar",
            "-w",
            RESOURCES_PATH + "testkey.x509.pem",
            RESOURCES_PATH + "testkey.pk8",
            MODDED_APK_PATH,
            SIGNED_APK_PATH
        };
        exec(signArgs);
    }

    public void makeLevelChanges() {
        modifyLevels();
        modifyProperties();
    }
}
