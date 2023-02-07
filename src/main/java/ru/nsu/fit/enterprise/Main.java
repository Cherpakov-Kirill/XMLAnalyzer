package ru.nsu.fit.enterprise;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.enterprise.xml.XMLAnalyzer;

import java.io.File;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Application was started!");
        XMLAnalyzer xmlAnalyzer = new XMLAnalyzer(new File("C:\\Users\\cherp\\IdeaProjects\\RU-NVS.osm"));
        xmlAnalyzer.analyze();
        xmlAnalyzer.printInfo();
        log.info("Application was finished!");
    }
}