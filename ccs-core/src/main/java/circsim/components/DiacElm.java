package circsim.components;// stub implementation of circsim.DiacElm, based on circsim.SparkGapElm
// FIXME need to add circsim.DiacElm.java to srclist
// FIXME need to uncomment circsim.DiacElm line from circsim.CirSim.java

import circsim.CirSim;
import circsim.EditInfo;

import java.awt.*;
import java.util.StringTokenizer;

public class DiacElm extends CircuitElm {
    public double onresistance, offresistance, breakdown, holdcurrent;
    public boolean state;
    public Point ps3, ps4;

    public DiacElm(int xx, int yy) {
        super(xx, yy);
        // FIXME need to adjust defaults to make sense for diac
        offresistance = 1e9;
        onresistance = 1e3;
        breakdown = 1e3;
        holdcurrent = 0.001;
        state = false;
    }

    public DiacElm(int xa, int ya, int xb, int yb, int f,
                   StringTokenizer st) {
        super(xa, ya, xb, yb, f);
        onresistance = new Double(st.nextToken()).doubleValue();
        offresistance = new Double(st.nextToken()).doubleValue();
        breakdown = new Double(st.nextToken()).doubleValue();
        holdcurrent = new Double(st.nextToken()).doubleValue();
    }

    public boolean nonLinear() {
        return true;
    }

    public int getDumpType() {
        return 203;
    }

    public String dump() {
        return super.dump() + " " + onresistance + " " + offresistance + " "
                + breakdown + " " + holdcurrent;
    }

    public void setPoints() {
        super.setPoints();
        calcLeads(32);
        ps3 = new Point();
        ps4 = new Point();
    }

    public void draw(Graphics g) {
        // FIXME need to draw Diac
        int i;
        double v1 = volts[0];
        double v2 = volts[1];
        setBbox(point1, point2, 6);
        draw2Leads(g);
        setPowerColor(g, true);
        doDots(g);
        drawPosts(g);
    }

    public void calculateCurrent() {
        double vd = volts[0] - volts[1];
        if (state)
            current = vd / onresistance;
        else
            current = vd / offresistance;
    }

    public void startIteration() {
        double vd = volts[0] - volts[1];
        if (Math.abs(current) < holdcurrent) state = false;
        if (Math.abs(vd) > breakdown) state = true;
        //System.out.print(this + " res current set to " + current + "\n");
    }

    public void doStep() {
        if (state)
            sim.stampResistor(nodes[0], nodes[1], onresistance);
        else
            sim.stampResistor(nodes[0], nodes[1], offresistance);
    }

    public void stamp() {
        sim.stampNonLinear(nodes[0]);
        sim.stampNonLinear(nodes[1]);
    }

    public void getInfo(String[] arr) {
        // FIXME
        arr[0] = "spark gap";
        getBasicInfo(arr);
        arr[3] = state ? "on" : "off";
        arr[4] = "Ron = " + getUnitText(onresistance, CirSim.ohmString);
        arr[5] = "Roff = " + getUnitText(offresistance, CirSim.ohmString);
        arr[6] = "Vbrkdn = " + getUnitText(breakdown, "V");
        arr[7] = "Ihold = " + getUnitText(holdcurrent, "A");
    }

    public EditInfo getEditInfo(int n) {
        if (n == 0)
            return new EditInfo("On resistance (ohms)", onresistance, 0, 0);
        if (n == 1)
            return new EditInfo("Off resistance (ohms)", offresistance, 0, 0);
        if (n == 2)
            return new EditInfo("Breakdown voltage (volts)", breakdown, 0, 0);
        if (n == 3)
            return new EditInfo("Hold current (amps)", holdcurrent, 0, 0);
        return null;
    }

    public void setEditValue(int n, EditInfo ei) {
        if (ei.value > 0 && n == 0)
            onresistance = ei.value;
        if (ei.value > 0 && n == 1)
            offresistance = ei.value;
        if (ei.value > 0 && n == 2)
            breakdown = ei.value;
        if (ei.value > 0 && n == 3)
            holdcurrent = ei.value;
    }
}

