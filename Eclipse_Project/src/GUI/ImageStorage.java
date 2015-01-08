package GUI;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageStorage {
	private static ImageIcon iconList[] = new ImageIcon[18];
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
		iconList[0] = new ImageIcon(ImageIO.read(new File("data\\icons\\Nothing.png")));
		iconList[1] = new ImageIcon(ImageIO.read(new File("data\\icons\\RedRose.png")));
		iconList[2] = new ImageIcon(ImageIO.read(new File("data\\icons\\GreenPistachio.png")));
		iconList[3] = new ImageIcon(ImageIO.read(new File("data\\icons\\WhiteCoconut.png")));
		iconList[4] = new ImageIcon(ImageIO.read(new File("data\\icons\\BrownHazelnut.png")));
		iconList[5] = new ImageIcon(ImageIO.read(new File("data\\icons\\RedRoseHor.png")));
		iconList[6] = new ImageIcon(ImageIO.read(new File("data\\icons\\GreenPistachioHor.png")));
		iconList[7] = new ImageIcon(ImageIO.read(new File("data\\icons\\WhiteCoconutHor.png")));
		iconList[8] = new ImageIcon(ImageIO.read(new File("data\\icons\\BrownHazelnutHor.png")));
		iconList[9] = new ImageIcon(ImageIO.read(new File("data\\icons\\RedRoseVer.png")));
		iconList[10] = new ImageIcon(ImageIO.read(new File("data\\icons\\GreenPistachioVer.png")));
		iconList[11] = new ImageIcon(ImageIO.read(new File("data\\icons\\WhiteCoconutVer.png")));
		iconList[12] = new ImageIcon(ImageIO.read(new File("data\\icons\\BrownHazelnutVer.png")));
		iconList[13] = new ImageIcon(ImageIO.read(new File("data\\icons\\RedRoseWrap.png")));
		iconList[14] = new ImageIcon(ImageIO.read(new File("data\\icons\\GreenPistachioWrap.png")));
		iconList[15] = new ImageIcon(ImageIO.read(new File("data\\icons\\WhiteCoconutWrap.png")));
		iconList[16] = new ImageIcon(ImageIO.read(new File("data\\icons\\BrownHazelnutWrap.png")));
		iconList[17] = new ImageIcon(ImageIO.read(new File("data\\icons\\ColorBomb.png")));
	}
	
	public ImageIcon getIcon(int id){
		return iconList[id];
	}
	
	
}
