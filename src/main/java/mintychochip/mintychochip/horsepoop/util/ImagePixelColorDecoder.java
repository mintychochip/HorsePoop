package mintychochip.mintychochip.horsepoop.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.listener.display.RGB;

public class ImagePixelColorDecoder {

  public static RGB[][] decode(String filePath) {
    File img = new File(HorsePoop.getInstance().getDataFolder(), filePath);
    RGB[][] rgbs;
    try {
      BufferedImage read = ImageIO.read(img);


      int width = read.getWidth();
      int height = read.getHeight();
      rgbs = new RGB[width][height];

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          int rgb = read.getRGB(x, y);
          rgbs[x][y] = new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return rgbs;
  }
}
