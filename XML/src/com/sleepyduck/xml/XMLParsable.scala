package com.sleepyduck.xml

trait XMLParsable {
	val _XMLElementFactory: XMLElementFactory = new XMLElementFactory(this)

	def putAttributes(element: XMLElement)
	def toXMLElement(): XMLElement = _XMLElementFactory toXMLElement
	def toXMLString = _XMLElementFactory toXMLString
}