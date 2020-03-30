import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.ModuloInteger;
import org.jscience.mathematics.vector.DenseMatrix;

import java.io.File;
/*
I dedicate this program to Jscience, they made this possible. They have an amazing library http://jscience.org/
 */
public class HillKeys {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        MatrixHandler mh = new MatrixHandler();

        inputHandler.argumentCheck(args, 3);
        int radix = inputHandler.stringToInteger(args[0]);
        int blockSize = inputHandler.stringToInteger(args[1]);
        File output = inputHandler.createFile(args[2], false);
        ModuloInteger.setModulus(LargeInteger.valueOf(radix));
        DenseMatrix matrix = mh.createRandomMatrix(blockSize, radix);
        mh.matrixTofile(matrix, blockSize, output);
    }
}