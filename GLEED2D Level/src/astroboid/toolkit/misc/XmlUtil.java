package astroboid.toolkit.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtil {
	static XPathFactory factory = XPathFactory.newInstance();

	static Map<String, XPathExpression> xpathExpressions = new LazyCache<String, XPathExpression>(64,
			new Functor1<XPathExpression, String>() {
				@Override
				public XPathExpression invoke(String query) {
					XPath xpath = factory.newXPath();
					XPathExpression expr;
					try {
						expr = xpath.compile(query);
					} catch (XPathExpressionException e) {
						throw new RuntimeException(e);
					}
					return expr;
				}
			});

	public static NodeList selectNodeList(Node doc, String query) throws XPathExpressionException {
		XPathExpression expr = xpathExpressions.get(query);
		return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	}

	public static Node selectNode(Node doc, String query) throws XPathExpressionException {
		XPathExpression expr = xpathExpressions.get(query);
		return (Node) expr.evaluate(doc, XPathConstants.NODE);
	}

	public static <T> T selectValue(Class<T> type, Node doc, String query) throws XPathExpressionException {
		XPathExpression expr = xpathExpressions.get(query);
		if (Number.class.equals(type)) {
			Double v = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
			if (v.isNaN())
				throw new XPathExpressionException("Query " + query + " result is not a number");
			return type.cast(v);
		} else if (String.class.equals(type)) {
			return type.cast(expr.evaluate(doc, XPathConstants.STRING));
		} else if (Boolean.class.equals(type)) {
			return type.cast(expr.evaluate(doc, XPathConstants.BOOLEAN));
		} else {
			throw new XPathExpressionException("Unkown data type " + type);
		}
	}

	public static Document readDocumentFromStream(InputStream stream) throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(stream);
		return doc;
	}
}
