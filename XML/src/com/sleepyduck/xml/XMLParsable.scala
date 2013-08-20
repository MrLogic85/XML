package com.sleepyduck.xml

import com.bombardier.xml.XMLElementFactory

trait XMLParsable {
	val _XMLElementFactory: XMLElementFactory = new XMLElementFactory(this)

	def putAttributes(element: XMLElement)
	def toXMLElement(): XMLElement = _XMLElementFactory toXMLElement
	def toXMLString = _XMLElementFactory toXMLString
}