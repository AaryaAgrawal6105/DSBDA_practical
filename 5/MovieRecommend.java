package movie;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;


public class MovieRecommend {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

	;

		Job job = new Job(conf , "movie rating average");

		job.setJarByClass(MovieRecommend.class);
		
		job.setMapperClass(MovieMapper.class);
		job.setReducerClass(MovieReducer.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(FloatWritable.class);
		
				FileInputFormat.addInputPath(job, new Path(args[0]));
				FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}