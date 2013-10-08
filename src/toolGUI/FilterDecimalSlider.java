package toolGUI;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;

/**
 * application panel to display a range slider.
 */
public class FilterDecimalSlider extends JPanel {

    private JLabel rangeSliderLabel1 = new JLabel();
    private JLabel rangeSliderValue1 = new JLabel();
    private JLabel rangeSliderValue2 = new JLabel();
    private RangeSlider rangeSlider=new RangeSlider();
    private final JLabel lblMassimo = new JLabel();

    public FilterDecimalSlider(int min, int max) {
        setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{60, 45, 60, 35, 0};
        gridBagLayout.rowHeights = new int[]{30, 30, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        
        rangeSliderLabel1.setText("minimo:");
        
                GridBagConstraints gbc_rangeSliderLabel1 = new GridBagConstraints();
                gbc_rangeSliderLabel1.insets = new Insets(0, 0, 5, 5);
                gbc_rangeSliderLabel1.gridx = 0;
                gbc_rangeSliderLabel1.gridy = 0;
                add(rangeSliderLabel1, gbc_rangeSliderLabel1);
        rangeSliderValue1.setHorizontalAlignment(JLabel.CENTER);
        rangeSliderValue1.setText(""+min);
        GridBagConstraints gbc_rangeSliderValue1 = new GridBagConstraints();
        gbc_rangeSliderValue1.anchor = GridBagConstraints.WEST;
        gbc_rangeSliderValue1.insets = new Insets(0, 0, 5, 5);
        gbc_rangeSliderValue1.gridx = 1;
        gbc_rangeSliderValue1.gridy = 0;
        add(rangeSliderValue1, gbc_rangeSliderValue1);
        lblMassimo.setText("massimo:");
        GridBagConstraints gbc_lblMassimo = new GridBagConstraints();
        gbc_lblMassimo.insets = new Insets(0, 0, 5, 5);
        gbc_lblMassimo.gridx = 2;
        gbc_lblMassimo.gridy = 0;
        add(lblMassimo, gbc_lblMassimo);
        rangeSliderValue2.setHorizontalAlignment(JLabel.CENTER);
        rangeSliderValue2.setText(""+max/1000);
        GridBagConstraints gbc_rangeSliderValue2 = new GridBagConstraints();
        gbc_rangeSliderValue2.insets = new Insets(0, 0, 5, 0);
        gbc_rangeSliderValue2.gridx = 3;
        gbc_rangeSliderValue2.gridy = 0;
        add(rangeSliderValue2, gbc_rangeSliderValue2);
        rangeSlider.setUpperValue(max);
        rangeSlider.setPreferredSize(new Dimension(145, 35));
        rangeSlider.setPaintTicks(true);
        rangeSlider.setPaintLabels(true);
        rangeSlider.setSnapToTicks(true);
        rangeSlider.setBorder(null);
        rangeSlider.setMinimum(min);
        rangeSlider.setMaximum(max);
        rangeSlider.setValue(min);
        rangeSlider.setUpperValue(max);
        // Add listener to update display.
        rangeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                rangeSliderValue1.setText(""+(double)slider.getValue()/1000);
                rangeSliderValue2.setText(""+(double)slider.getUpperValue()/1000);
            }
        });
        
        GridBagConstraints gbc_rangeSlider = new GridBagConstraints();
        gbc_rangeSlider.fill = GridBagConstraints.HORIZONTAL;
        gbc_rangeSlider.insets = new Insets(0, 0, 0, 5);
        gbc_rangeSlider.gridwidth = 4;
        gbc_rangeSlider.gridx = 0;
        gbc_rangeSlider.gridy = 1;
        add(rangeSlider, gbc_rangeSlider);
    }
    public RangeSlider getRangeSlider(){
    	return rangeSlider;
    }

}