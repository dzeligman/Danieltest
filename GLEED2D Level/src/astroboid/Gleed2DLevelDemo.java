package astroboid;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import pulpcore.Assets;
import pulpcore.image.Colors;
import pulpcore.image.CoreImage;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Sprite;
import astroboid.gleed2d.Gleed2DItem;
import astroboid.toolkit.misc.Vec2;
import astroboid.toolkit.misc.XmlUtil;

public class Gleed2DLevelDemo extends Scene2D {

	@Override
	public void load() {
		add(new FilledSprite(Colors.WHITE));

		try {
			Document doc = XmlUtil.readDocumentFromStream(Assets.getAsStream("Level1.xml"));
			NodeList nodeList = XmlUtil.selectNodeList(doc, "/Level/Layers/Layer[@Name='Layer1']/Items/*");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Gleed2DItem item = new Gleed2DItem(nodeList.item(i));
				Vec2 position = item.position();
				CoreImage image = CoreImage.load(item.textureFilename());
				Sprite sprite = new ImageSprite(image, (position.x), (position.y));
				sprite.setAnchor(0.5, 0.5);
				add(sprite);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
