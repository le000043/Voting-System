public class Input {
    private String fileName;
    private String flags;
    private GUIInput guiInput;

    public Input(String flags) {
        if(flags.contains("-m")){
            guiInput = new CreateBallot();
        }else if(flags.contains(".csv")){
            fileName = flags;
        }
        else {
            guiInput = new FileSelector();
        }
    }
    public String GetFileName(){
        if (fileName!=null){
            return fileName;
        }
        else {
            guiInput.OpenDialog();
            return guiInput.GetFileName();
        }
    }




}
