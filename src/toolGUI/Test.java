package toolGUI;

import org.gephi.project.api.ProjectController;
import org.openide.util.Lookup;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Init a project - and therefore a workspace
		DesktopGUI desktop=new DesktopGUI();
		desktop.pack();
		desktop.setVisible(true);
	}

}
