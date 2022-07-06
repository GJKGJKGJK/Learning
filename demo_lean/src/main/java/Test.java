import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;

/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
public class Test {

    public static void main(String[] args) {
        Map<Integer,String> map = new ConcurrentHashMap<>();
        for(int i=0;;i++){
            map.put(i,String.valueOf(i));
        }



    }
}
