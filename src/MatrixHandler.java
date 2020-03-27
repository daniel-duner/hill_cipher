import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.ModuloInteger;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class MatrixHandler {
    public DenseMatrix<ModuloInteger> createMatrix(File matrixFile, int blockSize, int radix){
        try{
        InputHandler inputHandler = new InputHandler();
        Scanner reader = new Scanner(matrixFile);
        ModuloInteger[][] matrix = new ModuloInteger[blockSize][blockSize];
        int size = 0;
        while (reader.hasNext()) {
            String[] row = reader.nextLine().split(" ");
            for (int i = 0; i < blockSize; i++) {
                inputHandler.validateInteger(row[i]);
                validateNumber(Integer.parseInt(row[i]), radix);
                matrix[size][i] = ModuloInteger.valueOf(LargeInteger.valueOf(row[i]));
            }
            if (size == blockSize) {
                break;
            }
            size++;
        }
        reader.close();
        return DenseMatrix.valueOf(matrix);
        } catch (FileNotFoundException e){
            Terminator.terminate("File "+matrixFile+" was not found");
            return null;
        }
    }

    public void validateNumber(int number, int radix){
        if(number >= radix || number < 0){
            Terminator.terminate("Number is not less than radix: "+radix+" or is a negative number, received number: "+number);
        }
    }

    public DenseMatrix<ModuloInteger> createRandomMatrix(int blockSize, int radix){
        ModuloInteger.setModulus(LargeInteger.valueOf(radix));
        ModuloInteger determinant = ModuloInteger.ZERO;
        DenseMatrix<ModuloInteger> denseMatrix = null;
        while(determinant.longValue()==0){
            try{
                denseMatrix = generateRandomMatrix(blockSize, radix);
                determinant =  denseMatrix.determinant();
            }catch(Exception e){
                determinant = ModuloInteger.ZERO;
            }
        }
        return denseMatrix;
    }

    public DenseMatrix<ModuloInteger>invertibleMatrix(DenseMatrix<ModuloInteger> matrix, int radix) {
        ModuloInteger.setModulus(LargeInteger.valueOf(radix));
        try {
            if (matrix.determinant().longValue() == 0) {
                throw new ArithmeticException();
            }
            return matrix;
        } catch (ArithmeticException e) {
            Terminator.terminate("Invalid key, the key is not invertible");
            return null;
        }
    }

    private DenseMatrix<ModuloInteger> generateRandomMatrix(int blockSize, int radix){
        ModuloInteger[][] matrix = new ModuloInteger[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                matrix[i][j]=randomNumberGenerator(radix);
            }
        }
        return DenseMatrix.valueOf(matrix);

    }

    private ModuloInteger randomNumberGenerator(int radix){
        Random rand = new Random();
        ModuloInteger.setModulus(LargeInteger.valueOf(radix));
        return ModuloInteger.valueOf(LargeInteger.valueOf(rand.nextInt(radix)));
    }

    public void matrixTofile(DenseMatrix<ModuloInteger> matrix,int blockSize, File output) {
        try{
            PrintWriter writer = new PrintWriter(output);
            for(int i = 0; i < blockSize; i++){
                for(int j = 0; j < blockSize; j++){
                    if(j+1 != blockSize){
                        writer.print(matrix.get(i,j)+" ");
                    } else {
                        writer.println(matrix.get(i,j));
                    }
                }
            }
            writer.close();
        } catch(IOException e){
            Terminator.terminate("Could not write file: "+output);
        }
    }
}
