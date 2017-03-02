package simpledao.cityoff.com.easydao.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by wangyang on 2017/2/20.
 */

public class IOUtils {
    /**
     * 关闭流
     * @param closeable
     */
    public static void close(Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
