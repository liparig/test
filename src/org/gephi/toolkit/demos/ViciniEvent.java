/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.toolkit.demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Lipari
 */
public class ViciniEvent extends Observable implements ActionListener{
   GraphView mainView=null;
   GraphView view=null;
        
    public void actionPerformed(ActionEvent ae) { 
        GraphModel graphModel=Lookup.getDefault().lookup(GraphController.class).getModel();
        if(!graphModel.getVisibleView().isMainView()){
            graphModel.destroyView(view);
            graphModel.setVisibleView(mainView);
        }
        else
            mainView=graphModel.getVisibleView();
          
        view = graphModel.newView();     //Duplicate main view
        DirectedGraph subGraph = graphModel.getDirectedGraph(view);
        float g=0.6f;
        JFrame frame=new JFrame();
        boolean one=false;
        for(Node node: subGraph.getGraphModel().getDirectedGraphVisible().getNodes().toArray()){
            if(node.getNodeData().getTextData().isVisible()){
                    one=true;
                    node.getNodeData().setG(1f);
                    for(Node vicino: graphModel.getDirectedGraph().getNeighbors(node).toArray()){
                        vicino.getNodeData().setG(1f);}
                }
        }
        for(Node nodes:subGraph.getGraphModel().getDirectedGraph().getNodes().toArray()){
                if(nodes.getNodeData().g()!=1f){
                    subGraph.clearEdges(nodes);
                    subGraph.removeNode(nodes);
                }
                else
                    nodes.getNodeData().setG(g);
        }
        if(!one)
             JOptionPane.showMessageDialog(frame,"Seleziona almeno un Nodo");
        else{
            graphModel.setVisibleView(view);
            this.setChanged();
            this.notifyObservers();
        }
    }
}
 
