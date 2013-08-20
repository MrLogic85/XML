package com.bombardier.xml;

import java.io.StringReader

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler

import com.sleepyduck.xml.XMLElement
import com.sleepyduck.xml.XMLParsable

import javax.xml.parsers.SAXParserFactory

class XMLElementFactory(registeredXMLObject: XMLParsable) {

	def toXMLElement = {
		val element = new XMLElement(registeredXMLObject getClass () getSimpleName ())
		registeredXMLObject putAttributes element
		element
	}

	def toXMLString = toXMLElement toString
}

object XMLElementFactory {
	def BuildFromXMLString(xmlString: String) = {
		val wrapperElement = new XMLElement();
		val parser = SAXParserFactory.newInstance().newSAXParser();
		val reader = new StringReader(xmlString);

		parser.parse(new InputSource(reader), new DefaultHandler() {
			var currentElement = wrapperElement;

			override def characters(ch: Array[Char], start: Int, length: Int) = {
				def isNotWhitespace(ch: Char) = !(Character isWhitespace ch)
				val offset = ch.indexWhere(isNotWhitespace, start)
				currentElement.data = String.valueOf(ch, start + offset, length - offset)
			}

			override def endElement(uri: String, localName: String, qName: String) = currentElement = currentElement.parent

			override def startElement(uri: String, localName: String, qName: String, attributes: Attributes) = {
				val newEl = new XMLElement(qName)
				currentElement addChild newEl
				for (i <- 0 until (attributes getLength)) newEl addAttribute (attributes getLocalName i, attributes getValue i)
				currentElement = newEl
			}
		})
		wrapperElement.children
	}
}