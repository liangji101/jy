package com.renren.ntc.video.utils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmojiEncoder {
	enum Type {
		UNICODE, NAME
	};

	public static EmojiEncoder converter;

	private EmojiEncoder() {
	}

	public static EmojiEncoder getInstance() {
		if (null == converter) {
			converter = new EmojiEncoder();
		}
		return converter;
	}
	
	static int[] toCodePointArray(String str) {
		char[] ach = str.toCharArray();
		int len = ach.length;
		int[] acp = new int[Character.codePointCount(ach, 0, len)];
		int j = 0;

		for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
			cp = Character.codePointAt(ach, i);
			acp[j++] = cp;
		}
		return acp;
	}
//
//	private Map<List<Integer>, String> convertMap;
//
//	public static class Builder {
//
//		private Type from;
//		private Type to;
//
//		public Builder from(Type type) {
//			this.from = type;
//			return this;
//		}
//
//		public Builder to(Type type) {
//			this.to = type;
//			return this;
//		}
//
//		public EmojiEncoder build() {
//			EmojiEncoder converter = new EmojiEncoder();
//			readMap(converter);
//			return converter;
//		}
//
//		private static final String TRIM_PATTERN = "[^0-9A-F]*";
//
//		public void readMap(EmojiEncoder converter) {
//			Map<List<Integer>, String> result = new HashMap<List<Integer>, String>();
//			converter.convertMap = result;
//
//			XMLEventReader reader = null;
//			try {
//
//				XMLInputFactory factory = XMLInputFactory.newInstance();
//
//                InputStream stream = getClass().getResourceAsStream("/emoji4unicode.xml");
//				reader = factory.createXMLEventReader(stream);
//
//				while (reader.hasNext()) {
//					XMLEvent event = reader.nextEvent();
//
//					if (event.isStartElement()) {
//						StartElement element = (StartElement) event;
//						if (element.getName().getLocalPart().equals("e")) {
//
//							Attribute fromAttr = element
//									.getAttributeByName(new QName(from
//											.toString().toLowerCase()));
//							Attribute toAttr = element
//									.getAttributeByName(new QName(to.toString()
//											.toLowerCase()));
//							if (fromAttr == null) {
//								continue;
//							}
//							List<Integer> fromCodePoints = new ArrayList<Integer>();
//							String from = fromAttr.getValue();
//							if (from.length() > 6) {
//								String[] froms = from.split("\\+");
//								for (String part : froms) {
//									fromCodePoints.add(Integer.parseInt(part
//											.replaceAll(TRIM_PATTERN, ""), 16));
//								}
//							} else {
//								fromCodePoints.add(Integer.parseInt(from
//										.replaceAll(TRIM_PATTERN, ""), 16));
//							}
//							if (toAttr == null) {
//								result.put(fromCodePoints, "[*]");
//							} else {
//								String to = toAttr.getValue();
//								// if(null == to || "".equals(to.trim())){
//								// }
//								// StringBuilder toBuilder = new
//								// StringBuilder();
//								// if (to.length() > 6) {
//								// String[] tos = to.split("\\+");
//								// for (String part : tos) {
//								// toBuilder.append(Character
//								// .toChars(Integer.parseInt(part
//								// .replaceAll(
//								// TRIM_PATTERN,
//								// ""), 16)));
//								// }
//								// } else {
//								// toBuilder.append(Character.toChars(Integer
//								// .parseInt(to.replaceAll(
//								// TRIM_PATTERN, ""), 16)));
//								// }
//								// result.put(fromCodePoints,
//								// toBuilder.toString());
//								result.put(fromCodePoints, "[*]");
//							}
//						}
//					}
//				}
//
//				reader.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			} finally {
//				if (reader != null) {
//					try {
//						reader.close();
//					} catch (XMLStreamException e) {
//
//					}
//				}
//
//			}
//
//		}
//
//	}

	public String convert(String input) {
		   if (input == null) {
	            return "";
	        }
	        StringBuilder result = new StringBuilder();
	        int[] codePoints = toCodePointArray(input);
	        for (int i = 0; i < codePoints.length; i++) {
	            if ((codePoints[i] > 0x1f000) && (codePoints[i] < 0x1ffff)) {
	                result.append("[*]");
	                continue;
	            }
	            result.append(Character.toChars(codePoints[i]));
	        }
	        return result.toString();
	}

	
	
//	int[] toCodePointArray(String str) {
//		char[] ach = str.toCharArray();
//		int len = ach.length;
//		int[] acp = new int[Character.codePointCount(ach, 0, len)];
//		int j = 0;
//
//		for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
//			cp = Character.codePointAt(ach, i);
//			acp[j++] = cp;
//		}
//		return acp;
//	}

}
