package circsim;

public interface ImportExportDialog {
    public void setDump(String dump);

    public void execute();

    public enum Action {IMPORT, EXPORT}
}

