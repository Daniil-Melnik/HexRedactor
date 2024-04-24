import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import static java.lang.Math.toIntExact;

public class ByteIO {
    private final String fName;

    public ByteIO(String fName) {
        this.fName = fName;
    }

    ////////////////////////////////////////////////////////////////
    ///////////////////// Получить все байты ///////////////////////
    ////////////////////////////////////////////////////////////////

    public String[] getHexBytesAll() throws IOException {
        Path path = Paths.get(this.fName);
        byte[] bytes = Files.readAllBytes(path);
        String[] hexBytes = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hexBytes[i] = Integer.toHexString(bytes[i]);
        }
        return hexBytes;
    }

    ////////////////////////////////////////////////////////////////
    ///////////////// Получить байты по смещению ///////////////////
    ////////////////////////////////////////////////////////////////

    public String[] getHexBytesOfft(int offt, int len) {
        String[] hexBytesOfft = null;
        try (RandomAccessFile file = new RandomAccessFile(this.fName, "r")) {
            byte[] bytes = new byte[len];
            file.seek(offt);
            file.read(bytes);
            hexBytesOfft = new String[len];
            for (int i = 0; i < len; i++) {
                hexBytesOfft[i] = String.format("%02X", bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexBytesOfft;
    }

    /////////////////////////////////////////////////////////////////
    //////////////// Получить длину файла в байтах //////////////////
    /////////////////////////////////////////////////////////////////
    public long getFileLength(){
        long res = 0;
        try (RandomAccessFile file = new RandomAccessFile(this.fName, "r")) {
            res = file.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /////////////////////////////////////////////////////////////////
    ////////////// Перевести массив 16-строк в байты ////////////////
    /////////////////////////////////////////////////////////////////
    public byte [] transformToBytesArr(String [] data){
        int endIndex = data.length - 1;
        int nStars = 0;
        for (int i = endIndex; i >= 0; i--){
            if (data[i].equals("**")){
                nStars++;
            }  
        }
        int actualLen = data.length  - nStars;
        byte [] res = new byte[actualLen];
        int cnt = 0;
        for (int i = 0; i < data.length; i++){
            if (!data[i].equals("**")){
                res[cnt] = Byte.parseByte(data[i], 16);
                cnt++;
            }
            //res[i] = data[i].getBytes()[0];
            //System.out.println(res[i] + " " + (byte)(Integer.parseInt(data[i], 16)));
        }
        return res;
    }

    public void printData(int offt, String[] data, int dLen) {
        File file = new File("example.txt");
        int index = 0;

        byte [] fullPackDataByte = new byte [8];
        byte [] preEmptyDataByte = new byte [offt % 8];
        String [] fullPackDataStr = new String[8];
        String [] preEmptyDataStr = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            int nFullPacks = offt / 8;
            System.out.println("Сдвиг = " + offt);
            System.out.println(index + " - начало");

            for (int i = 0; i < nFullPacks; i++){
                fullPackDataStr = getHexBytesOfft(index, 8);

                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);
                System.out.println("File has been written successfully");

                index += 8;
                System.out.println(index + " - полная до даты");
            }

            preEmptyDataStr = getHexBytesOfft(index, offt % 8);
            preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
            randomAccessFile.write(preEmptyDataByte);

            index = offt;
            System.out.println(index + " - перед датой");

            randomAccessFile.write(transformToBytesArr(data));

            index += data.length + dLen; // добавлен допю сдвиг 21.01.2024
            System.out.println(index + " - вписали дату");

            long pInd = (getFileLength() - index) / 8;

            for (int k = 0; k < pInd; k++){
                fullPackDataStr = getHexBytesOfft(index, 8);
                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);
                index += 8;
                System.out.println(index + " - полная после даты");
            }
            System.out.println(index + " - перед огузком");
            int nPreEmptyBytes = toIntExact((getFileLength() - index) % 8);
            preEmptyDataStr = getHexBytesOfft(index,  nPreEmptyBytes);
            preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
            randomAccessFile.write(preEmptyDataByte);
            index += nPreEmptyBytes;
            System.out.println(index + " - конец");
            System.out.println("== " + getFileLength());

            randomAccessFile.close();
            FileManager fM = new FileManager();
            fM.setFile("example.txt", "src/1.txt");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ByteIO bIO = new ByteIO("src/1.txt");
        //String [] hexBytes = bIO.getHexBytesOfft(0, 40);
        String [] data = {"40", "40", "40", "40", "40", "40", "40", "40", "40", "40"};
        bIO.printData(4, data, 0);
    }
}











