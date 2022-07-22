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
        int a =0;
        do {
            a=1;
            if(a!=0){
                break;
            }

            a=2;
            if(a!=0){
                break;
            }
        }while (a==0);
        System.out.println(a);

    }
}
