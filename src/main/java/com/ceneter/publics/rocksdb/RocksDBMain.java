/**
 * 
 */
package com.ceneter.publics.rocksdb;

import org.rocksdb.CompactionStyle;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.Statistics;
import org.rocksdb.TickerType;
import org.rocksdb.util.SizeUnit;

/**
 * @author zhuzhu
 *
 */
public class RocksDBMain {

	static {
		RocksDB.loadLibrary();
	}

	/**
	 * @param args
	 * @throws RocksDBException
	 */
	public static void main(String[] args) throws RocksDBException {

		Options options = new Options();

		options.setCreateIfMissing(true).setStatistics(new Statistics())
				.setWriteBufferSize(8 * SizeUnit.KB).setMaxWriteBufferNumber(3)
				.setMaxBackgroundCompactions(10)
				// .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
				.setCompactionStyle(CompactionStyle.UNIVERSAL);

		RocksDB db = RocksDB.open(options, "d://rocksdb");
		for (int i = 0; i < 100000; i++)
			db.put(("key" + i).getBytes(), "vv".getBytes());

		System.out.println(new String(db.get("key".getBytes())));
		
		long tickerCount = options.statistics().getTickerCount(TickerType.BLOCK_CACHE_ADD);
		
		System.out.println(tickerCount);

	}

}
