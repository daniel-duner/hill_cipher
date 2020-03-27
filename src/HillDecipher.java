import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.ModuloInteger;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HillDecipher {
    public static void main(String[] args) {
            InputHandler inputHandler = new InputHandler();
            inputHandler.argumentCheck(args, 5);
            int radix = inputHandler.stringToInteger(args[0]);
            int blockSize = inputHandler.stringToInteger(args[1]);
            File keyfile = inputHandler.createFile(args[2], true);
            File outputFile = inputHandler.createFile(args[3], false);
            File cipherfile = inputHandler.createFile(args[4], true);
            inputHandler.validateSquareMatrix(keyfile, blockSize);
            MessageHandler messageHandler = new MessageHandler();
            MatrixHandler matrixHandler = new MatrixHandler();

            //Sets Modulo
            ModuloInteger.setModulus(LargeInteger.valueOf(radix));
            //Takes the key file and transforms it into a denseMatrix
            DenseMatrix key = matrixHandler.invertibleMatrix(matrixHandler.createMatrix(keyfile, blockSize, radix), radix).inverse();
            //takes a cipherfile and converts the message to an integer array
            List<ModuloInteger> message = messageHandler.sequenceToArray(cipherfile);
            //Decrypts the message using the inverted key matrix and removes the padding
            List<ModuloInteger> decryptedMessage = messageHandler.removePadding(blockSize, decrypt(key, message, blockSize));
            //writes the message to file
            messageHandler.writeMessageToFile(decryptedMessage, outputFile);
    }

    private static List<ModuloInteger> decrypt(DenseMatrix<ModuloInteger> key, List<ModuloInteger> message, int blockSize) {
        List<ModuloInteger> decrypted = new ArrayList<>();
        int messageSize = message.size();
        for (int i = 0; i < messageSize; i += blockSize) {
            List<ModuloInteger> vector = new ArrayList<>();
            for (int j = 0; j < blockSize; j++) {
                vector.add(message.remove(0));
            }
            DenseVector<ModuloInteger> result = key.times(DenseVector.valueOf(vector));
            for (int k = 0; k < blockSize; k++) {
                    decrypted.add(result.get(k));
            }
        }
        return decrypted;
    }

    }
