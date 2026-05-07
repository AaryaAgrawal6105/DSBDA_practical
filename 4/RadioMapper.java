package musicradio;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class RadioMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() == 0 && value.toString().contains("UserId")) return;

		String[] fields = value.toString().split(",");
		if (fields.length < 5) return;

		String trackId = fields[1].trim();
		String radio = fields[3].trim();
		String skip = fields[4].trim();

		if (radio.equals("1")) {
			context.write(new Text(trackId), new Text("RADIO"));
		}
		if (skip.equals("1")) {
			context.write(new Text(trackId), new Text("SKIP"));
		}
	}
}