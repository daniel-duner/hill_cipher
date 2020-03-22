import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.org.jscience.physics.units.SI.*;


public class Matrix {
  int radix;
  int blockSize;
  File outputFile;
  int[][] matrix;

  public Matrix(int radix, int blockSize, File outputFile) {
    this.radix = radix;
    this.blockSize = blockSize;
    this.outputFile = outputFile;
    generateMatrix();
    System.out.println("Determinant: "+determinantOfMatrix(this.matrix, this.blockSize));
  }
  public Matrix(int radix, int blockSize, int[][] matrix){
    this.radix = radix;
    this.blockSize = blockSize;
    this.matrix = matrix;
  }

  public void toFile() {
    try{
      PrintWriter writer = new PrintWriter(this.outputFile);
      for(int i = 0; i < this.blockSize; i++){
        for(int j = 0; j < this.blockSize; j++){
          if(j+1 != this.blockSize){
            writer.print(this.matrix[i][j]+" ");
          } else {
            writer.println(this.matrix[i][j]);
          }
        }
      }
      writer.close();
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  private void generateMatrix() {
    this.matrix = new int[this.blockSize][this.blockSize];
    for (int i = 0; i < this.blockSize; i++) {
      for (int j = 0; j < this.blockSize; j++) {
        this.matrix[i][j]=randomNumberGenerator();
      }
    }
    if(determinantOfMatrix(this.matrix, this.blockSize) == 0){
      generateMatrix();
    }
  }

  private int randomNumberGenerator(){
    Random rand = new Random();
    return rand.nextInt(radix); 
  }

  private int determinantOfMatrix(int mat[][], int n) {
    int D = 0;
    if (n == 1)
      return mat[0][0];
    int temp[][] = new int[this.blockSize][this.blockSize];
    int sign = 1;

    for (int f = 0; f < n; f++) {
      getCofactor(mat, temp, 0, f, n);
      D += sign * mat[0][f] * determinantOfMatrix(temp, n - 1);
      sign = -sign;
    }

    return D;
  }

  private void getCofactor(int mat[][], int temp[][], int p, int q, int n) {
    int i = 0, j = 0;
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (row != p && col != q) {
          temp[i][j++] = mat[row][col];
          if (j == n - 1) {
            j = 0;
            i++;
          }
        }
      }
    }
  }
}