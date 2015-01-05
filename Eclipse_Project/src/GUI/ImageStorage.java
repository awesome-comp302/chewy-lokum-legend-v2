package GUI;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageStorage {
	private static ImageIcon iconList[] = new ImageIcon[17];
	private static ImageStorage instance;
	
	public static ImageStorage getInstance() throws IOException{
		if(instance == null){
			instance = new ImageStorage();
		}
		
		return instance;
	}
	
	private ImageStorage() throws IOException {
		loadImages();
	}
	
	private void loadImages() throws IOException{
		iconList[0] = new ImageIcon(ImageIO.read(new File("Nothing.png")));
		iconList[1] = new ImageIcon(ImageIO.read(new File("RedRose.png")));
		iconList[2] = new ImageIcon(ImageIO.read(new File("GreenPistachio.png")));
		iconList[3] = new ImageIcon(ImageIO.read(new File("WhiteCoconut.png")));
		iconList[4] = new ImageIcon(ImageIO.read(new File("BrownHazelnut.png")));
		iconList[5] = new ImageIcon(ImageIO.read(new File("RedRoseHor.png")));
		iconList[6] = new ImageIcon(ImageIO.read(new File("GreenPistachioHor.png")));
		iconList[7] = new ImageIcon(ImageIO.read(new File("WhiteCoconutHor.png")));
		iconList[8] = new ImageIcon(ImageIO.read(new File("BrownHazelnutHor.png")));
		iconList[9] = new ImageIcon(ImageIO.read(new File("RedRoseVer.png")));
		iconList[10] = new ImageIcon(ImageIO.read(new File("GreenPistachioVer.png")));
		iconList[11] = new ImageIcon(ImageIO.read(new File("WhiteCoconutVer.png")));
		iconList[12] = new ImageIcon(ImageIO.read(new File("BrownHazelnutVer.png")));
		iconList[13] = new ImageIcon(ImageIO.read(new File("RedRoseWrap.png")));
		iconList[14] = new ImageIcon(ImageIO.read(new File("GreenPistachioWrap.png")));
		iconList[15] = new ImageIcon(ImageIO.read(new File("WhiteCoconutWrap.png")));
		iconList[16] = new ImageIcon(ImageIO.read(new File("BrownHazelnutWrap.png")));
		iconList[17] = new ImageIcon(ImageIO.read(new File("ColorBomb.png")));
	}
	
	public ImageIcon getIcon(int id){
		return iconList[id];
	}
	
	
}
