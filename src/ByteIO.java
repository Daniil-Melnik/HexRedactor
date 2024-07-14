import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Math.toIntExact;

/*
* Класс с утилитами
* Назначение:
* 1) ввод-вывод байт
* 2) преобразования над байтами в А16 и назад
* */

public class ByteIO {
    private final String fName;

    public ByteIO(String fName) {
        this.fName = fName;
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
    public long getFileLength(String fName) {
        long res = 0;
        try (RandomAccessFile file = new RandomAccessFile(fName, "r")) {
            res = file.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /////////////////////////////////////////////////////////////////
    ////////////// Перевести массив 16-строк в байты ////////////////
    /////////////////////////////////////////////////////////////////
    public byte[] transformToBytesArr(String[] data) {
        int endIndex = data.length - 1;
        int nStars = 0;
        for (int i = endIndex; i >= 0; i--) {
            if (data[i].equals("**")) {
                nStars++;
            }
        }
        int actualLen = data.length - nStars;
        byte[] res = new byte[actualLen];
        int cnt = 0;
        for (int i = 0; i < data.length; i++) {
            if (!data[i].equals("**")) {
                res[cnt] = (byte) (Integer.parseInt(data[i], 16));
                cnt++;
            }
        }
        return res;
    }

    ////////////////////////////////////////////////////////////////
    ////////// Разбить входную строку вставки на массив ////////////
    ////////////////////////////////////////////////////////////////
    public String[] splitHexBytes(String inHexStr) {
        return inHexStr.split(";");
    }

    ////////////////////////////////////////////////////////////////
    ////////// Разбить входную строку вставки на массив ////////////
    ////////////////////////////////////////////////////////////////
    public void printData(int offt, String[] data, int dLen, String newName) {
        String tmpFileName = "tmpFile.txt";
        File tmpFile = new File(tmpFileName);
        int index = 0;

        byte[] fullPackDataByte = new byte[8];
        byte[] preEmptyDataByte = new byte[offt % 8];
        String[] fullPackDataStr = new String[8];
        String[] preEmptyDataStr = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(tmpFile, "rw");

            int nFullPacks = offt / 8;

            for (int i = 0; i < nFullPacks; i++) {
                fullPackDataStr = getHexBytesOfft(index, 8);

                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);

                index += 8;
            }

            preEmptyDataStr = getHexBytesOfft(index, offt % 8);
            preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
            randomAccessFile.write(preEmptyDataByte);

            index = offt;

            randomAccessFile.write(transformToBytesArr(data));

            index += data.length + dLen; // добавлен допю сдвиг 21.01.2024

            long pInd = (getFileLength(this.fName) - index) / 8;

            for (int k = 0; k < pInd; k++) {
                fullPackDataStr = getHexBytesOfft(index, 8);
                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);
                index += 8;
            }
            if (index < getFileLength(this.fName)) {
                int nPreEmptyBytes = toIntExact((getFileLength(this.fName) - index) % 8);
                preEmptyDataStr = getHexBytesOfft(index, nPreEmptyBytes);
                preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
                randomAccessFile.write(preEmptyDataByte);
                index += nPreEmptyBytes;
            }

            randomAccessFile.close();
            FileManager fM = new FileManager();
            boolean isCopy = newName.equals(this.fName) ? false : true;

            fM.setFile(tmpFileName, newName, isCopy);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
