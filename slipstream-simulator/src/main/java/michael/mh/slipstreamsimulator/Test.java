package michael.mh.slipstreamsimulator;

public class Test {
    public static void main (String args[]) {
        System.setProperty("org.graphstream.ui", "swing");
        Slipstream testSlipstream = new Slipstream();
        testSlipstream.displayGraph();
    }
}
