import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class Payload implements AppFileInterface {

    private BitSet bits;

    private File file;

    private boolean set;

    public Payload() {
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
        else
            return bits.size();
    }

    public File setFile(File file) {
        if (createBitSet(file) == null) {
            return this.file = null;
        } else {
            set(true);
            return this.file = file;
        }
    }

    private BitSet createBitSet(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            System.out.println("Bits read in for " + file.getName());
            return bits = BitSet.valueOf(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            return bits = null;
        }
    }
}
