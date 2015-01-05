package GUI;

import java.io.IOException;
import javax.swing.JLabel;
import Logic.Cell;
import Logic.ChewyObject;
import Logic.Lokum;

@SuppressWarnings("serial")
public class CellButton extends JLabel{
		public int coordX;
		public int coordY;
		private ImageStorage is;
		
		public CellButton(Cell c, int i, int j){
			super(c.getCurrentObject().getType().substring(0, 2));
			coordX = i;
			coordY = j;
			ChewyObject co = c.getCurrentObject();
			
			try {
				is = ImageStorage.getInstance();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			if(co instanceof Lokum){
				setAsLokum((Lokum)co);
			}else{
				setAsNothing();
			}
			
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.CENTER);
            
            /*BufferedImage icon = null;
			try {
				icon = ImageIO.read(new File("example.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
            //setIcon(new ImageIcon(icon));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.CENTER);
            
            
		}
		
		public void setAsNothing(){
			setIcon(is.getIcon(0));
		}
		
		public void setAsLokum(Lokum lo){
			String type = lo.getType();
			String sType = lo.getSpecialType();
			
			
			if (sType.equalsIgnoreCase("regular")) {
				if (type.equalsIgnoreCase("red rose")) {
					setIcon(is.getIcon(1));
				} else if (type.equalsIgnoreCase("green pistachio")) {
					setIcon(is.getIcon(2));
				} else if (type.equalsIgnoreCase("white coconut")) {
					setIcon(is.getIcon(3));
				} else if (type.equalsIgnoreCase("brown hazelnut")) {
					setIcon(is.getIcon(4));
				}
			}else if(sType.equalsIgnoreCase("Horizontal Striped")){
				if (type.equalsIgnoreCase("red rose")) {
					setIcon(is.getIcon(5));
				} else if (type.equalsIgnoreCase("green pistachio")) {
					setIcon(is.getIcon(6));
				} else if (type.equalsIgnoreCase("white coconut")) {
					setIcon(is.getIcon(7));
				} else if (type.equalsIgnoreCase("brown hazelnut")) {
					setIcon(is.getIcon(8));
				}
			}else if(sType.equalsIgnoreCase("Vertical Striped")){
				if (type.equalsIgnoreCase("red rose")) {
					setIcon(is.getIcon(9));
				} else if (type.equalsIgnoreCase("green pistachio")) {
					setIcon(is.getIcon(10));
				} else if (type.equalsIgnoreCase("white coconut")) {
					setIcon(is.getIcon(11));
				} else if (type.equalsIgnoreCase("brown hazelnut")) {
					setIcon(is.getIcon(12));
				}
			}else if(sType.equalsIgnoreCase("Wrapped")){
				if (type.equalsIgnoreCase("red rose")) {
					setIcon(is.getIcon(13));
				} else if (type.equalsIgnoreCase("green pistachio")) {
					setIcon(is.getIcon(14));
				} else if (type.equalsIgnoreCase("white coconut")) {
					setIcon(is.getIcon(15));
				} else if (type.equalsIgnoreCase("brown hazelnut")) {
					setIcon(is.getIcon(16));
				}
			}else{
				setIcon(is.getIcon(17));
			}
			
		}
		
		public void setAsTimer(){
			setText("+5");
		}
		
		public void unsetAsTimer(){
			setText("");
		}
		
		
	}