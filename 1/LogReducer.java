package logfile;

import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class LogReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private int maxTime = Integer.MIN_VALUE;
    private List<String> maxUsers = new ArrayList<String>();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int totalTime = 0;

        for (IntWritable value : values) {
            totalTime += value.get();
        }

        if (totalTime > maxTime) {
            maxTime = totalTime;
            maxUsers.clear();
            maxUsers.add(key.toString());
        } else if (totalTime == maxTime) {
            maxUsers.add(key.toString());
        }
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {

        for (String user : maxUsers) {
            context.write(new Text(user), new IntWritable(maxTime));
        }
    }
}