import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resizer implements Runnable {

    private File[] files;
    private int newWidth;
    private String dstPath;
    private long start;

    public Resizer(File[] files, int newWidth, String dstPath, long start) {
        this.files = files;
        this.newWidth = newWidth * 2;
        this.dstPath = dstPath;
        this.start = start;
    }

    @Override
    public void run() {
        for (var file: files) {
            try {
                BufferedImage image = ImageIO.read(file);
                if(image == null) continue;
                int newHeight = Math.round(image.getHeight() / (image.getWidth() / newWidth));
                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;
                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }
                newImage.createGraphics().transform(AffineTransform.getScaleInstance(2,2));
                File newFile = new File(dstPath + "\\" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("time left: " + (System.currentTimeMillis() - start) + "ms");
    }
}
