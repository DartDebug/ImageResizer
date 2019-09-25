import java.io.File;

public class Main
{
    private static int newWidth = 300;
    private static String srcPath = "C:\\Users\\D_AntipovA\\Desktop\\images";
    private static String dstPath = "C:\\Users\\D_AntipovA\\Desktop\\Dst";

    public static void main(String[] args) {
        File srcDir = new File(srcPath);
        File[] files = srcDir.listFiles();
        long start = System.currentTimeMillis();
        File[] files1 = new File[files.length / 2];
        System.arraycopy(files, 0, files1, 0, files1.length);
        File[] files2 = new File[files.length - files.length / 2];
        System.arraycopy(files, files.length / 2, files2, 0, files2.length);
        int cores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < cores; i++) {
            File[] filesForOneCore;
            if ( i != cores - 1) {
                filesForOneCore = new File[files.length / cores];
            }
            else {
                filesForOneCore = new File[files.length - (files.length / cores) * i];
            }
            System.arraycopy(files, i * (files.length / cores), filesForOneCore, 0, filesForOneCore.length);
            new Thread(new Resizer(filesForOneCore, newWidth, dstPath, start)).start();
        }
    }
}
