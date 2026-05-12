package music;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;


public class MusicAnalytics {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		

		Job job = new Job(conf , "music job");
		job.setJarByClass(MusicAnalytics.class);

		
		job.setMapperClass(MusicMapper.class);
		job.setReducerClass(MusicReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}