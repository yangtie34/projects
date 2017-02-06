package com.glite.flink.example.batch.outputformat;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.common.io.FileOutputFormat;
import org.apache.flink.core.fs.Path;

@PublicEvolving
public class TextOutputFormat<T> extends FileOutputFormat<T> {

	private static final long serialVersionUID = 1L;
	
	private static final int NEWLINE = '\n';

	private String charsetName;
	
	private transient Charset charset;

	// --------------------------------------------------------------------------------------------

	public static interface TextFormatter<IN> extends Serializable {
		public String format(IN value);
	}

	public TextOutputFormat(Path outputPath) {
		this(outputPath, "UTF-8");
	}
	
	public TextOutputFormat(Path outputPath, String charset) {
		super(outputPath);
		this.charsetName = charset;
	}
	
	
	public String getCharsetName() {
		return charsetName;
	}
	
	public void setCharsetName(String charsetName) throws IllegalCharsetNameException, UnsupportedCharsetException {
		if (charsetName == null) {
			throw new NullPointerException();
		}
		
		if (!Charset.isSupported(charsetName)) {
			throw new UnsupportedCharsetException("The charset " + charsetName + " is not supported.");
		}
		
		this.charsetName = charsetName;
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public void open(int taskNumber, int numTasks) throws IOException {
		super.open(taskNumber, numTasks);
		
		try {
			this.charset = Charset.forName(charsetName);
		}
		catch (IllegalCharsetNameException e) {
			throw new IOException("The charset " + charsetName + " is not valid.", e);
		}
		catch (UnsupportedCharsetException e) {
			throw new IOException("The charset " + charsetName + " is not supported.", e);
		}
	}
	
	@Override
	public void writeRecord(T record) throws IOException {
		byte[] bytes = record.toString().getBytes(charset);
		this.stream.write(bytes);
		this.stream.write(NEWLINE);
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "TextOutputFormat (" + getOutputFilePath() + ") - " + this.charsetName;
	}
}
