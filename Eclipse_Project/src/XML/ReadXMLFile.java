package XML;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.Player;

import java.io.File;
 
public class ReadXMLFile {
	
	private static ReadXMLFile instance;
	private static GamePlay gp;
	
	private ReadXMLFile(){
		
	}
	
	public static ReadXMLFile getInstance(){
		if (instance == null) {
			instance = new ReadXMLFile();
		}
		return instance;
	}
	
	public GamePlay loadGame(){
		return gp;
	}
 
  public void read() {
 
    try {
 
	File fXmlFile = new File("file.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
 
	NodeList player = doc.getElementsByTagName("Player");
	NodeList board = doc.getElementsByTagName("Board");
	
	
	Node passingScore = doc.getElementsByTagName("passingScore").item(0);
	Node currentScore = doc.getElementsByTagName("currentScore").item(0);
	Node remainingMoves = doc.getElementsByTagName("remainingMoves").item(0);
	Node level = doc.getElementsByTagName("level").item(0);
	
	int passingScoreState = Integer.parseInt(passingScore.getTextContent());
	int currentScoreState = Integer.parseInt(currentScore.getTextContent());
	int remainingMovesState = Integer.parseInt(remainingMoves.getTextContent());
	int levelState = Integer.parseInt(level.getTextContent());
	
 

	
	Element el = (Element) player.item(0);
	
	
	String name = getTextValue(el,"name");
	int id = getIntValue(el,"id");

	
	NodeList lokums = (NodeList) board.item(0);
	NodeList lokum = (NodeList) lokums.item(0);
	
	String width = ((Node) lokums).getAttributes().getNamedItem("width").getNodeValue();
	String height = ((Node) lokums).getAttributes().getNamedItem("height").getNodeValue();
	
	Board boardState = new Board(Integer.parseInt(width), Integer.parseInt(height));
	Level levelTest = new Level(passingScoreState, remainingMovesState, boardState, levelState);
	Player playerTest = new Player(name);
	playerTest.setID(id);
	gp = new GamePlay(levelTest);
	gp.setPlayer(playerTest);
	gp.setScore(currentScoreState);
	
	for (int i = 0; i < lokum.getLength(); i++) {
		String type = getTextValue((Element) lokum.item(i), "type");
		String sType = getTextValue((Element) lokum.item(i), "sType");
		int x = Integer.parseInt(getTextValue((Element) lokum.item(i), "xPos"));
		int y = Integer.parseInt(getTextValue((Element) lokum.item(i), "yPos"));
		boardState.fillCellAt(x, y, new Lokum(type, sType));
	}
	
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
  
  private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
  
  private static int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
 
}