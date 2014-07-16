package pl.grushenko.okapi.geokrety;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pl.grushenko.okapi.geokrety.Geokret.GeokretState;
import pl.grushenko.okapi.geokrety.Geokret.GeokretType;
import pl.grushenko.okapi.util.Location;

public class GeokretyParser {
	public static Geokret parseGeokret(String xml) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbf.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("geokrety");
		Element kret = (Element)  nList.item(0).getFirstChild();
		
		return new Geokret(Integer.parseInt(kret.getAttribute("id")), kret.getTextContent(), Integer.parseInt(kret.getAttribute("dist")), new Location(Float.parseFloat(kret.getAttribute("lat")), Float.parseFloat(kret.getAttribute("lon"))), kret.getAttribute("waypoint"),Integer.parseInt(kret.getAttribute("owner_id")), GeokretState.values()[Integer.parseInt(kret.getAttribute("state"))], GeokretType.values()[Integer.parseInt(kret.getAttribute("type"))], kret.getAttribute("image"));
	}
}
