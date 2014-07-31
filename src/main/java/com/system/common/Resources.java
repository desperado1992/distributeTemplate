package com.system.common;

import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:45:12 
 * @function:
 */
public final class Resources {

    private Resources() {
    }

    public static final int NO_MESSAGES_TO_SEND = 100;

    //FILES
    public static final String CLIENT_OUTPUT = "clientOutput.txt";
    public static final String SERVER_OUTPUT = "serverOutput.txt";

    // HANDLERS
    public static final StringEncoder STRING_ENCODER = new StringEncoder(CharsetUtil.UTF_8);
    public static final StringDecoder STRING_DECODER = new StringDecoder(CharsetUtil.UTF_8);
    public static final LoggingHandler LOGGING_HANDLER = new LoggingHandler();
    public static final String STRING_ENCODER_NAME = "stringEncoder";
    public static final String STRING_DECODER_NAME = "stringDecoder";
    public static final String FRAME_ENCODER_NAME = "frameEncoder";
    public static final String FRAME_DECODER_NAME = "frameDecoder";
    public static final String LOGGING_HANDLER_NAME = "loggingHandler";
    public static final String CLIENT_HANDLER_NAME = "clientHandler";
    public static final String SERVER_HANDLER_NAME = "serverHandler";

    // SOCKET PROPERTIES
    // maximum queue length for incoming connection indications (a request to connect)
    public static final int SO_BACKLOG = 128;

    // FRAME OPTIONS
    public static final int FRAME_LENGTH_FIELD_OFFSET = 0;
    public static final int FRAME_LENGTH_ADJUSTMENT = 0;
    public static final int FRAME_LENGTH_FIELD_SIZE = 3;
    public static final int MAX_FRAME_LENGTH = (int) Math.pow(2, FRAME_LENGTH_FIELD_SIZE * 8);
}
