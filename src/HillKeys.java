import java.io.File;

public class HillKeys {
  public static void main(String[] args) {
    try {
      int radix = Integer.parseInt(args[0]);
      int blockSize = Integer.parseInt(args[1]);
      if (args[2].indexOf(".txt") > -1) {
        File outputFile = new File(args[2]);
        Matrix key = new Matrix(radix, blockSize, outputFile);
        key.toFile();
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}