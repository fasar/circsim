package circsim.components;

import circsim.CirSim;
import circsim.EditInfo;

import java.awt.*;
import java.util.StringTokenizer;

public class VarRailElm extends RailElm {
    public Scrollbar slider;
    public Label label;
    public String sliderText;

    public VarRailElm(int xx, int yy) {
        super(xx, yy, WF_VAR);
        sliderText = "Voltage";
        frequency = maxVoltage;
        createSlider();
    }

    public VarRailElm(int xa, int ya, int xb, int yb, int f,
                      StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
        sliderText = st.nextToken();
        while (st.hasMoreTokens())
            sliderText += ' ' + st.nextToken();
        createSlider();
    }

    public String dump() {
        return super.dump() + " " + sliderText;
    }

    public int getDumpType() {
        return 172;
    }

    public void createSlider() {
        waveform = WF_VAR;
        CirSim.main.add(label = new Label(sliderText, Label.CENTER));
        int value = (int) ((frequency - bias) * 100 / (maxVoltage - bias));
        CirSim.main.add(slider = new Scrollbar(Scrollbar.HORIZONTAL, value, 1, 0, 101));
        CirSim.main.validate();
    }

    public double getVoltage() {
        frequency = slider.getValue() * (maxVoltage - bias) / 100. + bias;
        return frequency;
    }

    public void delete() {
        CirSim.main.remove(label);
        CirSim.main.remove(slider);
    }

    public EditInfo getEditInfo(int n) {
        if (n == 0)
            return new EditInfo("Min Voltage", bias, -20, 20);
        if (n == 1)
            return new EditInfo("Max Voltage", maxVoltage, -20, 20);
        if (n == 2) {
            EditInfo ei = new EditInfo("Slider Text", 0, -1, -1);
            ei.text = sliderText;
            return ei;
        }
        return null;
    }

    public void setEditValue(int n, EditInfo ei) {
        if (n == 0)
            bias = ei.value;
        if (n == 1)
            maxVoltage = ei.value;
        if (n == 2) {
            sliderText = ei.textf.getText();
            label.setText(sliderText);
        }
    }

    public int getShortcut() {
        return 0;
    }
}