import java.awt.*;

public class FileSelector implements GUIInput{
    private String filename;

    @Override
    public void OpenDialog(){
        FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.LOAD);
        fd.setDirectory("./");
        fd.setFile("*.csv");
        boolean fileFound = false;
        while (!fileFound) {
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null){
                System.out.println("You cancelled the choice");
                System.exit(0);
        }else{
                if(filename.endsWith(".csv")){
                    this.filename = fd.getDirectory() + filename;
                    fileFound = true;
                    fd.dispose();
                }
            }
        }
    }

    @Override
    public String GetFileName() {
        return this.filename;
    }
}
