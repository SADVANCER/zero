package zero.common.algo.sort;

/**
 * @author ningcheng
 * @date 2017/12/25
 */
public class SortUtil {

    protected static void swap(int[] s, int p1, int p2) {
        int t = s[p1];
        s[p1] = s[p2];
        s[p2] = t;
    }

}
