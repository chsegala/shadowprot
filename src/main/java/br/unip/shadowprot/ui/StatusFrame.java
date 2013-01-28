package br.unip.shadowprot.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.Point3d;

import org.springframework.stereotype.Component;

@Component
public class StatusFrame extends JFrame {
	private static final int LABEL_HEIGHT = 15;
	private static final int LABEL_WIDTH = 100;
	private static final int VALUE_WIDTH = 180;
	
	private static final long serialVersionUID = -7077871250645606269L;
	
	public static void main(String[] args) {
		StatusFrame sf = new StatusFrame();
		sf.setVisible(true);
	}

	private JLabel forearmValue;
	private JLabel armValue;
	private JLabel baseValue;
	private JLabel baseLabel;
	private JLabel handPointValue;

	public StatusFrame() {
		super("Ângulos");
		setPreferredSize(new Dimension(350, 170));
		getContentPane().setLayout(new FlowLayout());

		baseLabel = new JLabel("Ombro");
		baseLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		baseLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		getContentPane().add(baseLabel);

		baseValue = new JLabel();
		baseValue.setFont(new Font("Dialog", Font.BOLD, 16));
		baseValue.setPreferredSize(new Dimension(VALUE_WIDTH, LABEL_HEIGHT));
		getContentPane().add(baseValue);

		JLabel armLabel = new JLabel("Braço");
		armLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		armLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		getContentPane().add(armLabel);
		
		armValue = new JLabel();
		armValue.setFont(new Font("Dialog", Font.BOLD, 16));
		armValue.setPreferredSize(new Dimension(VALUE_WIDTH, LABEL_HEIGHT));
		getContentPane().add(armValue);
		
		JLabel forearmLabel = new JLabel("Antebraço");
		forearmLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		forearmLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		getContentPane().add(forearmLabel);
		
		forearmValue = new JLabel();
		forearmValue.setFont(new Font("Dialog", Font.BOLD, 16));
		forearmValue.setPreferredSize(new Dimension(VALUE_WIDTH, LABEL_HEIGHT));
		getContentPane().add(forearmValue);
		
		handPointValue = new JLabel();
		handPointValue.setFont(new Font("Dialog", Font.BOLD, 16));
		forearmValue.setPreferredSize(new Dimension(VALUE_WIDTH, LABEL_HEIGHT));
		getContentPane().add(handPointValue);
		
		pack();
	}
	
	public void setValues(byte[] values){
		baseValue.setText(Byte.valueOf(values[0]).toString());
		armValue.setText(Byte.valueOf(values[1]).toString());
		forearmValue.setText(Byte.valueOf(values[2]).toString());
	}

	public void setValues(double[] values) {
		baseValue.setText(Double.valueOf(values[0]).toString());
		armValue.setText(Double.valueOf(values[1]).toString());
		forearmValue.setText(Double.valueOf(values[2]).toString());
	}
	
	public void setHandValue(Point3d value){
		handPointValue.setText(String.format("x:%3.2f | y:%3.2f | z:%3.2f2", value.x, value.y, value.z));
	}
}
