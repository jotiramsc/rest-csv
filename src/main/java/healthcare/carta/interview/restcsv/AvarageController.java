package healthcare.carta.interview.restcsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class AvarageController {

	@PostMapping()
	public Response uploadFile(@RequestParam("data") MultipartFile file, @RequestParam("column") String columnName)
			throws IOException {
		{

			Response response = new Response();
			try {

				BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVParser csvParser = CSVParser.parse(reader, CSVFormat.EXCEL.withFirstRecordAsHeader());
				// Map<String, Integer> headerMap = csvParser.getHeaderMap();

				// Integer columnIndex = headerMap.get(columnName);
				List<CSVRecord> records = csvParser.getRecords();

				if (records != null) {
					Map<String, Integer> headers = csvParser.getHeaderMap();
					int index = headers.get(columnName);

					OptionalDouble average = records.stream().map(record -> Integer.parseInt(record.get(index)))
							.mapToInt(p -> p).average();

					if (average.isPresent()) {

						response.setData(average.getAsDouble()+"");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
			return response;

		}
	}

}
