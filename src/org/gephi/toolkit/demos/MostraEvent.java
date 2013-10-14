/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.toolkit.demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.openide.util.Lookup;

/**
 *
 * @author Lipari
 */
public class MostraEvent extends Observable implements ActionListener {

    public void actionPerformed(ActionEvent ae) {
        PreviewController previewController=Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel previewModel=previewController.getModel();
        if(previewModel.getProperties().getValue(PreviewProperty.SHOW_NODE_LABELS)== Boolean.TRUE)
             previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS,false);
        else
             previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS,true);
       this.setChanged();
       this.notifyObservers();
    }
    
}
