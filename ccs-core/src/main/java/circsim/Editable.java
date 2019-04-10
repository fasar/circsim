package circsim;

public interface Editable {
    public EditInfo getEditInfo(int n);

    public void setEditValue(int n, EditInfo ei);
}
