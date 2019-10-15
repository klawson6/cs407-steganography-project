import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * An implementation of AppFileInterface for payload files for encoding.
 */
public class Payload implements AppFileInterface {
    // A reference to the BitSet of the payload
    private BitSet bits;
    // A reference to the payload file
    private File file;
    // A flag to indicate if the payload file has been set
    private boolean set;

    // Initialise
    public Payload() {
        bits = null;
        file = null;
    }

    /**
     * Classic getter for the BitSet of the payload
     */
    public BitSet getBitSet() {
        return bits;
    }

    /**
     * Classic getter for getting the payload file flag
     * @return
     */
    @Override
    public boolean isSet() {
        return set;
    }

    /**
     * Classic setter for setting the payload file flag
     * @return
     */
    @Override
    public boolean set(boolean set) {
        return this.set = set;
    }

    /**
     * Classic getter for getting the BitSet size
     * @return
     */
    @Override
    public int getBitSize() {
        if (bits == null)
            return -1;
        else
            return bits.size();
    }

    /**
     * Sets the file for the payload, if the file is successfully processed into a BitSet
     * @param file
     * @return
     */
    public File setFile(File file) {
        if (createBitSet(file) == null) {
            return this.file = null;
        } else {
            set(true);
            return this.file = file;
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    /**
     * Creates a BitSet from the file given.
     * @param file
     * @return
     */
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
