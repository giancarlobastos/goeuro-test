package com.goeuro.test.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Service responsible to generate CSV files.
 * 
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 */
@Slf4j
@Service
public class CsvService {

	/**
	 * Write the CSV representation of the objects to the given stream.
	 * 
	 * @param stream The stream where the CSV will be written.
	 * @param objects The objects to be represented.
	 * @param clazz The class of the objects.
	 */
	public <T> void writeCsv(OutputStream stream, List<T> objects, Class<T> clazz) {
		CsvMapper mapper = new CsvMapper();
		mapper.getFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

		CsvSchema schema = mapper.schemaFor(clazz);
		BufferedOutputStream buffer = new BufferedOutputStream(stream);
		ObjectWriter writer = mapper.writer(schema);

		try {
			writer.writeValue(buffer, objects);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
