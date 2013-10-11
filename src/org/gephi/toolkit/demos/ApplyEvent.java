package org.gephi.toolkit.demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JCheckBox;

import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.edge.EdgeWeightBuilder.EdgeWeightFilter;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;
import org.gephi.filters.plugin.graph.InDegreeRangeBuilder.InDegreeRangeFilter;
import org.gephi.filters.plugin.operator.INTERSECTIONBuilder.IntersectionOperator;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.openide.util.Lookup;

public class ApplyEvent extends Observable implements ActionListener {
	JCheckBox ckDegree;
	JCheckBox ckInDegree;
	JCheckBox ckEdge;
	RangeSlider deg;
	RangeSlider inDeg;
	RangeSlider edg;
	
	public ApplyEvent(JCheckBox ckDegree,RangeSlider deg,JCheckBox ckInDegree,RangeSlider inDeg,JCheckBox ckEdge,RangeSlider edg){
		this.ckDegree=ckDegree;
		this.ckInDegree=ckInDegree;
		this.ckEdge=ckEdge;
		this.deg=deg;
		this.edg=edg;
		this.inDeg=inDeg;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Query query1=null;
		Query query2=null;
		Query query3=null;
		//Get controllers and models
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
		if(graphModel==null)
			return;

		/**Filter, remove degree < 10*/
		if(ckDegree.isSelected()){
			DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
			degreeFilter.setRange(new Range(deg.getValue(),deg.getUpperValue()));     //Remove nodes with degree < 10
			query1 = filterController.createQuery(degreeFilter);
		
		}
		
		//Filter, remove InDegree < 10
		if(ckInDegree.isSelected()){
			InDegreeRangeFilter inDegreeFilter = new InDegreeRangeFilter();
			inDegreeFilter.setRange(new Range(inDeg.getValue(),inDeg.getUpperValue()));     //Remove nodes with Indegree < 10
			query2 = filterController.createQuery(inDegreeFilter);
				
		}
		//Filter, remove EdgeWeight < 10
		if(ckEdge.isSelected()){
			EdgeWeightFilter edgeFilter = new EdgeWeightFilter();
			edgeFilter.setRange(new Range((float)edg.getValue()/1000,(float)edg.getUpperValue()/1000));     //Remove nodes with edgeweight < 10
			query3 = filterController.createQuery(edgeFilter);		
		}
		
		/**Combine two filters with AND - Set query and query2 as sub-query of AND*/
		IntersectionOperator intersectionOperator = new IntersectionOperator();
		Query query4 = filterController.createQuery(intersectionOperator);
		if(ckDegree.isSelected())
			filterController.setSubQuery(query4, query1);
		if(ckInDegree.isSelected())
			filterController.setSubQuery(query4, query2);
		if(ckEdge.isSelected())
			filterController.setSubQuery(query4, query3);
		//Set the filter result as the visible view
		GraphView view;
		view=graphModel.getVisibleView();
		if(!view.isMainView())
		graphModel.destroyView(view);
		view=filterController.filter(query4);
		graphModel.setVisibleView(view);
		this.setChanged();
	    this.notifyObservers();
	}

}
