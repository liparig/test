package org.gephi.toolkit.demos;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Node;
import org.gephi.preview.api.PreviewMouseEvent;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.spi.PreviewMouseListener;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = PreviewMouseListener.class)
public class MouseListenerLabel implements PreviewMouseListener {

    @Override
    public void mouseClicked(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
     
    }


    @Override
    public void mousePressed(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
            boolean find=false;
           for (Node node : Lookup.getDefault().lookup(GraphController.class).getModel(workspace).getGraphVisible().getNodes()) {
            if (clickingInNode(node, event)) {
                  node.getNodeData().getTextData().setText(node.getNodeData().getLabel());
                    if(node.getNodeData().getTextData().isVisible())
                        node.getNodeData().getTextData().setVisible(false);
                   else
                       node.getNodeData().getTextData().setVisible(true);
                find=true;
                System.out.println("NodoCliccato:"+node.getNodeData().getLabel());
                properties.putValue("display-label.node.id", node.getNodeData().getId());                       
            }
        }
        if(!find)
            properties.removeSimpleValue("display-label.node.id");
    }
    @Override
    public void mouseDragged(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
    }

    @Override
    public void mouseReleased(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
    }

    private boolean clickingInNode(Node node, PreviewMouseEvent event) {
            int nx=(int)node.getNodeData().x();
            int ny=(int)node.getNodeData().y();
            int nr=(int)node.getNodeData().getRadius();
            int ex=event.x;
            int ey=-event.y;
            boolean inx = ex<=nx+nr&&ex>=nx-nr;
            boolean iny = ey<=ny+nr&&ey>=ny-nr;
        return (inx&&iny); 
    }
}
