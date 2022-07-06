import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Integer,Object[]> map = new HashMap<>();
        map.put(123, new Object[]{123,"GGG"});
        System.out.println(JSON.toJSONString(map));
        Object[] objects = (Object[]) map.get(123);
        System.out.println(JSON.toJSONString(objects));


    }
}
