import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * An implementation of AppFileInterface for cover image files for encoding.
 */
public class CoverImg implements AppFileInterface {
    // A reference to the byte array of the cover image
    private byte[] bits;
    // A reference to the cover image file
    private File file;
    // A flag to indicate if the cover image file has been set
    private boolean set;

    // Initialise
    public CoverImg() {
        bits = null;
        file = null;
    }

    /**
     * Classic getter for the byte array of the cover image
     */
    public byte[] getByteArray() {
        return bits;
    }

    /**
     * Classic getter for getting the cover image file flag
     * @return
     */
    @Override
    public boolean isSet() {
        return set;
    }

    /**
     * Classic setter for setting the cover image file flag
     * @return
     */
    @Override
    public boolean set(boolean set) {
        return this.set = set;
    }

    /**
     * Classic getter for getting the byte array size
     * @return
     */
    @Override
    public int getByteArraySize() {
        if (bits == null)
            return -1;
        else {
            return (bits.length-103);   //TODO change to correct byte equivalent
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    /**
     * Sets the file for the cover image, if the file is successfully processed into a byte array
     * @param file
     * @return
     */
    public File setFile(File file) {
        if (verifyImage(file)) {
            createByteArray(file);
            set(true);
            return this.file = file;
        } else
            return this.file = null;
    }

    /**
     * Verifies the given file as appropriate to be processed by the encoder.
     * The file must be a bitmap image of 24 bit colour depth.
     * @param file
     * @return
     */
    private boolean verifyImage(File file) {
        try {
            // Gets the bytes of the file
            byte[] type = new FileInputStream(file).readNBytes(2);
            System.out.println(type[0] + " " + type[1]);
            // Check the bitmap file code at the head of the file
            if (type[0] != 0x42 || type[1] != 0x4D) return false;
            System.out.println(ImageIO.read(file).getColorModel().getPixelSize());
            // Checks the bit depth and returns true if it is 24 and false if not
            return ImageIO.read(file).getColorModel().getPixelSize() == 24;
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            return false;
        }
    }

    /**
     * Creates a byte array from the file given.
     * @param file
     * @return
     */
    private void createByteArray(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            bits = in.readAllBytes();
            System.out.println("Bytes read in for " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "File could not be read. Please select another file.");
            bits = null;
        }
    }
}
