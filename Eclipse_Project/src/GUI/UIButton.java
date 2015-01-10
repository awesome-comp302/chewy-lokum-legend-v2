package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;



@SuppressWarnings("serial")
public class UIButton extends JLabel {
	private ImageIcon icon;

	public UIButton(String buttonType, String text, Color bgColor){
		super(text);
		
		
		setBackground(bgColor);
		try {
			if (buttonType.equals("menu"))
				icon = ImageStorage.getInstance().getIcon(19);
			else if (buttonType.equals("game")){
				icon = ImageStorage.getInstance().getIcon(20);
				setForeground(Color.white);
			}
			else if (buttonType.equals("logo"))
				icon = ImageStorage.getInstance().getIcon(18);

		} catch (Exception e) {
		}
		setIcon(icon);

		setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);
	}

}
