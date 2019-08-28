package cn.smile.io.java8new.stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String [] args){
        System.out.println("-------------------------------------使用java7-------------------------------------------");

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println("列表:"+strings);

        //获取列表中的空字符串个数
        long count = getCountEmptyStringUsingJava7(strings);
        System.out.println("空字符数量为: " + count);

        //获取列表中字符串长度为3的字符串个数
        count = getCountLength3UsingJava7(strings);
        System.out.println("字符串长度为 3 的数量为: " + count);

        //删除空字符串
        List filtered = deleteEmptyStringsUsingJava7(strings);
        System.out.println("筛选后的列表: " + filtered);

        //删除空字符串，并使用逗号拼接
        String mergedString = getMergedStringUsingJava7(strings,",");
        System.out.println("合并字符串："+mergedString);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        //获取列表平方数
        List<Integer> squreLists = getSquares(numbers);
        System.out.println("平方列表："+squreLists);

        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
        System.out.println("列表："+integers);
        System.out.println("获取最大值："+getMax(integers));
        System.out.println("获取最小值："+getMin(integers));
        System.out.println("获取列表的和："+getSum(integers));
        System.out.println("获取平均值："+getAverage(integers));

        //输出10个随机数
        Random random = new Random();
        for(int i=0;i<10;i++){
            System.out.print(random.nextInt(10)+" ");
        }
        System.out.println();

        System.out.println("-----------------------------------使用java8----------------------------------------------");
        System.out.println("列表："+strings);

        strings.stream().forEach(string -> System.out.print(string));

        count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("列表中空字串的个数："+count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("列表中长度为3的字串个数："+count);

        List filteredList2 = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("列表中去除长度为0的字符串："+filteredList2);

        String mergedString2 = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
        System.out.println("列表中去除空字符串并用都好拼接："+mergedString2);

        List squeredList2 = numbers.stream().map(i -> i*i).collect(Collectors.toList());
        System.out.println("整数集合求每个元素的平方："+squeredList2);
        IntSummaryStatistics intSummaryStatistics = integers.stream().mapToInt(x -> x).summaryStatistics();

        System.out.println("列表："+integers);
        System.out.println("获取最大值："+intSummaryStatistics.getMax());
        System.out.println("获取最小值："+intSummaryStatistics.getMin());
        System.out.println("获取列表的和："+intSummaryStatistics.getSum());
        System.out.println("获取平均值："+intSummaryStatistics.getAverage());

        random.ints(0,10).limit(10).sorted().forEach(System.out::print);

        System.out.println();
        // 并行处理
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("空字符串的数量为: " + count);


    }

    private static int getCountEmptyStringUsingJava7(List<String> strings){
        int count = 0;

        for(String string: strings){

            if(string.isEmpty()){
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings){
        int count = 0;

        for(String string: strings){

            if(string.length() == 3){
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings){
        List<String> filteredList = new ArrayList<String>();

        for(String string: strings){

            if(!string.isEmpty()){
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings, String separator){
        StringBuilder stringBuilder = new StringBuilder();

        for(String string: strings){

            if(!string.isEmpty()){
                stringBuilder.append(string);
                stringBuilder.append(separator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length()-2);
    }

    private static List<Integer> getSquares(List<Integer> numbers){
        List<Integer> squaresList = new ArrayList<Integer>();

        for(Integer number: numbers){
            Integer square = new Integer(number.intValue() * number.intValue());

            if(!squaresList.contains(square)){
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers){
        int max = numbers.get(0);

        for(int i=1;i < numbers.size();i++){

            Integer number = numbers.get(i);

            if(number.intValue() > max){
                max = number.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers){
        int min = numbers.get(0);

        for(int i=1;i < numbers.size();i++){
            Integer number = numbers.get(i);

            if(number.intValue() < min){
                min = number.intValue();
            }
        }
        return min;
    }

    private static int getSum(List numbers){
        int sum = (int)(numbers.get(0));

        for(int i=1;i < numbers.size();i++){
            sum += (int)numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers){
        return getSum(numbers) / numbers.size();
    }

}
