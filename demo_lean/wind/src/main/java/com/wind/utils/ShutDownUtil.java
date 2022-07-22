package com.wind.utils;

import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;


/**
 * IOUtil
 *
 * @author: GJK
 * @date: 2022/7/16 17:54
 * @description:
 */
public final class ShutDownUtil {

    private static final List<Closeable> SHUT_DOWN_LIST = new LinkedList<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Closeable closeable : SHUT_DOWN_LIST) {
                IOUtil.closeQuietly(closeable);
            }
        }));
    }

    private ShutDownUtil() {

    }

    public static void addShutdownTask(Closeable closeable) {
        SHUT_DOWN_LIST.add(closeable);
    }
}
