package michael.mh.slipstreamsimulator;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.Random;

public class Slipstream {
    private Graph slipstreamGraph;
    
    public Slipstream() {
        initializeSlipstream();
    }
    
    public void displayGraph() {
        slipstreamGraph.display();
        slipstreamGraph.nodes().forEach((node) -> {
            System.out.println("Node Id: " + node.getId());
            System.out.println("Is Slippoint: " + node.getAttribute("isSlippoint"));
            if (node.getAttribute("isSlippoint", Boolean.class)) {
                StarSystem starSystem = node.getAttribute("starSystem", StarSystem.class);
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
        
        final int num1Final = num1;
        final int num2Final = num2;
        final int num3Final = num3;
        
        slipstreamGraph.nodes().forEach(node -> {
            int loopCount = node.getIndex();
            if (loopCount != num1Final && loopCount != num2Final && loopCount != num3Final) {
                node.setAttribute("isSlippoint", false);
            } else if (loopCount == num1Final) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", solSystem);
            } else if (loopCount == num2Final) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", alphaCSystem);
            } else if (loopCount == num3Final) {
                node.setAttribute("isSlippoint", true);
                node.setAttribute("starSystem", vegaSystem);
            }
        });
    }
    
    private void assignEdgeDistances() {
        Random rand = new Random();
        
        slipstreamGraph.edges().forEach(edge -> {
            int distance = rand.nextInt(4) + 1;
            edge.setAttribute("distance", distance);
        });
    }

}
