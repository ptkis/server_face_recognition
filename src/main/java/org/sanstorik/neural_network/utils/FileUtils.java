package org.sanstorik.neural_network.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;

public final class FileUtils {
    private static final String IMAGES_FOLDER = "images/";
    private static File tempFile;

    private FileUtils() {}


    /**
     * Finds {@code fileName} in resources root.
     * @param fileName path to image from resources folder.
     * @return file from resources or null if wasn't found
     */
    public static File loadFile(String fileName) {
        URL url = FileUtils.class.getClassLoader().getResource(fileName);
        return url != null ? new File(url.getFile()) : null;
    }


    /**
     * Loads image from resources root.
     * @param imageName path to image from images folder
     * @return image or null if wasn't found or couldn't be read
     */
    public static Image loadImage(String imageName) {
        Image img = null;
        try {
            File file = loadFile(IMAGES_FOLDER + imageName);
            if (file != null) img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


    public static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally{
            if (sourceChannel != null && destChannel != null) {
                sourceChannel.close();
                destChannel.close();
            }
        }
    }


    public static File saveImageAsTemporaryFile(BufferedImage image) {
        return saveImageAsTemporaryFile(image, "/images/recognizer_cache.jpg");
    }


    public static File saveImageAsTemporaryFile(BufferedImage image, String name) {
        File outputFile = new File(getResourcesPath() + name);

        try {
            if (outputFile.exists()) {
                outputFile.delete();
            }

            outputFile.createNewFile();
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile.exists() ? outputFile : null;
    }


    public static void timeSpent(long start, String label) {
        System.out.println("TIME SPENT ON [" + label + "] = " + (System.currentTimeMillis() - start));
    }


    public static String getResourcesPath() {
        return "src/main/resources/";
    }
}