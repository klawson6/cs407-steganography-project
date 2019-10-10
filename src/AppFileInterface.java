import java.io.File;
import java.util.BitSet;

/**
 * An interface for implementing different types of files for the Steganography tool.
 */
public interface AppFileInterface {

    public File getFile();

    public File setFile(File file);

    public BitSet getBitSet();

    public int getBitSize();

    public boolean isSet();

    public boolean set(boolean set);
}
