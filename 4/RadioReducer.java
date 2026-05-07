package musicradio;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class RadioReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		int radioCount = 0;
		int skipCount = 0;

		for (Text val : values) {
			String v = val.toString();
			if (v.equals("RADIO")) {
				radioCount++;
			} else if (v.equals("SKIP")) {
				skipCount++;
			}
		}

		context.write(key, new Text("RadioCount: " + radioCount + ", SkipCount: " + skipCount));
	}
}