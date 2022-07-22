package com.wind.utils;

import java.io.Closeable;
import java.io.IOException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * IOUtil
 *
 * @author: GJK
 * @date: 2022/7/16 17:56
 * @description:
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IOUtil {

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            log.error("关闭资源失败->",ioe);
        }
    }
}
