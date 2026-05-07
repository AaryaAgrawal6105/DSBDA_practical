package movie;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class MovieReducer extends Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable> {

	@Override
	public void reduce(IntWritable key, Iterable<FloatWritable> values, Context context)
			throws IOException, InterruptedException {
		float sum = 0;
		int count = 0;

		for (FloatWritable val : values) {
			sum += val.get();
			count++;
		}

		if (count > 0) {
			float avg = sum / count;
			context.write(key, new FloatWritable(avg));
		}
	}
}