import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * An implementation of AppFileInterface for payload files for encoding.
 */
public class Payload implements AppFileInterface {
    // A reference to the byte array of the payload
    private byte[] bits;
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
     * Classic getter for the byte array of the payload
     */
    public byte[] getByteArray() {
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
    public int getByteArraySize() {
        if (bits == null)
            return -1;
        else
            return bits.length;
    }

    /**
     * Sets the file for the payload, if the file is successfully processed into a byte array
     * @param file
     * @return
     */
    public File setFile(File file) {
        if (createByteArray(file) == null) {
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
     * Creates a byte array from the file given.
     * @param file
     * @return
     */
    private byte[] createByteArray(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            System.out.println("Bytes read in for " + file.getName());
            return bits = in.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            return bits = null;
        }
    }
}
