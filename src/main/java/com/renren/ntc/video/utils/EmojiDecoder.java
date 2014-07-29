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
import java.util.HashMap;
import java.util.Map;

public class EmojiDecoder {
	enum Type {
		UNICODE,NAME
	};
	public static EmojiDecoder converter;
	
	private EmojiDecoder() {
	}
	
	public static EmojiDecoder getInstance() {
		if (null == converter) {
			converter = new EmojiDecoder.Builder().from(Type.NAME).to(
					Type.UNICODE).build();
		}
		return converter;
	}
	

	private Map<String, String> convertMap;

	public static class Builder {

		private Type from;
		private Type to;

		public Builder from(Type type) {
			this.from = type;
			return this;
		}

		public Builder to(Type type) {
			this.to = type;
			return this;
		}

		public EmojiDecoder build() {
			EmojiDecoder converter = new EmojiDecoder();
			readMap(converter);
			return converter;
		}

		private static final String TRIM_PATTERN = "[^0-9A-F]*";

		public void readMap(EmojiDecoder converter) {
			Map<String, String> result = new HashMap<String, String>();
			converter.convertMap = result;

			XMLEventReader reader = null;
			try {

				XMLInputFactory factory = XMLInputFactory.newInstance();

				InputStream stream = getClass().getResourceAsStream("/emoji4unicode.xml");
				reader = factory.createXMLEventReader(stream);

				while (reader.hasNext()) {
					XMLEvent event = reader.nextEvent();

					if (event.isStartElement()) {
						StartElement element = (StartElement) event;
						if (element.getName().getLocalPart().equals("e")) {

							Attribute fromAttr = element
									.getAttributeByName(new QName(from
											.toString().toLowerCase()));
							Attribute toAttr = element
									.getAttributeByName(new QName(to.toString()
											.toLowerCase()));
							if (fromAttr == null) {
								continue;
							}
							String from = fromAttr.getValue();
							if (toAttr == null) {
								result.put(from, "");
							} else {
								String to = toAttr.getValue();
								if(null == to || "".equals(to.trim())){
								}
								StringBuilder toBuilder = new StringBuilder();
								if (to.length() > 6) {
									String[] tos = to.split("\\+");
									for (String part : tos) {
										toBuilder.append(Character
												.toChars(Integer.parseInt(part
														.replaceAll(
																TRIM_PATTERN,
																""), 16)));
									}
								} else {
									toBuilder.append(Character.toChars(Integer
											.parseInt(to.replaceAll(
													TRIM_PATTERN, ""), 16)));
								}
								result.put(from, toBuilder.toString());
							}
						}
					}
				}

				reader.close();
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (XMLStreamException e) {

					}
				}

			}

		}

	}

	
	public String convert(String input) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < input.length(); i++){
			 StringBuffer key2 = new StringBuffer();
			 char ch = input.charAt(i);
			 if ('[' == ch) {
				for (int j = i; j < input.length(); j++) {
					char chr = input.charAt(j);
					key2.append(chr);
					if (']' == chr) {
						String k = key2.toString();
						if (convertMap.containsKey(k)) {
							String value = convertMap.get(k);
							if (value != null) {
								result.append(value);
							}
							ch = 0;
							i = j + 1;
							break;
						}
					}
				}
			}
			result.append(ch);
		}
		return result.toString();
	}

}
