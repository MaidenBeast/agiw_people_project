package it.uniroma3.agiw.indexing;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MetaFilePreparer implements FilePreparer {

	@Override
	public Object prepare(String path) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
		return jsonObject;
	}

}
