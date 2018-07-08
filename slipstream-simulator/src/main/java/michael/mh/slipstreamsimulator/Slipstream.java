package michael.mh.slipstreamsimulator;

import java.util.Collection;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.Random;
import org.graphstream.graph.Node;

public class Slipstream {
    private Graph slipstreamGraph;
    
    public Slipstream() {
        initializeSlipstream();
    }
    
    public void displayGraph() {
        slipstreamGraph.display();
        slipstreamGraph.getNodeSet().stream().forEach((node) -> {
            System.out.println("Node Id: " + node.getId());
            System.out.println("Is Slippoint: " + node.getAttribute("isSlippoint"));
            if (node.getAttribute("isSlippoint")) {
                StarSystem starSystem = node.getAttribute("starSystem");
                System.out.println("Star System: " + starSystem.getName());
            }
        });
    }
    
    private void initializeSlipstream() {
        initGraph();
        assignEdgeDistances();
        initDecisionPoints();
    }
    
    private void initGraph() {
        slipstreamGraph = new SingleGraph("Slipstream");
        Generator gen = new RandomGenerator(3);
        
        gen.addSink(slipstreamGraph);
        gen.begin();
        
        for (int i=0; i<50; i++) {
            gen.nextEvents();
        }
        
        gen.end();
    }
    
    private void initDecisionPoints() {
        StarSystem solSystem = new StarSystem("Sol");
        StarSystem alphaCSystem = new StarSystem("Alpha Centauri");
        StarSystem vegaSystem = new StarSystem("Vega");
        
        Random rand = new Random();
        
        int num1 = rand.nextInt(49) + 1;
        int num2 = 0;
        int num3 = 0;
        
        while(num2 < 1 || num2 == num1) {
            num2 = rand.nextInt(49) + 1;
        }
        
        while(num3 < 1 || num3 == num1 || num3 == num2) {
            num3 = rand.nextInt(49) + 1;
        }
        
        int loopCount = 0;
        
        for (Node node : slipstreamGraph.getEachNode()) {
            loopCount++;
            if (loopCount != num1 && loopCount != num2 && loopCount != num3) {
                node.setAttribute("isSlippoint", false);
            } else if (loopCount == num1) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", solSystem);
            } else if (loopCount == num2) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", alphaCSystem);
            } else if (loopCount == num3) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", vegaSystem);
            }
        }
    }
    
    private void assignEdgeDistances() {
        Random rand = new Random();
        
        for (Edge edge : slipstreamGraph.getEachEdge()) {
            int distance = rand.nextInt(4) + 1;
            edge.setAttribute("distance", distance);
        }
    }

}
