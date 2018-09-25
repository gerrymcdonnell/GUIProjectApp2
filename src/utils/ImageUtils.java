/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.*;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author gerry
 */
public class ImageUtils {

    public static BufferedImage loadImage(String sFile) {
        BufferedImage bimg = null;
        try {

            bimg = ImageIO.read(new File(sFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    /**
     * Saves a BufferedImage to the given file, pathname must not have any
     * periods "." in it except for the one before the format, i.e. C:/images/fooimage.png
     * @param img
     * @param saveFile
     */
    public static void saveImage(BufferedImage img, String sFile) {
        try {
            String format = (sFile.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(sFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //http://www.javalobby.org/articles/ultimate-image/
    public static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }
    //http://www.javalobby.org/articles/ultimate-image/
    public static BufferedImage verticalflip(BufferedImage img) {
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage dimg = dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
            Graphics2D g = dimg.createGraphics();
            g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
            g.dispose();
            return dimg;
    }

    public static BufferedImage rotate(BufferedImage img, int angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawImage(img, null, 0, 0);
        return dimg;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public static BufferedImage makeColorTransparent(BufferedImage image, Color color) {
        BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = dimg.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, 0, 0);
        g.dispose();
        for (int i = 0; i < dimg.getHeight(); i++) {
            for (int j = 0; j < dimg.getWidth(); j++) {
                if (dimg.getRGB(j, i) == color.getRGB()) {
                    dimg.setRGB(j, i, 0x8F1C1C);
                }
            }
        }
        return dimg;
    }

    //heavy duty image operations using filters
    //found at: http://www.java2s.com/Code/Java/2D-Graphics-GUI/ImageFilter.htm
    public static BufferedImage blurImage(BufferedImage image) {
        float[] blurKernel = {
            1 / 9f, 1 / 9f, 1 / 9f,
            1 / 9f, 1 / 9f, 1 / 9f,
            1 / 9f, 1 / 9f, 1 / 9f
        };

        BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, blurKernel));
        image = blur.filter(image, new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType()));
        return image;
    }

    public static BufferedImage sharpenImage(BufferedImage image) {
        float[] sharpenMatrix = {0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f};
        BufferedImageOp sharpenFilter = new ConvolveOp(new Kernel(3, 3, sharpenMatrix),
                ConvolveOp.EDGE_NO_OP, null);
        image = sharpenFilter.filter(image, new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType()));
        return image;
    }

    public static BufferedImage invertImage(BufferedImage image) {
        byte[] invertArray = new byte[256];

        for (int counter = 0; counter < 256; counter++) {
            invertArray[counter] = (byte) (255 - counter);
        }

        BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
        image = invertFilter.filter(image, new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType()));

        return image;
    }

    public static BufferedImage colourFilter(BufferedImage image) {
        float[][] colorMatrix = {{1f, 0f, 0f}, {0.5f, 1.0f, 0.5f}, {0.2f, 0.4f, 0.6f}};
        BandCombineOp changeColors = new BandCombineOp(colorMatrix, null);
        Raster sourceRaster = image.getRaster();
        WritableRaster displayRaster = sourceRaster.createCompatibleWritableRaster();
        changeColors.filter(sourceRaster, displayRaster);
        return new BufferedImage(image.getColorModel(), displayRaster, true, null);
    }
}
