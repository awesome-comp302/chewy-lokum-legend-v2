package GUI;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import Logic.Cell;
import Logic.ChewyObject;
import Logic.Lokum;

@SuppressWarnings("serial")
public class CellButton extends JLabel{
		public int coordX;
		public int coordY;
		public CellButton(Cell c, int i, int j){
			super(c.getCurrentObject().getType().substring(0, 2));
			coordX = i;
			coordY = j;
			ChewyObject co = c.getCurrentObject();
			String type = co.getType();
			
            BufferedImage icon = null;
			try {
				icon = ImageIO.read(new File("example.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            setIcon(new ImageIcon(icon));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.CENTER);
			
			/*if (co instanceof Lokum) {
				
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
			}*/
			
			
			
		}
	}