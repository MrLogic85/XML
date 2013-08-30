package com.sleepyduck.xml

import scala.collection.mutable.ArrayBuffer

case class XMLElement(
	var name: String = "",
	var data: String = "",
	val children: ArrayBuffer[XMLElement] = ArrayBuffer[XMLElement](),
	var parent: XMLElement = null,
	val attributes: ArrayBuffer[Attribute] = ArrayBuffer[Attribute]()) {

	def addAllChildren(elems: List[XMLElement]) = elems foreach (this addChild _)
	def addAttribute(name: String, value: String) = attributes += new Attribute(name, value)
	def getAttribute(name: String) = (attributes filter (_.name == name) headOption) getOrElse null

	def addChild(el: XMLElement) = {
		children += el
		el.parent = this
	}

	def getElement(name: String): XMLElement = {
		if (this.name == name) this
		else (children map (_.getElement(name)) filter (_ != null) headOption) getOrElse null
	}

	def getElement(elementName: String, attributeName: String, attributeValue: String): XMLElement = {
		if (name == elementName
			&& getAttribute(attributeName) != null
			&& getAttribute(attributeName).value == attributeValue) this
		else (children map (_.getElement(elementName, attributeName, attributeValue)) filter (_ != null) headOption) getOrElse null
	}

	override def toString = toString("")
	def toString(ident: String): String = {
		var str = ident + "<" + name + "";
		attributes foreach (attr => str += " " + attr.name + "=\"" + attr.value + "\"")
		if (data.length == 0 && children.length == 0)
			str += "/>"
		else {
			str += ">" + data + (if (children.length > 0) "\n" else "")
			children foreach (str += _.toString(ident + "\t") + "\n")
			str += (if (children.length > 0) ident else "") + "</" + name + ">"
		}
		str
	}
}

case class Attribute(var name: String, var value: String)