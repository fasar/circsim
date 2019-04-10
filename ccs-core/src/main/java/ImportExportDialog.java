public interface ImportExportDialog {
    void setDump(String dump);

    void execute();

    enum Action {IMPORT, EXPORT}
}

