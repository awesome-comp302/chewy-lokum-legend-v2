package XML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.Player;

import java.io.File;
import java.io.IOException;

public class ReadXMLFile {

	private static ReadXMLFile instance;
	private static GamePlay gp;

	private ReadXMLFile() {

	}

	/**
	 * @return Instance of Class
	 */
	public static ReadXMLFile getInstance() {
		if (instance == null) {
			instance = new ReadXMLFile();
		}
		return instance;
	}

	/**
	 * @requires A file should have been read from disk wit read() method before
	 *           using this method.
	 * @return the Gameplay which was read from a file
	 */
	public GamePlay loadGame() {
		return gp;
	}

	/**
	 * 
	 * Reads saved GamePlay's data from a file, and constructs a new gameplay.
	 * 
	 * @requires File with the given name should be existed and should be in
	 *           proper XML format.
	 * @param fileName
	 *            the file, with this label, will be read.
	 * @throws ParserConfigurationException
	 *             , IOException, SAXException; if the file is missing,
	 *             corrupted, or not in proper format.
	 */
	public void read() {

		File fXmlFile = new File("file.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :"
				+ doc.getDocumentElement().getNodeName());

		NodeList player = doc.getElementsByTagName("Player");
		NodeList board = doc.getElementsByTagName("Board");
		NodeList level = doc.getElementsByTagName("Level");

		Node passingScore = doc.getElementsByTagName("passingScore").item(0);
		Node currentScore = doc.getElementsByTagName("currentScore").item(0);
		Node remainingMoves = doc.getElementsByTagName("remainingMoves")
				.item(0);
		Node remainingSpecialMoves = doc.getElementsByTagName(
				"remainingSpecialMoves").item(0);
		Node remainingTime = doc.getElementsByTagName("remainingTime").item(0);
		// Node levelModes = doc.getElementsByTagName("levelModes").item(0);
		// Node levelVals = doc.getElementsByTagName("levelVars").item(0);
		Node levelID = doc.getElementsByTagName("level").item(0);
		// Node level = doc.getElementsByTagName("level").item(0);

		int passingScoreState = Integer.parseInt(passingScore.getTextContent());
		int currentScoreState = Integer.parseInt(currentScore.getTextContent());
		int remainingMovesState = Integer.parseInt(remainingMoves
				.getTextContent());
		int levelIdState = Integer.parseInt(levelID.getTextContent());
		int spLeftState = Integer.parseInt(remainingSpecialMoves
				.getTextContent());
		int remainingTimeState = Integer.parseInt(remainingTime
				.getTextContent());
		// int levelState = Integer.parseInt(level.getTextContent());

		Element el = (Element) player.item(0);

		int id = getIntValue(el, "id");

		NodeList lokums = (NodeList) board.item(0);
		NodeList lokum = (NodeList) lokums.item(0);

		String width = ((Node) lokums).getAttributes().getNamedItem("width")
				.getNodeValue();
		String height = ((Node) lokums).getAttributes().getNamedItem("height")
				.getNodeValue();

		Board boardState = new Board(Integer.parseInt(width),
				Integer.parseInt(height));
		Level levelTest = new Level(passingScoreState, remainingMovesState,
				boardState, levelIdState);
		Player playerTest = new Player();
		playerTest.setID(id);
		gp = new GamePlay(levelTest);
		gp.setPlayer(playerTest);
		gp.setScore(currentScoreState);
		gp.setSpecialMovementsLeft(spLeftState);
		levelTest.setTime(remainingTimeState);

		for (int i = 0; i < lokum.getLength(); i++) {
			String type = getTextValue((Element) lokum.item(i), "type");
			String sType = getTextValue((Element) lokum.item(i), "sType");
			int isTimed = Integer.parseInt(getTextValue(
					(Element) lokum.item(i), "isTimed"));
			int x = Integer.parseInt(getTextValue((Element) lokum.item(i),
					"xPos"));
			int y = Integer.parseInt(getTextValue((Element) lokum.item(i),
					"yPos"));
			boardState.fillCellAt(x, y, new Lokum(isTimed, type, sType));
		}

	}

	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private static int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele, tagName));
	}

}