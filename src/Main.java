import java.io.File;

public class Main
{
    private static int newWidth = 300;
    private static String srcPath = "";
    private static String dstPath = "";

    public static void main(String[] args) {
        File srcDir = new File(srcPath);
        File[] files = srcDir.listFiles();
        long start = System.currentTimeMillis();
        int cores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < cores; i++) {
            File[] filesForOneCore;
            if ( i != cores - 1) {
                assert files != null;
                filesForOneCore = new File[files.length / cores];
            }
            else {
                assert files != null;
                filesForOneCore = new File[files.length - (files.length / cores) * i];
            }
            System.arraycopy(files, i * (files.length / cores), filesForOneCore, 0, filesForOneCore.length);
            new Thread(new Resizer(filesForOneCore, newWidth, dstPath, start)).start();
        }
    }
}
