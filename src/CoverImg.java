import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class CoverImg implements AppFileInterface {

    private BitSet bits;

    private File file;

    private boolean set;

    public CoverImg() {
        bits = null;
        file = null;
    }

    public BitSet getBitSet() {
        return bits;
    }

    @Override
    public boolean isSet() {
        return set;
    }

    @Override
    public boolean set(boolean set) {
        return this.set = set;
    }

    @Override
    public int getBitSize() {
        if (bits == null)
            return -1;
        else {
            return (bits.size()-528);
        }
    }

    public File setFile(File file) {
        if (verifyImage(file)) {
            createBitSet(file);
            set(true);
            return this.file = file;
        } else
            return this.file = null;
    }

    private boolean verifyImage(File file) {
        try {
            byte[] type = new FileInputStream(file).readNBytes(2);
            System.out.println(type[0] + " " + type[1]);
            if (type[0] != 0x42 || type[1] != 0x4D) return false;
            System.out.println(ImageIO.read(file).getColorModel().getPixelSize());
            return ImageIO.read(file).getColorModel().getPixelSize() == 24;
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            return false;
        }
    }

    private void createBitSet(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            bits = BitSet.valueOf(in.readAllBytes());
            System.out.println("Bits read in for " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            bits = null;
        }
    }
}
