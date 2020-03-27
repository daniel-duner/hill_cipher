import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import javax.naming.SizeLimitExceededException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//inputFile indicates whether the file is meant for input or output
public class InputHandler {
    public File createFile(String path, boolean inputFile){
            File file = new File(path);
        try{
            Path realPath = Paths.get(path);
            file.getCanonicalPath();
            if((!file.exists() || file.isDirectory()) && inputFile){
                throw new IOException();
            }
            if((!file.canRead() && inputFile) || (!file.canWrite() && !inputFile)){
                Terminator.terminate("Could not access file on path: "+path);
            }
        } catch (InvalidPathException | IOException e) {
            Terminator.terminate("No file exists on the path: " + path);
        }
        return file;
    }


    public Integer stringToInteger(String number){
        validateInteger(number);
        return Integer.parseInt(number);
    }
    //Check if Integer
    public void validateInteger(String supposedNumber){
        try{
            int number = Integer.parseInt(supposedNumber);
            if(number < 0){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            Terminator.terminate(supposedNumber+" is not an positive integer number");
        }
    }

    //Checks the amount of arguments
    public void argumentCheck(String[] arguments, int numberOfArguments){
        int inputLength = arguments.length;
        if (inputLength != numberOfArguments) {
            Terminator.terminate("WrongNumberOfArguments expected: "+ 3 + " received "+ inputLength);
        }
    }

    //Validates whether a matrix from a file is a square matrix
    public void validateSquareMatrix(File key, int blockSize){
        try{
        Scanner reader = new Scanner(key);
        String error = "The key is not a "+blockSize+"x"+blockSize+" matrix";
        int size = blockSize;
        while(reader.hasNext()){
           if(reader.nextLine().split(" ").length != blockSize){
               System.out.println(error);
               System.exit(1);
            }
           if(size-- == 0){
               System.out.println(error);
               System.exit(1);
           }
        }
        }catch (FileNotFoundException e){
            Terminator.terminate("File "+key+" was not found");
        }
    }
}