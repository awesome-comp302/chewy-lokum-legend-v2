package XML;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.Player;

public class WriteXMLFile {
	private static WriteXMLFile instance;

	private static Player player_;
	private static Board board;
	private static Level level;
	private static GamePlay gp;
	private static Element lokum[][];

	private WriteXMLFile() {

	}

	/**
	 * @return Instance of Class
	 */
	public static WriteXMLFile getInstance() {
		if (instance == null) {
			instance = new WriteXMLFile();
		}
		return instance;
	}

	/**
	 * Divides the given GamePlay into pieces as level, player, and board.
	 * 
	 * @param gamePlay
	 * @requires Given GamePlay is not null, the components of GamePlay (level,
	 *           board, player) is not null.
	 * 
	 */
	public void saveGame(GamePlay gamePlay) {
		gp = gamePlay;
		player_ = gamePlay.getPlayer();
		level = gp.getLevel();
		board = level.getBoard();
	}

	/**
	 * @return true, if the class is ready to write a GamePlay. Otherwise,
	 *         false.
	 */
	public boolean repOk() {
		if (board == null || gp == null || level == null || player_ == null)
			return false;

		return true;

	}

	/**
	 * 
	 * Writes GamePlay's data, which is given by saveGame method.
	 * 
	 * @requires repOk check, the program should be able to write files to its
	 *           directory.
	 * @param fileName
	 *            the file will be written with this label.
	 * @throws ParserConfigurationException
	 *             , TransformerException; if the data is corrupted or not in
	 *             proper format.
	 */
	public void write(String filename) {

		lokum = new Element[board.getWidth()][board.getHeight()];

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Game");
			doc.appendChild(rootElement);

			Element player = doc.createElement("Player");
			rootElement.appendChild(player);

			Element Board = doc.createElement("Board");
			rootElement.appendChild(Board);

			// set attribute to staff element
			// Attr attr = doc.createAttribute("id");
			// attr.setValue(ID);
			// player.setAttributeNode(attr);

			// shorten way
			Board.setAttribute("width", "" + board.getWidth());
			Board.setAttribute("height", "" + board.getHeight());

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode("" + player_.getID()));
			player.appendChild(id);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(player_.getName()));
			player.appendChild(name);

			Element lokums = doc.createElement("lokums");
			Board.appendChild(lokums);

			for (int i = 0; i < board.getWidth(); i++) {
				for (int j = 0; j < board.getHeight(); j++) {
					lokum[i][j] = doc.createElement("lokum");
					lokums.appendChild(lokum[i][j]);

					Element type = doc.createElement("type");
					type.appendChild(doc.createTextNode(board.cellAt(i, j)
							.getCurrentObject().getType()));
					lokum[i][j].appendChild(type);

					Element sType = doc.createElement("sType");
					sType.appendChild(doc.createTextNode(((Lokum) board.cellAt(
							i, j).getCurrentObject()).getSpecialType()));
					lokum[i][j].appendChild(sType);

					Element xPos = doc.createElement("xPos");
					xPos.appendChild(doc.createTextNode("" + i));
					lokum[i][j].appendChild(xPos);

					Element yPos = doc.createElement("yPos");
					yPos.appendChild(doc.createTextNode("" + j));
					lokum[i][j].appendChild(yPos);

					Element isTimed = doc.createElement("isTimed");
					
					int timed = 0;
					boolean temp = ((Lokum) board.cellAt(i, j).getCurrentObject()).isTimed();
					
					if(temp) timed = 1;
					
					isTimed.appendChild(doc.createTextNode(""+ timed));
					lokum[i][j].appendChild(isTimed);

				}

			}

			Element passingScore = doc.createElement("passingScore");
			passingScore.appendChild(doc.createTextNode(""
					+ level.getPassingScore()));
			rootElement.appendChild(passingScore);

			Element currentScore = doc.createElement("currentScore");
			currentScore.appendChild(doc.createTextNode("" + gp.getScore()));
			rootElement.appendChild(currentScore);

			Element remainingMoves = doc.createElement("remainingMoves");
			remainingMoves.appendChild(doc.createTextNode(""
					+ gp.getMovementsLeft()));
			rootElement.appendChild(remainingMoves);

			Element remainingSpecialMoves = doc.createElement("remainingSpecialMoves");
			remainingSpecialMoves.appendChild(doc.createTextNode(""
					+ gp.getSpecialMovementsLeft()));
			
			
			rootElement.appendChild(remainingSpecialMoves);

			Element remainingTime = doc.createElement("remainingTime");
			remainingTime.appendChild(doc.createTextNode("" + gp.getTimeLeft()));
			rootElement.appendChild(remainingTime);

			Element levelID = doc.createElement("levelID");
			levelID.setAttribute("lastUnlockedLevel","" + Level.getLastUnlockedLevel());
			levelID.appendChild(doc.createTextNode("" + level.getLevelId()));
			rootElement.appendChild(levelID);
			
			boolean modes[] = level.getLevelModes();
			
			int modeMap[] = {0, 0, 0, 0};
			
			if(modes[0]) modeMap[0] = 1;
			if(modes[1]) modeMap[1] = 1;
			if(modes[2]) modeMap[2] = 1;
			if(modes[3]) modeMap[3] = 1;
			
			
			Element levelModes = doc.createElement("levelModes");
			
			levelModes.setAttribute("SpLokums", "" + modeMap[0]);
			levelModes.setAttribute("Timer", "" + modeMap[1]);
			levelModes.setAttribute("TimerLokum", "" + modeMap[2]);
			levelModes.setAttribute("SpMove", "" + modeMap[3]);
			
			rootElement.appendChild(levelModes);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));

			// Output to console for testing

			transformer.transform(source, result);


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}