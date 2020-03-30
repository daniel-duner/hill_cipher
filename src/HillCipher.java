import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.ModuloInteger;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
I dedicate this program to Jscience, they made this possible. THey have an amazing library http://jscience.org/
 */
public class HillCipher {
    public static void main(String[] args) {
            InputHandler inputHandler = new InputHandler();
            MessageHandler messageHandler = new MessageHandler();
            MatrixHandler matrixHandler = new MatrixHandler();
            inputHandler.argumentCheck(args, 5);

            int radix = inputHandler.stringToInteger(args[0]);
            int blockSize = inputHandler.stringToInteger(args[1]);
            File keyfile = inputHandler.createFile(args[2], true);
            File plainfile = inputHandler.createFile(args[3], true);
            File cipherfile = inputHandler.createFile(args[4], false);
            inputHandler.validateSquareMatrix(keyfile, blockSize);

            //Sets Modulo to radix
            ModuloInteger.setModulus(LargeInteger.valueOf(radix));
            //Takes the key-file and transforms it into a denseMatrix
            DenseMatrix<ModuloInteger> keyMatrix = matrixHandler.createMatrix(keyfile, blockSize,radix);
            //Takes a message in  and converts the message to an integer array
            List<ModuloInteger> message = messageHandler.getPaddedMessage(blockSize, plainfile);
            //Encrypts the message using the key matrix and writes the sequence to a file
            messageHandler.writeMessageToFile(encrypt(keyMatrix, message, blockSize), cipherfile);

    }

    private static List<ModuloInteger> encrypt(DenseMatrix<ModuloInteger> key, List<ModuloInteger> message, int blockSize){
        List<ModuloInteger> encrypted = new ArrayList<>();
        int messageSize = message.size();
        for (int i = 0; i < messageSize; i += blockSize) {
            List<ModuloInteger> vector = new ArrayList<>();
            for (int j = 0; j < blockSize; j++) {
                vector.add(message.remove(0));
            }
            DenseVector<ModuloInteger> result = key.times(DenseVector.valueOf(vector));
            for (int k = 0; k < blockSize; k++) {
                encrypted.add( result.get(k));
            }
        }
        return encrypted;
    }


}

