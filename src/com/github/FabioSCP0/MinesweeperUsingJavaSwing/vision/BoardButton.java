package com.github.FabioSCP0.MinesweeperUsingJavaSwing.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.EventField;
import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.Field;
import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.ObserverField;

@SuppressWarnings("serial")
public class BoardButton extends JButton implements ObserverField, MouseListener {
	
	private final Color BG_STANDARD = new Color(184,184,184);
	private final Color BG_MARKED = new Color(8,179,247);
	private final Color BG_EXPLOSION = new Color(189,66,68);
	private final Color GREEN_TEXT = new Color(0,100,0);
	
	private Field field;
	
	public BoardButton(Field field) {
		this.field = field;
		setBackground(BG_STANDARD);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		
		field.registerObserver(this);
	}

	@Override
	public void eventHappened(Field field, EventField event) {
		switch(event) {
			case OPENED:
				openedStyle();
				break;
			case EXPLOSION:
				explosionStyle();
				break;
			case MARKED:
				markedStyle();
				break;
			case UNMARKED:
				unmarkedStyle();
				break;
			default:
				defaultStyle();
		}
		
		SwingUtilities.invokeLater(()->{
			repaint();
			validate();
		});
	}

	private void openedStyle() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(field.isMined()) {
			setBackground(BG_EXPLOSION);
			return;
		}
		setBackground(BG_STANDARD);
		
		switch (field.mineInNeighborhood()) {
		case 1: 
			setForeground(GREEN_TEXT);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5: 
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		String value = !field.safetedNeighborhood() ? field.mineInNeighborhood() + "" : "";
		setText(value);
 	}

	private void explosionStyle() {
		setBackground(BG_EXPLOSION);
		setForeground(Color.WHITE);
		setText("X");	
	}

	private void markedStyle() {
		setBackground(BG_MARKED);
		setForeground(Color.BLACK);
		setText("M");		
	}

	private void unmarkedStyle() {
		setBackground(BG_STANDARD);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");		
	}
	
	private void defaultStyle() {
		setBackground(BG_STANDARD);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");	
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			field.open();
		} else field.toggleMarcked();
		
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
