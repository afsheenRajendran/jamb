package com.ithellam.jamb.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step000 {
    private static final Logger logger = LoggerFactory.getLogger(Step000.class);

    public static void main(final String[] args) {
        final int numArgs = args.length;

        if (numArgs < 1) {
            logger.error("Expected at least one argument. But got {}.", numArgs);
            System.exit(-1);
        }

        Integer stepNum = null;
        final String stepNumArg = args[0];

        try {
            stepNum = Integer.valueOf(stepNumArg);
        } catch (final NumberFormatException ex) {
            logger.error("Unable to convert first argument: {} into integer to determine step number", stepNumArg);
        }

        logger.info("Started executing step number: {}", stepNum);

        try {
            new Step100().execute();

            logger.info("Finished executing step number: {}", stepNum);

        } catch (final Exception ex) {
            logger.error("Caught exception.", ex);
            System.exit(-7);
        }

        System.exit(0);
    }
}

