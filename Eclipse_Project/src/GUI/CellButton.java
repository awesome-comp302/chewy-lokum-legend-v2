package GUI;
import java.awt.Color;

import javax.swing.JButton;

import Logic.Cell;
import Logic.ChewyObject;
import Logic.Lokum;

@SuppressWarnings("serial")
public class CellButton extends JButton{
		public int coordX;
		public int coordY;
		public CellButton(Cell c, int i, int j){
			super(c.getCurrentObject().getType().substring(0, 2));
			coordX = i;
			coordY = j;
			ChewyObject co = c.getCurrentObject();
			String type = co.getType();
			
			if (co instanceof Lokum) {
				
				Lokum l = (Lokum)co;
				if (!l.getSpecialType().equalsIgnoreCase("regular")) {
					setText(l.getSpecialType().substring(0, 2));
				}
				if (type.equalsIgnoreCase("red rose")) {
					setBackground(Color.RED);
				}
				else if (type.equalsIgnoreCase("green pistachio")) {
					setBackground(Color.GREEN );
				} else if (type.equalsIgnoreCase("brown hazelnut")) {
					setBackground(Color.BLACK);
				} else if (type.equalsIgnoreCase("white coconut")) {
					setBackground(Color.WHITE);
				}
				
				
			}else{
				setBackground(Color.GRAY);
				setText("");
			}
			
			
			
		}
	}