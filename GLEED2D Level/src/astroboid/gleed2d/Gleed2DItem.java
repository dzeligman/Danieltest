package astroboid.gleed2d;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pulpcore.image.Colors;
import astroboid.toolkit.misc.Vec2;
import astroboid.toolkit.misc.XmlUtil;

public class Gleed2DItem {
	private final Node node;

	public Gleed2DItem(Node node) throws XPathException {
		this.node = node.cloneNode(true);
	}

	private <T> T property(Class<T> type, String name) {
		try {
			return XmlUtil.selectValue(type, node, name);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public String name() {
		return property(String.class, "@Name");
	}

	public String type() {
		return node.getAttributes().getNamedItem("xsi:type").getTextContent();
	}

	public boolean visible() {
		return property(Boolean.class, "@Visible");
	}

	public Vec2 position() {
		float x = property(Number.class, "Position/X/text()").floatValue();
		float y = property(Number.class, "Position/Y/text()").floatValue();
		return new Vec2(x, y);
	}

	public Vec2 origin() {
		float x = property(Number.class, "Origin/X/text()").floatValue();
		float y = property(Number.class, "Origin/Y/text()").floatValue();
		return new Vec2(x, y);
	}

	public float rotation() {
		return property(Number.class, "Rotation/text()").floatValue();
	}

	public float height() {
		return property(Number.class, "Height/text()").floatValue();
	}

	public float width() {
		return property(Number.class, "Width/text()").floatValue();
	}

	public float radius() {
		return property(Number.class, "Radius/text()").floatValue();
	}

	public int fillColor() {
		int r = property(Number.class, "FillColor/R/text()").intValue();
		int g = property(Number.class, "FillColor/G/text()").intValue();
		int b = property(Number.class, "FillColor/B/text()").intValue();
		int a = property(Number.class, "FillColor/A/text()").intValue();
		return Colors.rgba(r, g, b, a);
	}

	public int tintColor() {
		int r = property(Number.class, "TintColor/R/text()").intValue();
		int g = property(Number.class, "TintColor/G/text()").intValue();
		int b = property(Number.class, "TintColor/B/text()").intValue();
		int a = property(Number.class, "TintColor/A/text()").intValue();
		return Colors.rgba(r, g, b, a);
	}

	public String textureFilename() {
		return property(String.class, "texture_filename/text()");
	}

	public List<String> customProperties() {
		try {
			List<String> result = new ArrayList<String>();
			NodeList customPropertyNodes = XmlUtil.selectNodeList(node, "CustomProperties/Property/@Name");
			for (int i = 0; i < customPropertyNodes.getLength(); i++) {
				Node localPointNode = customPropertyNodes.item(i);
				result.add(localPointNode.getNodeValue());
			}
			return result;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}

	}

	public String customProperty(String name, String notFoundValue) {
		return customProperty(String.class, name, notFoundValue);
	}

	public String customProperty(String name) {
		return customProperty(name, null);
	}

	public <T> T customProperty(Class<T> type, String name) {
		return customProperty(type, name, null);
	}

	public <T> T customProperty(Class<T> type, String name, T notFoundValue) {
		String query;
		if (Boolean.class.equals(type)) {
			query = "CustomProperties/Property[@Name='" + name + "']/boolean/text()";
		} else {
			query = "CustomProperties/Property[@Name='" + name + "']/string/text()";
		}
		try {
			//empty string means not found
			if ("".equals(XmlUtil.selectValue(String.class, node, query)))
				return notFoundValue;

			T value = null;
			if (Number.class.equals(type) || String.class.equals(type) || Boolean.class.equals(type)) {
				value = XmlUtil.selectValue(type, node, query);
			} else if (Vec2.class.equals(type)) {
				float x = XmlUtil.selectValue(Number.class, node,
						"CustomProperties/Property[@Name='" + name + "']/Vector2/X/text()").floatValue();
				float y = XmlUtil.selectValue(Number.class, node,
						"CustomProperties/Property[@Name='" + name + "']/Vector2/Y/text()").floatValue();
				value = type.cast(new Vec2(x, y));
			} else {
				throw new RuntimeException("Unkown data type " + type);
			}
			return value;
		} catch (XPathException e) {
			if (notFoundValue != null)
				return notFoundValue;
			throw new RuntimeException(e);
		}
	}
}