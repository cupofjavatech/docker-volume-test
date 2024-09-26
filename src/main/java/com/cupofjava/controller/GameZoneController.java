package com.cupofjava.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cupofjava.model.Game;

@RestController
@RequestMapping("/games")
public class GameZoneController {

	private static Logger LOG = LoggerFactory.getLogger(GameZoneController.class);

	@Value("${game.file.path}")
	private String filePath;

	@Value("${game.file.name}")
	private String fileName;

	@GetMapping("/all")
	public List<Game> getAllGame() throws IOException {
		LOG.info("Get Game List");
		return getGamesAll();
	}

	@PostMapping("/add")
	public List<Game> addGame(@RequestBody Game game) throws IOException {
		LOG.info("Add a new Game");

		BufferedWriter writer = getBufferedWriter();
		writer.append(game.getId() + "," + game.getName());
		writer.newLine();
		writer.flush();
		writer.close();
		;
		return getGamesAll();
	}

	private BufferedWriter getBufferedWriter() {
		BufferedWriter writer = null;
		try {
			Path path = Paths.get(filePath + "/" + fileName);

			if (path.toFile().exists()) {
				writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
			} else {
				writer = Files.newBufferedWriter(Files.createFile(path), StandardOpenOption.APPEND);
			}

		} catch (IOException ex) {
			LOG.error("File Write Error", ex);
		}
		return writer;
	}

	private BufferedReader getBufferReader() {
		BufferedReader reader = null;
		try {
			Path path = Paths.get(filePath + "/" + fileName);
			if (Files.exists(path)) {
				reader = Files.newBufferedReader(path);
			}
		} catch (IOException ex) {
			LOG.error("File Read Error", ex);
		}
		return reader;
	}

	private List<Game> getGamesAll() throws IOException {
		BufferedReader reader = getBufferReader();
		List<Game> list = new ArrayList<>();

		if (reader != null) {
			reader.lines().forEach(line -> {
				String[] s = line.split(",");
				list.add(new Game(s[0], s[1]));
			});

			reader.close();
		}

		return list;
	}
}
