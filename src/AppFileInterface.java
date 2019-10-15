import java.io.File;
import java.util.BitSet;

/**
 * An interface for implementing different types of files for the Steganography tool.
 */
public interface AppFileInterface {

    public File getFile();

    public File setFile(File file);

    public byte[] getByteArray();

    public int getByteArraySize();

    public boolean isSet();

    public boolean set(boolean set);
}
