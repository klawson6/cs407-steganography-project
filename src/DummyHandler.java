import java.io.File;

/**
 * Just a dummy class with method headers for Kyle to call. Move along
 */
public class DummyHandler {

    public void checkCoverImg(File f){
        System.out.println(f.getName());
    }

    public void stageFile(File f){
        System.out.println(f.getName() + "\nStaged to be hidden in a file.");
    }

    public void stageCoverImg(File f){
        System.out.println(f.getName() + "\nStage to conceal a file.");
    }
}
