package org.gephi.toolkit.demos;
import org.gephi.graph.api.Node;
import org.gephi.preview.api.Item;

/**
 * Basic item without properties but a node.
 */
public class LabelItem implements Item{
    protected final String type="click_label";
    protected final Node node;
    public LabelItem(Node node) {
        this.node = node;
    }
    
    @Override
    public Object getSource() {
        return node;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public <D> D getData(String key) {
        return null;
    }

    @Override
    public void setData(String key, Object value) {
    }

    @Override
    public String[] getKeys() {
        return new String[0];
    }
    
}
