package ru.nsu.fit.enterprise.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
public class XMLAnalyzer {

    private final File file;
    private final MapCounter userChanges;
    private final MapCounter keyUsing;

    public XMLAnalyzer(File file) {
        this.file = file;
        this.userChanges = new MapCounter();
        this.keyUsing = new MapCounter();
    }

    public void analyze() {
        try (InputStream inputStream = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(file)), true)) {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "node" -> {
                            Attribute userName = startElement.getAttributeByName(new QName("user"));
                            if (userName != null) {
                                userChanges.increase(userName.getValue());
                            }
                        }
                        case "tag" -> {
                            Attribute keyName = startElement.getAttributeByName(new QName("k"));
                            if (keyName != null) {
                                keyUsing.increase(keyName.getValue());
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printInfo() {
        List<Map.Entry<String, Long>> users = userChanges.getSortedReverseOrderFrequencies();
        List<Map.Entry<String, Long>> keys = keyUsing.getSortedDirectOrderFrequencies();

        log.info("Total users number = {}", users.size());
        users.forEach(user -> log.info("userName = '{}', counter = {}", user.getKey(), user.getValue()));
        log.info("Total keys number = {}", keys.size());
        keys.forEach(key -> log.info("keyName = '{}', counter = {}", key.getKey(), key.getValue()));
    }

}
