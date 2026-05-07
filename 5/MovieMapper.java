package movie;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class MovieMapper extends Mapper<LongWritable, Text, IntWritable, FloatWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() == 0 && value.toString().contains("userId")) return;

		String[] fields = value.toString().split(",");
		if (fields.length < 3) return;

		try {
			int movieId = Integer.parseInt(fields[1].trim());
			float rating = Float.parseFloat(fields[2].trim());

			context.write(new IntWritable(movieId), new FloatWritable(rating));
		} catch (NumberFormatException e) {
			// skip malformed line
		}
	}
}