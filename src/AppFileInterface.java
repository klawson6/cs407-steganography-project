import java.io.File;
import java.util.BitSet;

public interface AppFileInterface {

    public File setFile(File file);

    public BitSet getBitSet();

    public int getBitSize();

    public boolean isSet();

    public boolean set(boolean set);
}
