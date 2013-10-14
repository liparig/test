/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.toolkit.demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Lipari
 */
public class MostraEvent extends Observable implements ActionListener {
    private boolean mostra=true;
    public void actionPerformed(ActionEvent ae) {
        GraphModel graphModel=Lookup.getDefault().lookup(GraphController.class).getModel();
        for(Node node: graphModel.getGraphVisible().getNodes().toArray())
            node.getNodeData().getTextData().setVisible(mostra);
            mostra=!mostra;
         this.setChanged();
         this.notifyObservers();
    }
}