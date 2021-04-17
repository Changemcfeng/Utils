import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * 求数组交集（interSection）
 * 求数组并集（unionSection）
 * 求数组差集（differenceSet）
 * 数组转化成set（arrayIntoSet）
 * set转化成数组（setIntoArray）
 * @author mcfeng
 * @version 1.0 创建时间2021-4-17
 * */

public class IUDUtils {

    /**
     * 求两数组交集
     *
     * @param interSection Integer[]
     * @param interSection1 Integer[]
     * @return Integer[]
     */
    public static Integer[] interSection(Integer[] interSection,Integer[] interSection1){
        Set<Integer> set1 = arrayIntoSet(interSection);
        Set<Integer> set2 = arrayIntoSet(interSection1);
        set1.retainAll(set2);
        return setIntoArray(set1,new Integer[set1.size()]);
    }

    /**
     * 求两数组交集
     *
     * @param interSection  String[]
     * @param interSection1 String[]
     * @return String[]
     */
    public static String[] interSection(String[] interSection,String[] interSection1){
        Set<String> set1 = arrayIntoSet(interSection);
        Set<String> set2 = arrayIntoSet(interSection1);
        set1.retainAll(set2);
        return setIntoArray(set1,new String[set1.size()]);
    }

    /**
     * 求两数组并集
     *
     * @param unionSection  Integer[]
     * @param unionSection1 Integer[]
     * @return Integer[]
     */
    public static Integer[] unionSection(Integer[] unionSection,Integer[] unionSection1){
        Set<Integer> set1 = arrayIntoSet(unionSection);
        Set<Integer> set2 = arrayIntoSet(unionSection1);
        set1.addAll(set2);
        return setIntoArray(set1,new Integer[set1.size()]);
    }

    /**
     * 求两数组并集
     *
     * @param unionSection  String[]
     * @param unionSection1 String[]
     * @return String[]
     */
    public static String[] unionSection(String[] unionSection,String[] unionSection1){
        Set<String> set1 = arrayIntoSet(unionSection);
        Set<String> set2 = arrayIntoSet(unionSection1);
        set1.addAll(set2);
        return setIntoArray(set1,new String[set1.size()]);
    }

    /**
     * 求两数组差集
     *
     * @param differenceSet  Integer[]
     * @param differenceSet1 Integer[]
     * @return Integer[]
     */
    public static String[] differenceSet(String[] differenceSet,String[] differenceSet1) {
        Set<String> set1 = arrayIntoSet(differenceSet);
        Set<String> set2 = arrayIntoSet(differenceSet1);
        set1.removeAll(set2);
        return setIntoArray(set1,new String[set1.size()]);
    }

    /**
     * 求两数组差集
     *
     * @param differenceSet  String[]
     * @param differenceSet1 String[]
     * @return String[]
     */
    public static Integer[] differenceSet(Integer[] differenceSet,Integer[] differenceSet1) {
        Set<Integer> set1 = arrayIntoSet(differenceSet);
        Set<Integer> set2 = arrayIntoSet(differenceSet1);
        set1.removeAll(set2);
        return setIntoArray(set1,new Integer[set1.size()]);
    }
    /**
     * 数组转化成set
     *
     * @param arrays  T[]
     * @return T[]
     */
    public static <T> Set arrayIntoSet(T[] arrays){
        return new HashSet<T>(Arrays.asList(arrays));
    }
    /**
     * set转化成数组(第二个参数调用 new String[set.size()] 因为无法new泛型数组)
     *
     * @param set  Set<T>
     * @param t  T[]
     * @return T[]
     */
    public static <T> T[] setIntoArray(Set<T> set,T[] t){
        return set.toArray(t);
    }
}
