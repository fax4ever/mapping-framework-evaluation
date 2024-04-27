package fax.play.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowsAndMoviesLogger {

   private final Logger logger = LoggerFactory.getLogger(ShowsAndMoviesLogger.class);

   private final String operation;
   private final int chunkSize;
   private int chunkExecuted = 0;

   public ShowsAndMoviesLogger(String operation, int chunkSize) {
      this.operation = operation;
      this.chunkSize = chunkSize;
   }

   public void chunkExecuted() {
      chunkExecuted++;
      int logFactor = 10;
      if (chunkExecuted % logFactor == 0) {
         logger.info("Processed {} chunks. Total {}: {}.", chunkExecuted, operation, chunkExecuted * chunkSize);
      }
   }

   public void chunkRollback(Throwable t) {
      logger.warn("Chunk rollback.", t);
   }

   public void complete() {
      logger.info("Processed {} chunks. Total {}: {}. Load completed.", chunkExecuted, operation, chunkExecuted * chunkSize);
   }
}
