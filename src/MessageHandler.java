import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.ModuloInteger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageHandler {
    public List<ModuloInteger> getPaddedMessage(int blocksize, File messageFile){
        List<ModuloInteger> message = sequenceToArray(messageFile);
        return addPadding(blocksize, message);
    }

    private List<ModuloInteger> addPadding(int blocksize, List<ModuloInteger> message){
        int padding =  blocksize-(message.size()%blocksize);
        int paddingLength = padding;
        if(padding == 0) {
            paddingLength = blocksize;
        }
        for(int i = 0; i< paddingLength;i++){
            message.add(ModuloInteger.valueOf(LargeInteger.valueOf(padding)));
        }
        return message;
    }

    public List<ModuloInteger> removePadding(int blockSize, List<ModuloInteger> message){
        ModuloInteger lastElement = message.remove(message.size()-1);
        long paddingLength = lastElement.longValue()-1;
        if(paddingLength == -1){
            paddingLength =  blockSize-1;
        }
        for(int i = 0; i < paddingLength;i++){
            message.remove(message.size()-1);
        }
        return message;
    }

    public List<ModuloInteger> sequenceToArray(File sequence){
        InputHandler inputHandler = new InputHandler();
        try{
        Scanner scanner = new Scanner(sequence);
        List<ModuloInteger> numbersArray = new ArrayList<>();
        String[] messageString = scanner.nextLine().split(" ");
        for(String str : messageString){
            inputHandler.validateInteger(str);
            numbersArray.add(ModuloInteger.valueOf(LargeInteger.valueOf(Integer.parseInt(str))));
        }
        return numbersArray;
        } catch (IOException e){
            Terminator.terminate("Could not read file: "+sequence);
            return null;
        }
    }
    public void writeMessageToFile(List<ModuloInteger> message, File output) {
        try {
            FileWriter writer = new FileWriter(output);
            for (ModuloInteger moduloInteger : message) {
                writer.append(moduloInteger + " ");
            }
            writer.close();
        } catch (IOException e) {
            Terminator.terminate("Could not write file: " + output);
        }
    }
}
