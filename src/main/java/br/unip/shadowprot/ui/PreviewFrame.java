package br.unip.shadowprot.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreviewFrame extends JFrame {
	private static final long serialVersionUID = -2730681529494855041L;
	
	private UserTrackerWindowComponent viewer;
	
	@Autowired
	public PreviewFrame(UserTrackerWindowComponent viewer) {
		
		this.viewer = viewer;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.add("Center", viewer);
		this.pack();
		this.setVisible(true);
	}
	
	public UserTrackerWindowComponent getViewer() {
		return viewer;
	}

}