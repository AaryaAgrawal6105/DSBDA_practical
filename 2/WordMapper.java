package wordcount;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] words = line.split("\\s+");
		for (String word : words) {
			Text outputKey = new Text(word.toUpperCase().trim());
			IntWritable outputValue = new IntWritable(1);
			context.write(outputKey, outputValue);
		}
	}
}